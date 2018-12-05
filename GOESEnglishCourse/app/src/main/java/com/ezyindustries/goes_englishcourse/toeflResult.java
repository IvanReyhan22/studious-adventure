package com.ezyindustries.goes_englishcourse;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyindustries.goes_englishcourse.Score.scoreData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class toeflResult extends AppCompatActivity {

    private TextView name, Score;
    private ImageView profilpic, back;
    private FirebaseAuth Auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private String picprofil, scoreTemp, totalToeflString;
    private ProgressBar loading;
    private CardView vocabulary;
    private CardView basic;
    private CardView video;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toefl_result);

        vocabulary = (CardView) findViewById(R.id.vocabulary2);
        basic = (CardView)findViewById(R.id.lesson2);
        video = (CardView) findViewById(R.id.videos2) ;

        back = (ImageView) findViewById(R.id.back);
        loading = (ProgressBar) findViewById(R.id.loadingpic);
        profilpic = (ImageView) findViewById(R.id.profilpic);
        name = (TextView) findViewById(R.id.name);
        Score = (TextView) findViewById(R.id.score);
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("user");
        Auth = FirebaseAuth.getInstance();

        getData();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Update();
            }
        },1000);

        Onclicklistener();
    }

    private void Onclicklistener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(toeflResult.this, main_page.class));
                finish();
            }
        });
        vocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(toeflResult.this, Vocabulary_fetchUp.class);
                startActivity(intent);
                finish();
            }
        });

        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(toeflResult.this, basiclearning.class);
                startActivity(intent);
                finish();
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(toeflResult.this, videos.class);
                startActivity(intent);
                finish();
            }
        });


    }
    private void getData(){
        ref.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User usr = dataSnapshot.getValue(User.class);
                name.setText(Objects.requireNonNull(usr).getUsername());
                picprofil = Objects.requireNonNull(usr).getPicUrl();
                Picasso.get().load(picprofil).into(profilpic, new Callback() {
                    @Override
                    public void onSuccess() {
                    loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("Score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                scoreData sd = dataSnapshot.getValue(scoreData.class);

                String currentToeflScore = Objects.requireNonNull(sd).getToeflScore();
                Integer integertoefcurrent = Integer.parseInt(currentToeflScore);

                String point = Objects.requireNonNull(sd).getPoint();
                Integer intpoint = Integer.parseInt(point);

                Integer totaltoef = integertoefcurrent + intpoint;
                totalToeflString = String.valueOf(totaltoef);

                ref.child(Auth.getCurrentUser().getUid()).child("Score").child("toeflScore").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Score.setText(dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Update(){
        ref.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("Score").child("toeflScore").setValue(Score.getText().toString()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Theres an error analyzing your score", Toast.LENGTH_SHORT).show();
                    }
                });

        ref.child(Auth.getCurrentUser().getUid()).child("Score").child("point").setValue("0").addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Theres an error analyzing your point", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
