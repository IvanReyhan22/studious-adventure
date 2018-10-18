package com.ezyindustries.goes_englishcourse;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static android.graphics.Color.rgb;

public class vocabulary10 extends AppCompatActivity {

    private ImageView back;
    private LinearLayout nextl;
    private CardView a, b, c, d;
    private TextView question, mscore, number, Level;
    private TextView Answer_A, Answer_B, Answer_C, Answer_D;
    private String Correct, questiondata, level;
    private Integer questionnumber = 0;
    private Integer score = 0, qtrue = 0;
    private Integer limitation = 5;
    private FirebaseAuth Auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;

    private Animation scale;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary10);
        back = (ImageView) findViewById(R.id.back);
        question = (TextView) findViewById(R.id.question);
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

        scale =AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale_animation);
        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("user");

        levelData();

        final Animation Shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(vocabulary10.this, main_page.class);
                startActivity(intent);
            }
        });



        Answer_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Answer_A.getText().equals(Correct)) {
                    a.startAnimation(scale);
                    backgroundChangeATrue();
                    score = score + 10;
                    scoreupdete(score);
                    updatestatus();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestion();
                        }
                    },1000);
                    numbershow();
                    questiontruecounter();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            limit();
                        }
                    },1000);

                } else {
                    a.startAnimation(Shake);
                    backgroundChangeA();
                    updatestatusfalse();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestion();
                        }
                    },1000);
                    numbershow();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            limit();
                        }
                    },1000);
                }
            }
        });

        Answer_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Answer_B.getText().equals(Correct)) {
                    b.startAnimation(scale);
                    backgroundChangeBTrue();
                    score = score + 10;
                    scoreupdete(score);
                    updatestatus();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestion();
                        }
                    },1000);
                    numbershow();
                    questiontruecounter();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            limit();
                        }
                    },1000);

                } else {
                    b.startAnimation(Shake);
                    backgroundChangeB();
                    updatestatusfalse();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestion();
                        }
                    },1000);
                    numbershow();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            limit();
                        }
                    },1000);
                }
            }
        });

        Answer_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Answer_C.getText().equals(Correct)) {
                    c.startAnimation(scale);
                    backgroundChangeCTrue();
                    score = score + 10;
                    scoreupdete(score);
                    updatestatus();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestion();
                        }
                    },1000);
                    numbershow();
                    questiontruecounter();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            limit();
                        }
                    },1000);
                } else {
                    c.startAnimation(Shake);
                    backgroundChangeC();
                    updatestatusfalse();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestion();
                        }
                    },1000);
                    numbershow();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            limit();
                        }
                    },1000);

                }
            }
        });

        Answer_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Answer_D.getText().equals(Correct)) {
                    d.startAnimation(scale);
                    backgroundChangeDTrue();
                    score = score + 10;
                    scoreupdete(score);
                    updatestatus();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestion();
                        }
                    },1000);
                    numbershow();
                    questiontruecounter();
                    limit();
                } else {
                    d.startAnimation(Shake);
                    backgroundChangeD();
                    updatestatusfalse();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestion();
                        }
                    },1000);
                    numbershow();
                    limit();
                }
            }
        });
    }

    private void backgroundChangeA(){
        a.setCardBackgroundColor(rgb(231, 76, 60));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                a.setCardBackgroundColor(Color.WHITE);
            }
        },1000);
    }

    private void backgroundChangeB(){
        b.setCardBackgroundColor(rgb(231, 76, 60));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setCardBackgroundColor(Color.WHITE);
            }
        },1000);
    }

    private void backgroundChangeC(){
        c.setCardBackgroundColor(rgb(231, 76, 60));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                c.setCardBackgroundColor(Color.WHITE);
            }
        },1000);
    }

    private void backgroundChangeD(){
        d.setCardBackgroundColor(rgb(231, 76, 60));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                d.setCardBackgroundColor(Color.WHITE);
            }
        },1000);
    }


//true
    private void backgroundChangeATrue(){
        a.setCardBackgroundColor(rgb(39, 174, 96));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                a.setCardBackgroundColor(Color.WHITE);
            }
        },1000);
    }

    private void backgroundChangeBTrue(){
        b.setCardBackgroundColor(rgb(39, 174, 96));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setCardBackgroundColor(Color.WHITE);
            }
        },1000);
    }

    private void backgroundChangeCTrue(){
        c.setCardBackgroundColor(rgb(39, 174, 96));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                c.setCardBackgroundColor(Color.WHITE);
            }
        },1000);
    }

    private void backgroundChangeDTrue(){
        d.setCardBackgroundColor(rgb(39, 174, 96));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                d.setCardBackgroundColor(Color.WHITE);
            }
        },1000);
    }

    private void levelData(){

        ref.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("latihan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                level = dataSnapshot.getValue(String.class);
                Level.setText(level);
                updateQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updatestatus(){
        try{
            mRootRef.child("Latihan soal/"+level+"/" + questionnumber +"/Status").setValue("Gold");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updatestatusfalse(){
        try{
            mRootRef.child("Latihan soal/"+level+"/" + questionnumber +"/Status").setValue("Zonk");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void questiontruecounter(){
        qtrue += 1;
    }

    private void scoreupdete(int point){ mscore.setText("" + score);}

    private void numbershow (){number.setText(""+questionnumber);}

    public void limit(){
        if (questionnumber == limitation ){

            Bundle extras = new Bundle();
            extras.putInt("SCORE", score);
            extras.putInt("QTRUE", qtrue);
            Intent intent = new Intent(vocabulary10.this, resultlayout.class);
            intent.putExtras(extras);
            startActivity(intent);
            finish();

        }else{

        }
    }

    public void updateQuestion() {

        questionnumber ++;

        DatabaseReference mQuestionRef= mRootRef.child("Latihan soal/"+level+"/" + questionnumber +"/Question");
        mQuestionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    questiondata = dataSnapshot.getValue(String.class);
                    String Question = dataSnapshot.getValue(String.class);
                    question.setText(Question);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference mAnswerARef= mRootRef.child("Latihan soal/"+level+"/" + questionnumber +"/AnswerA");
        mAnswerARef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Answer = dataSnapshot.getValue(String.class);
                Answer_A.setText(Answer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference mAnswerBRef= mRootRef.child("Latihan soal/"+level+"/" + questionnumber +"/AnswerB");
        mAnswerBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Answer = dataSnapshot.getValue(String.class);
                Answer_B.setText(Answer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference mAnswerCRef= mRootRef.child("Latihan soal/"+level+"/" + questionnumber +"/AnswerC");
        mAnswerCRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Answer = dataSnapshot.getValue(String.class);
                Answer_C.setText(Answer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference mAnswerDRef= mRootRef.child("Latihan soal/"+level+"/" + questionnumber +"/AnswerD");
        mAnswerDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Answer = dataSnapshot.getValue(String.class);
                Answer_D.setText(Answer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mCorrectDRef= mRootRef.child("Latihan soal/"+level+"/" + questionnumber +"/Correct/");
        mCorrectDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Correct= dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        numbershow();
    }
}




