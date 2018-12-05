package com.ezyindustries.goes_englishcourse;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
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

import org.apache.http.auth.AUTH;

import java.io.IOException;
import java.util.Objects;

public class Toeflpart2 extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth Auth;
    private DatabaseReference ref;
    private DatabaseReference refU;
    private ImageView pic;
    private CardView true1, true2, true3, true4;
    private TextView a,b,c,d, number,time;
    private Boolean audio = false;
    private String par1,par2,par3;
    private String correct, level ,PAKET, point, isPoint;
    private Integer  questionNumber = 21, limit=41 /*41*/, Point = 0, pointIn = 0, truepoint = 10;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toeflpart2);

        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Tes");
        refU = firebaseDatabase.getReference("user");

        pic = (ImageView)findViewById(R.id.pic);
        true1 = (CardView)findViewById(R.id.true1);
        true2 = (CardView)findViewById(R.id.true2);
        true3 = (CardView)findViewById(R.id.true3);
        true4 = (CardView)findViewById(R.id.true4);
        time = (TextView)findViewById(R.id.time);

        a= (TextView) findViewById(R.id.a);
        b= (TextView) findViewById(R.id.b);
        c= (TextView) findViewById(R.id.c);
        d= (TextView) findViewById(R.id.d);

        number = (TextView)findViewById(R.id.idnumber);

        Start();

    }

    private void CountdownTimer(){
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText("Time: " + millisUntilFinished / 1000);
                ref.child(level+"/"+questionNumber+"/Correct").addValueEventListener(new ValueEventListener() {
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
        questionNumber++;
        numbershow();
        refU.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("tes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                level = dataSnapshot.getValue(String.class);
                if(questionNumber >= limit){
                    Limit();
                }else {
                    Soundplay();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Soundplay(){
        ref.child(level+"/"+questionNumber+"/audioUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String AudioUrl = dataSnapshot.getValue(String.class);
                try{
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(""+AudioUrl+"");
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                public void onCompletion(MediaPlayer mp) {
                                    CountdownTimer();
                                }
                            });
                        }
                    });
                    mediaPlayer.prepare();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Limit(){
        mediaPlayer.stop();
        if(questionNumber >= limit){
            final String Direct ="Direction3";
            ref.child("Direction").child(Direct).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    par1 = dataSnapshot.child("Par1").getValue(String.class);
                    par2 = dataSnapshot.child("Par2").getValue(String.class);
                    par3 = dataSnapshot.child("Par3").getValue(String.class);

                    if(par1 != null){
                        if(par2 != null){
                            if(par3!= null){
                                Bundle extras = new Bundle();
                                extras.putString("1",par1);
                                extras.putString("2",par2);
                                extras.putString("3",par3);
                                extras.putString("NextDirec",Direct);
                                Intent intent = new Intent(Toeflpart2.this, Direction.class);
                                intent.putExtras(extras);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                startActivity(intent);
                                finish();
                            }else {
                                Dialog3();
                            }
                        }else {
                            Dialog3();
                        }
                    }else{
                        Dialog3();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{

        }
    }

    private void Timer(){
        if(audio = false){

        }else if(audio = true){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Start();
                }
            },3000);
        }

        audio = false;
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

    private void numbershow (){number.setText(""+questionNumber);}

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
                startActivity(new Intent(Toeflpart2.this, main_page.class));
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
        mediaPlayer.pause();
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
                startActivity(new Intent(Toeflpart2.this, main_page.class));
                finish();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mediaPlayer.start();
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
        mediaPlayer.pause();
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
                startActivity(new Intent(Toeflpart2.this, main_page.class));
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
