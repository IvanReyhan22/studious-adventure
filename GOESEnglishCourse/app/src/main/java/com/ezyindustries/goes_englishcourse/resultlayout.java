package com.ezyindustries.goes_englishcourse;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyindustries.goes_englishcourse.Score.scoreData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class resultlayout extends AppCompatActivity {

    private Button home;
    private LinearLayout view;
    private CardView no1, no2, no3, no4, no5, no6, no7, no8, no9, no10;
    private TextView statusno1, statusno2, statusno3, statusno4, statusno5, number1, number2, number3, number4, number5, isi1, isi2, isi3, isi4, isi5, Tscore, Qtrue, test;
    private ImageView checkfalse1, checkfalse2, checkfalse3, checkfalse4, checkfalse5;
    private ImageView checktrue1, checktrue2, checktrue3, checktrue4, checktrue5;
    private String Status, levelStatus, nickname, descirption, levelScore, email, website, mobilenumber, name, lesson, tes, gender, latihan, latihanU, latihanOpt;
    private Integer score, qtrue;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private FirebaseUser ur;
    private FirebaseUser Us;
    private FirebaseAuth Auth;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private String Vocabularyscore, Toeflscore, Point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultlayout);
        home = (Button) findViewById(R.id.home);
        view = (LinearLayout) findViewById(R.id.view);
        Tscore = (TextView) findViewById(R.id.Tscore);
        Qtrue = (TextView) findViewById(R.id.Qtrue);
        test = (TextView) findViewById(R.id.testlevel);

        checktrue1 = (ImageView) findViewById(R.id.true1);checktrue2 = (ImageView) findViewById(R.id.true2);checktrue3 = (ImageView) findViewById(R.id.true3);checktrue4 = (ImageView) findViewById(R.id.true4);checktrue5 = (ImageView) findViewById(R.id.true5);checkfalse1 = (ImageView) findViewById(R.id.false1);checkfalse2 = (ImageView) findViewById(R.id.false2);checkfalse3 = (ImageView) findViewById(R.id.false3);checkfalse4 = (ImageView) findViewById(R.id.false4);checkfalse5 = (ImageView) findViewById(R.id.false5);
        number1 = (TextView) findViewById(R.id.number1);number2 = (TextView) findViewById(R.id.number2);number3 = (TextView) findViewById(R.id.number3);number4 = (TextView) findViewById(R.id.number4);number5 = (TextView) findViewById(R.id.number5);
        isi1 = (TextView) findViewById(R.id.is1);isi2 = (TextView) findViewById(R.id.is2);isi3 = (TextView) findViewById(R.id.is3);isi4 = (TextView) findViewById(R.id.is4);isi5 = (TextView) findViewById(R.id.is5);
        statusno1 = (TextView) findViewById(R.id.statusno1);statusno2 = (TextView) findViewById(R.id.statusno2);statusno3 = (TextView) findViewById(R.id.statusno3);statusno4 = (TextView) findViewById(R.id.statusno4);statusno5 = (TextView) findViewById(R.id.statusno5);

        no1 = (CardView) findViewById(R.id.no1);
        no2 = (CardView) findViewById(R.id.no2);
        no3 = (CardView) findViewById(R.id.no3);
        no4 = (CardView) findViewById(R.id.no4);
        no5 = (CardView) findViewById(R.id.no5);
//        no6 =(CardView) findViewById(R.id.no6);
//        no7 =(CardView) findViewById(R.id.no7);
//        no8 =(CardView) findViewById(R.id.no8);
//        no9 =(CardView) findViewById(R.id.no9);
//        no10 =(CardView) findViewById(R.id.no10);

        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("user");
        ur = Auth.getCurrentUser();
        Us = Auth.getCurrentUser();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(resultlayout.this, main_page.class);
                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(resultlayout.this, vocabulary10.class);
                startActivity(intent);
            }
        });
        updatelevel();
        updateScore();
        checkStatus();
        Shows();

    }

    private void Shows() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        score = extras.getInt("SCORE", 0);
        qtrue = extras.getInt("QTRUE", 0);
        Tscore.setText("" + score);
        Qtrue.setText("" + qtrue);

    }

    private void checkStatus() {

        DatabaseReference mStatus1Ref = mRootRef.child("Latihan soal/level1/1/Status");
        mStatus1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Status = dataSnapshot.getValue(String.class);
                statusno1.setText(Status);
                if (statusno1.getText().equals("Gold")) {

                } else {
                    ValueAnimator animatort = ValueAnimator.ofFloat(0f, 1f);
                    animatort.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            checkfalse1.setAlpha((Float) animation.getAnimatedValue());
                        }
                    });

                    animatort.start();

                    ValueAnimator animatorf = ValueAnimator.ofFloat(1f, 0f);
                    animatorf.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            checktrue1.setAlpha((Float) animation.getAnimatedValue());
                        }
                    });

                    animatorf.start();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference mStatus2Ref = mRootRef.child("Latihan soal/level1/2/Status");
        mStatus2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Status = dataSnapshot.getValue(String.class);
                statusno2.setText(Status);
                if (statusno2.getText().equals("Gold")) {

                } else {
                    ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            checkfalse2.setAlpha((Float) animation.getAnimatedValue());
                        }
                    });

                    animator.start();

                    ValueAnimator animatorf = ValueAnimator.ofFloat(1f, 0f);
                    animatorf.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            checktrue2.setAlpha((Float) animation.getAnimatedValue());
                        }
                    });

                    animatorf.start();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference mStatus3Ref = mRootRef.child("Latihan soal/level1/3/Status");
        mStatus3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Status = dataSnapshot.getValue(String.class);
                statusno3.setText(Status);
                if (statusno3.getText().equals("Gold")) {

                } else {
                    ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            checkfalse3.setAlpha((Float) animation.getAnimatedValue());
                        }
                    });

                    animator.start();
                    ValueAnimator animatorf = ValueAnimator.ofFloat(1f, 0f);
                    animatorf.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            checktrue3.setAlpha((Float) animation.getAnimatedValue());
                        }
                    });

                    animatorf.start();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference mStatus4Ref = mRootRef.child("Latihan soal/level1/4/Status");
        mStatus4Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Status = dataSnapshot.getValue(String.class);
                statusno4.setText(Status);
                if (statusno4.getText().equals("Gold")) {

                } else {
                    ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            checkfalse4.setAlpha((Float) animation.getAnimatedValue());
                        }
                    });

                    animator.start();

                    ValueAnimator animatorf = ValueAnimator.ofFloat(1f, 0f);
                    animatorf.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            checktrue4.setAlpha((Float) animation.getAnimatedValue());
                        }
                    });

                    animatorf.start();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference mStatus5Ref = mRootRef.child("Latihan soal/level1/5/Status");
        mStatus5Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Status = dataSnapshot.getValue(String.class);
                statusno5.setText(Status);
                if (statusno5.getText().equals("Gold")) {

                } else {
                    ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            checkfalse5.setAlpha((Float) animation.getAnimatedValue());
                        }
                    });

                    animator.start();

                    ValueAnimator animatorf = ValueAnimator.ofFloat(1f, 0f);
                    animatorf.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            checktrue5.setAlpha((Float) animation.getAnimatedValue());
                        }
                    });

                    animatorf.start();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void updatelevel() {
        ref.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref.child(Auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            User user = dataSnapshot.getValue(User.class);
                            test.setText(Objects.requireNonNull(user).getLatihan());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                switch (test.getText().toString()){
                    case "level1" :
                        latihanU = "level2";
                    break;
                    case "level2" :
                        latihanU = "level3";
                        break;
                    case "level3" :
                        latihanU = "level4";
                        break;
                    case "leve4" :
                        latihanU = "level5";
                        break;
                    case "level5" :
                        latihanU = "level6";
                        break;
                    case "level6" :
                        latihanU = "level7";
                        break;
                    case "level7" :
                        latihanU = "level8";
                        break;
                    case "level8" :
                        latihanU = "level9";
                        break;
                    case "level9" :
                        latihanU = "level10";
                        break;
                        default:
                            Toast.makeText(getApplicationContext(), "Level Exeeded",Toast.LENGTH_SHORT).show();
                            break;

                }
                dataSnapshot.getRef().child("latihan").setValue(latihanU);
                ur.reload();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private  void updateScore(){
        ref.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("Score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("vocabularyScore").setValue(Tscore.getText().toString());
                ur.reload();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
