package com.ezyindustries.goes_englishcourse;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyindustries.goes_englishcourse.Score.scoreData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class profile extends AppCompatActivity {

    private ImageButton back;
    private FirebaseAuth auth;
    private FirebaseAuth Auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private TextView Total, levelScore, toeflScore, descirption, website, mobilenumber, email, nickname;
    private String ok = "halo";
    private ImageView pic;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        auth = FirebaseAuth.getInstance();
        Total = (TextView) findViewById(R.id.total);
        toeflScore = (TextView) findViewById(R.id.toeflScore);
        descirption = (TextView) findViewById(R.id.desciption);
        website = (TextView) findViewById(R.id.website);
        mobilenumber = (TextView) findViewById(R.id.mobilenumber);
        email = (TextView) findViewById(R.id.email);
        nickname = (TextView) findViewById(R.id.nickname);
        levelScore = (TextView) findViewById(R.id.level);
        back = (ImageButton) findViewById(R.id.back);
        pic = (ImageView) findViewById(R.id.pic);
        loading = (ProgressBar) findViewById(R.id.loading);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("user");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile.this, main_page.class);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this, edit_form.class));
            }
        });

        getPic();
        getData();
        getDataScore();
    }

    private void getData() {
        ref.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    nickname.setText(Objects.requireNonNull(user).getNickname());
                    descirption.setText(user.getDeskripsion());
                    email.setText(user.getEmail());
                    website.setText(user.getWebsite());
                    mobilenumber.setText(user.getPhone());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(profile.this, "Failed Retrieve data please restart!." + databaseError.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataScore(){
        ref.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("Score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    scoreData sd = dataSnapshot.getValue(scoreData.class);
                    Integer vocab =Integer.parseInt(Objects.requireNonNull(sd).getVocabularyScore());
                    Integer toef = Integer.parseInt(sd.getToeflScore());
                    Integer total = vocab + toef;
                    levelScore.setText(Objects.requireNonNull(sd).getVocabularyScore());
                    toeflScore.setText(sd.getToeflScore());
                    String Ttotal = String.valueOf(total);
                    Total.setText(Ttotal);

                    String key = ref.push().getKey();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(profile.this, "Failed Retrieve data please restart!." + databaseError.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPic(){
        ref.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("picUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String imageResource = dataSnapshot.getValue(String.class);
                if(imageResource.equals("")){
                    loading.setVisibility(View.GONE);
                }else{
                    Picasso.get().load(imageResource).into(pic, new Callback() {
                        @Override
                        public void onSuccess() {
                            loading.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("Image", e.getMessage());}
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(profile.this, main_page.class));
        finish();
    }
}

