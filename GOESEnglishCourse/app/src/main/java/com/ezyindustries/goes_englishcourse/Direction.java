package com.ezyindustries.goes_englishcourse;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Objects;

public class Direction extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth Auth;
    private DatabaseReference ref,refU;
    private TextView instruction, title, par1, par2, par3;
    private ImageView pic1;
    private String Direct,Dpic;
    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        getDataIntent();

        pic1 = (ImageView) findViewById(R.id.pic1);
        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Tes");
        refU = firebaseDatabase.getReference("user");
        title = (TextView) findViewById(R.id.titleOF);

        par1 = (TextView) findViewById(R.id.par1);
        par2 = (TextView) findViewById(R.id.par2);
        par3 = (TextView) findViewById(R.id.par3);



        getIntentdata();
//        gerParagraf();
        getData();
    }

    private void getDataIntent(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String temporarry = Objects.requireNonNull(extras).getString("NextDirec");
        if(temporarry != null){
            Direct = extras.getString("NextDirec");
        }else{
            Direct = "Direction1";
        }

    }

    private void getData(){
        ref.child("Direction").child(Direct).child("audioUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String AudioUrl = dataSnapshot.getValue(String.class);
                try{
                    mediaPlayer.setDataSource(""+AudioUrl+"");
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                public void onCompletion(MediaPlayer mp) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            switch (Direct){
                                                case "Direction1":
                                                    startActivity(new Intent(Direction.this, toeflResult.class));
                                                    finish();
                                                    break;
                                                case "Direction2":
                                                    startActivity(new Intent(Direction.this, Toeflpart2.class));
                                                    finish();
                                                    break;
                                                case "Direction3":
                                                    startActivity(new Intent(Direction.this, Toeflpart3.class));
                                                    finish();
                                                    break;
                                                case "Direction4":
                                                    startActivity(new Intent(Direction.this, Toeflpart4.class));
                                                    finish();
                                                    break;
                                            }

                                        }
                                    },1000);

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

    private void getIntentdata(){
        ref.child("Direction").child(Direct).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Dpic = dataSnapshot.child("Picture").getValue(String.class);

                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                par1.setText(Objects.requireNonNull(extras).getString("1"));
                par3.setText(extras.getString("3"));
                if(!Dpic.equals("")){
                    Toast.makeText(getApplicationContext(),"Not Null",Toast.LENGTH_SHORT).show();
                    Picasso.get().load(Dpic).into(pic1, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Dialog();
                        }
                    });
                }else{
                    pic1.setVisibility(View.GONE);
                    par2.setText(extras.getString("2"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
        info.setText("Error Crasing data overload code 001212");
        info.setGravity(Gravity.CENTER_HORIZONTAL);
        info.setTextColor(Color.BLACK);
        info.setPadding(15, 0, 15,0);
        alertDialog.setView(info);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Direction.this, main_page.class));
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
        mediaPlayer.stop();
        startActivity(new Intent(Direction.this, main_page.class));
        finish();
    }

}

