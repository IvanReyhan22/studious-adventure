package com.ezyindustries.goes_englishcourse;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Toeflpart4 extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth Auth;
    private DatabaseReference ref,refU;

    private TextView a,b,c,d,a2,b2,c2,d2;
    private TextView number1, number2, numberA1, numberA2 ,time;

    private ImageView image1, image2;

    private String ca,cb,cc,cd,ca2,cb2,cc2,cd2, imageref, imageref2; //answer data & image data

    private String par1,par2,par3;
    private String correct, getLevel  ,PAKET, point, isPoint;

    private ProgressBar load1, load2;

    private Integer  questionNumber = 71, questionnumber2, limit=73, Point = 0, pointIn = 0, truepoint = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toeflpart4);

        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Tes");
        refU = firebaseDatabase.getReference("user");

        time = (TextView)findViewById(R.id.time);
        number1 = (TextView) findViewById(R.id.number1);
        number2 = (TextView) findViewById(R.id.number2);
        numberA1 = (TextView) findViewById(R.id.numberA1);
        numberA2 = (TextView) findViewById(R.id.numberA2);

        a= (TextView) findViewById(R.id.a);
        b= (TextView) findViewById(R.id.b);
        c= (TextView) findViewById(R.id.c);
        d= (TextView) findViewById(R.id.d);
        a2 = (TextView) findViewById(R.id.a2);
        b2 = (TextView) findViewById(R.id.b2);
        c2 = (TextView) findViewById(R.id.c2);
        d2 = (TextView) findViewById(R.id.d2);

        image1 = (ImageView) findViewById(R.id.Image1);
        image2 = (ImageView) findViewById(R.id.Image2);


        load1 = (ProgressBar) findViewById(R.id.load1);
        load2 = (ProgressBar) findViewById(R.id.load2);
        Start();

    }

    private void CountdownTimer(){
        new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                time.setText("Time: " + millisUntilFinished / 1000);
                ref.child(getLevel+"/"+questionNumber+"/Correct").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        correct = dataSnapshot.getValue(String.class);

                        a.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(a.getText().equals(correct)){
                                    getScorepoint();
                                }
                            }
                        });

                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(b.getText().equals(correct)){
                                    getScorepoint();
                                }
                            }
                        });

                        c.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(c.getText().equals(correct)){
                                    getScorepoint();
                                }
                            }
                        });

                        d.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(d.getText().equals(correct)){
                                    getScorepoint();
                                }
                            }
                        });

                        a2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(a.getText().equals(correct)){
                                    getScorepoint();
                                }
                            }
                        });

                        b2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(b.getText().equals(correct)){
                                    getScorepoint();
                                }
                            }
                        });

                        c2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(c.getText().equals(correct)){
                                    getScorepoint();
                                }
                            }
                        });

                        d2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(d.getText().equals(correct)){
                                    getScorepoint();
                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            public void onFinish() {
                Start();
            }

        }.start();
    }

    public void Start(){
        getLevel();

    }

    private void getQuestion1(){
        numbershow1();
        ref.child(getLevel+"/"+questionNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ca = dataSnapshot.child("answerA").getValue(String.class);
                cb = dataSnapshot.child("answerB").getValue(String.class);
                cc = dataSnapshot.child("answerC").getValue(String.class);
                cd = dataSnapshot.child("answerD").getValue(String.class);
                a.setText(ca);
                b.setText(cb);
                c.setText(cc);
                d.setText(cd);
                imageref = dataSnapshot.child("image1").getValue(String.class);
                Toast.makeText(getApplicationContext(), ""+getLevel+"/"+questionNumber, Toast.LENGTH_SHORT).show();
                Picasso.get().load(imageref).into(image1, new Callback() {
                    @Override
                    public void onSuccess() {
                        load1.setVisibility(View.GONE);
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
    }

    private void getQuestion2(){
        questionnumber2 = questionNumber + 1;
        numbershow2();
        ref.child(getLevel+"/"+questionnumber2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ca2 = dataSnapshot.child("answerA").getValue(String.class);
                cb2 = dataSnapshot.child("answerB").getValue(String.class);
                cc2 = dataSnapshot.child("answerC").getValue(String.class);
                cd2 = dataSnapshot.child("answerD").getValue(String.class);
                a2.setText(ca2);
                b2.setText(cb2);
                c2.setText(cc2);
                d2.setText(cd2);
                imageref2 = dataSnapshot.child("image1").getValue(String.class);
                Toast.makeText(getApplicationContext(), ""+getLevel+"/"+questionnumber2, Toast.LENGTH_SHORT).show();
                Picasso.get().load(imageref2).into(image2, new Callback() {
                    @Override
                    public void onSuccess() {
                        load2.setVisibility(View.GONE);
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
    }

    private void getLevel(){
        refU.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("tes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getLevel = dataSnapshot.getValue(String.class);

                questionNumber++;
                if(questionNumber.equals(limit)){
                    Limit();
                }else{
                    getQuestion1();
                    getQuestion2();
                    CountdownTimer();
                    questionNumber = questionnumber2;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void Limit(){
        if(questionNumber >= limit){
            Intent intent = new Intent(Toeflpart4.this, toeflResult.class);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            startActivity(intent);
            finish();
        }else{

        }
    }


    private void getScorepoint(){
        refU.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("Score").child("point").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                point = dataSnapshot.getValue(String.class);
                Toast.makeText(getApplicationContext(),""+point,Toast.LENGTH_SHORT).show();
                Point = Integer.parseInt(point);
                updateScore();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateScore(){
        Integer Pointtotal = Point + pointIn + 10;
        isPoint = String.valueOf(Pointtotal);
        refU.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("Score").child("point").setValue(isPoint).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Problem Detected error code : 019237",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void numbershow1(){
        number1.setText(""+questionNumber);
        numberA1.setText(""+questionNumber);
    }
    private void numbershow2(){
        number2.setText(""+questionnumber2);
        numberA2.setText(""+questionnumber2);
    }

    public void Dialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        TextView title = new TextView(this);

        title.setText("Error Retrieve Picture");
        title.setPadding(10, 30, 10, 20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        TextView info = new TextView(this);
        info.setText("Sorry !! It seems that we have some problem in picking up the picture. Please Contact our service about this problem!!");
        info.setGravity(Gravity.CENTER_HORIZONTAL);
        info.setTextColor(Color.BLACK);
        info.setPadding(15, 0, 15,0);
        alertDialog.setView(info);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Toeflpart4.this, main_page.class));
                finish();
            }
        });

        new Dialog(getApplicationContext());
        alertDialog.show();

        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neuturalBtn = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neuturalBtn.gravity = Gravity.LEFT;
        okBT.setPadding(50, 10, 10, 10);
        okBT.setTextColor(Color.BLACK);
        okBT.setLayoutParams(neuturalBtn);
    }
    public void onBackPressed() {
        Dialog2();
    }

    public void Dialog2() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        TextView title = new TextView(this);

        title.setText("Failed");
        title.setPadding(10, 30, 10, 20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        TextView info = new TextView(this);
        info.setText("Are you sure to exit the test? Your score will not be saved ");
        info.setGravity(Gravity.CENTER_HORIZONTAL);
        info.setTextColor(Color.BLACK);
        info.setPadding(15, 0, 15,0);
        alertDialog.setView(info);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Toeflpart4.this, main_page.class));
                finish();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        new Dialog(getApplicationContext());
        alertDialog.show();

        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neuturalBtn = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neuturalBtn.gravity = Gravity.LEFT;
        okBT.setPadding(50, 10, 10, 10);
        okBT.setTextColor(Color.BLACK);
        okBT.setLayoutParams(neuturalBtn);

        final Button cancleBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negativeBtn = (LinearLayout.LayoutParams) cancleBT.getLayoutParams();
        negativeBtn.gravity = Gravity.RIGHT;
        cancleBT.setPadding(60, 10, 20, 10);
        cancleBT.setTextColor(Color.RED);
        cancleBT.setLayoutParams(negativeBtn);
    }

    public void Dialog3() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        TextView title = new TextView(this);

        title.setText("Failed");
        title.setPadding(10, 30, 10, 20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        TextView info = new TextView(this);
        info.setText("Missing Data Server Code : 90109 Please COntact our engineer");
        info.setGravity(Gravity.CENTER_HORIZONTAL);
        info.setTextColor(Color.BLACK);
        info.setPadding(15, 0, 15,0);
        alertDialog.setView(info);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Toeflpart4.this, main_page.class));
                finish();
            }
        });


        new Dialog(getApplicationContext());
        alertDialog.show();

        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neuturalBtn = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neuturalBtn.gravity = Gravity.LEFT;
        okBT.setPadding(50, 10, 10, 10);
        okBT.setTextColor(Color.BLACK);
        okBT.setLayoutParams(neuturalBtn);

    }
}
