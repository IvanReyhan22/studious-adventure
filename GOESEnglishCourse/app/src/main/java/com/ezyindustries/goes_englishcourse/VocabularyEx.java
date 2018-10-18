package com.ezyindustries.goes_englishcourse;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyindustries.goes_englishcourse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.Objects;
import java.util.TimerTask;

import static android.graphics.Color.rgb;

public class VocabularyEx extends AppCompatActivity {

    private ImageView back, readmore1, readmore2, readmore3, readmore4;
    private LinearLayout nextl, ConA_L;
    private CardView a, b, c, d, ConA,ConB,ConC,ConD;
    private TextView Question, mscore, number, Level;
    private TextView Answer_A, Answer_B, Answer_C, Answer_D, Ex_A, Ex_B, Ex_C,Ex_D;
    private String Correct, questiondata, level;
    private String Ulevel, pLevel, KEY;
    private FirebaseAuth Auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref, refU;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    boolean TimesClick = true;

    float startDegress = -180;
    float endDegress = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_ex);

        back = (ImageView) findViewById(R.id.back);
        Question = (TextView) findViewById(R.id.question);
        mscore = (TextView) findViewById(R.id.mscore);
        number = (TextView) findViewById(R.id.number);
        Level = (TextView) findViewById(R.id.level);

        Answer_A = (TextView) findViewById(R.id.Answer_A);
        Answer_B = (TextView) findViewById(R.id.Answer_B);
        Answer_C = (TextView) findViewById(R.id.Answer_C);
        Answer_D = (TextView) findViewById(R.id.Answer_D);

        a = (CardView) findViewById(R.id.a);
        b = (CardView) findViewById(R.id.b);
        c = (CardView) findViewById(R.id.c);
        d = (CardView) findViewById(R.id.d);

        Ex_A = (TextView)findViewById(R.id.Ex_A);
        Ex_B = (TextView)findViewById(R.id.Ex_B);
        Ex_C = (TextView)findViewById(R.id.Ex_C);
        Ex_D = (TextView)findViewById(R.id.Ex_D);

        ConA = (CardView)findViewById(R.id.ConA);
        ConB = (CardView)findViewById(R.id.ConB);
        ConC = (CardView)findViewById(R.id.ConC);
        ConD = (CardView)findViewById(R.id.ConD);

        ConA_L = (LinearLayout)findViewById(R.id.ConA_L);
        readmore1 = (ImageView) findViewById(R.id.readmore1);
        readmore2 = (ImageView) findViewById(R.id.readmore2);
        readmore3 = (ImageView) findViewById(R.id.readmore3);
        readmore4 = (ImageView) findViewById(R.id.readmore4);

        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Latihan soal");
        refU = firebaseDatabase.getReference("user");

        OnStart();
        getData();
        setOnclickListener();


    }

    private void OnStart(){
        ConA.setVisibility(View.GONE);
        ConB.setVisibility(View.GONE);
        ConC.setVisibility(View.GONE);
        ConD.setVisibility(View.GONE);

    }
    private void getData() {

        refU.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("latihan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pLevel = dataSnapshot.getValue(String.class);


                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                Integer KeY = Objects.requireNonNull(extras).getInt("key", 0);
                KEY = String.valueOf(KeY);
                number.setText(KEY);
                Answer_A.setText(KEY);
                ref.child(pLevel).child(KEY).child("Question").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String question = dataSnapshot.getValue(String.class);
                        Question.setText(question);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                ref.child(pLevel).child(KEY).child("AnswerA").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String AnswerA = dataSnapshot.getValue(String.class);
                        Answer_A.setText(AnswerA);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ref.child(pLevel).child(KEY).child("AnswerB").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String AnswerB = dataSnapshot.getValue(String.class);
                        Answer_B.setText(AnswerB);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ref.child(pLevel).child(KEY).child("AnswerC").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String AnswerC = dataSnapshot.getValue(String.class);
                        Answer_C.setText(AnswerC);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ref.child(pLevel).child(KEY).child("AnswerD").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String AnswerD = dataSnapshot.getValue(String.class);
                        Answer_D.setText(AnswerD);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ref.child(pLevel).child(KEY).child("Correct").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Correct = dataSnapshot.getValue(String.class);
                        if (Answer_A.getText().equals(Correct)) {
                            a.setCardBackgroundColor(rgb(39, 174, 96));
                            ConA.setCardBackgroundColor(rgb(39, 174, 96));
                        } else if (Answer_B.getText().equals(Correct)) {
                            b.setCardBackgroundColor(rgb(39, 174, 96));
                        } else if (Answer_C.getText().equals(Correct)) {
                            c.setCardBackgroundColor(rgb(39, 174, 96));
                        } else if (Answer_D.getText().equals(Correct)) {
                            d.setCardBackgroundColor(rgb(39, 174, 96));
                        } else if (!Answer_A.getText().equals(Correct)) {
                            a.setCardBackgroundColor(rgb(231, 76, 60));
                        } else if (!Answer_B.getText().equals(Correct)) {
                            b.setCardBackgroundColor(rgb(231, 76, 60));
                        } else if (!Answer_C.getText().equals(Correct)) {
                            c.setCardBackgroundColor(rgb(231, 76, 60));
                        } else if (!Answer_D.getText().equals(Correct)) {
                            d.setCardBackgroundColor(rgb(231, 76, 60));
                        } else {
                            Toast.makeText(getApplicationContext(), "There's no Correct answer for this question!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ref.child(pLevel).child(KEY).child("Ex_A").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String exa = dataSnapshot.getValue(String.class);
                        Ex_A.setText(exa);

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


    private void setOnclickListener(){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VocabularyEx.this, resultlayout.class);
                startActivity(intent);
                finish();
            }
        });

        Answer_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TimesClick == true){

                    startDegress+=180;
                    endDegress+=180;
                    RotateAnimation anim = new RotateAnimation(startDegress, endDegress, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

                    anim.setInterpolator(new LinearInterpolator());
                    anim.setRepeatCount(0);
                    anim.setFillAfter(true);
                    anim.setDuration(300);

                    ConA.setVisibility(View.VISIBLE);
                    readmore1.startAnimation(anim);
                    TimesClick = false;
                }else {
                    startDegress+=180;
                    endDegress+=180;
                    RotateAnimation anim = new RotateAnimation(startDegress, endDegress, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    anim.setInterpolator(new LinearInterpolator());
                    anim.setRepeatCount(0);
                    anim.setFillAfter(true);
                    anim.setDuration(300);

                    ConA.setVisibility(View.GONE);
                    readmore1.startAnimation(anim);
                    TimesClick = true;
                }
            }
        });

        Answer_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TimesClick == true){

                    startDegress+=180;
                    endDegress+=180;
                    RotateAnimation anim = new RotateAnimation(startDegress, endDegress, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

                    anim.setInterpolator(new LinearInterpolator());
                    anim.setRepeatCount(0);
                    anim.setFillAfter(true);
                    anim.setDuration(300);

                    ConB.setVisibility(View.VISIBLE);
                    readmore2.startAnimation(anim);
                    TimesClick = false;
                }else {
                    startDegress+=180;
                    endDegress+=180;
                    RotateAnimation anim = new RotateAnimation(startDegress, endDegress, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    anim.setInterpolator(new LinearInterpolator());
                    anim.setRepeatCount(0);
                    anim.setFillAfter(true);
                    anim.setDuration(300);

                    ConB.setVisibility(View.GONE);
                    readmore2.startAnimation(anim);
                    TimesClick = true;
                }
            }
        });

        Answer_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TimesClick == true){

                    startDegress+=180;
                    endDegress+=180;
                    RotateAnimation anim = new RotateAnimation(startDegress, endDegress, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

                    anim.setInterpolator(new LinearInterpolator());
                    anim.setRepeatCount(0);
                    anim.setFillAfter(true);
                    anim.setDuration(300);

                    ConC.setVisibility(View.VISIBLE);
                    readmore3.startAnimation(anim);
                    TimesClick = false;
                }else {
                    startDegress+=180;
                    endDegress+=180;
                    RotateAnimation anim = new RotateAnimation(startDegress, endDegress, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    anim.setInterpolator(new LinearInterpolator());
                    anim.setRepeatCount(0);
                    anim.setFillAfter(true);
                    anim.setDuration(300);

                    ConC.setVisibility(View.GONE);
                    readmore3.startAnimation(anim);
                    TimesClick = true;
                }
            }
        });

        Answer_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConD.requestFocus();
                if(TimesClick == true){

                    startDegress+=180;
                    endDegress+=180;
                    RotateAnimation anim = new RotateAnimation(startDegress, endDegress, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

                    anim.setInterpolator(new LinearInterpolator());
                    anim.setRepeatCount(0);
                    anim.setFillAfter(true);
                    anim.setDuration(300);

                    ConD.setVisibility(View.VISIBLE);
                    readmore4.startAnimation(anim);
                    TimesClick = false;
                }else {
                    startDegress+=180;
                    endDegress+=180;
                    RotateAnimation anim = new RotateAnimation(startDegress, endDegress, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    anim.setInterpolator(new LinearInterpolator());
                    anim.setRepeatCount(0);
                    anim.setFillAfter(true);
                    anim.setDuration(300);

                    ConD.setVisibility(View.GONE);
                    readmore4.startAnimation(anim);
                    TimesClick = true;
                }
            }
        });


    }
}
