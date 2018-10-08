package com.ezyindustries.goes_englishcourse;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class toefl extends AppCompatActivity {

    private ImageView locked, unlocked;
    private CardView choiceA,choiceB,choiceC,choiceD;
    private TextView a, b,c,d, idnumber;
    private Integer number = 0, audioList= 0,questionnumber = 0;
    private String ok;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("toeflListening");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toefl);
        idnumber = (TextView)findViewById(R.id.idnumber);

        unlocked = (ImageView) findViewById(R.id.unlocked);
        locked = (ImageView) findViewById(R.id.locked);

        //Button choice decalaration
        choiceA = (CardView) findViewById(R.id.true1);choiceB = (CardView) findViewById(R.id.true2);choiceC = (CardView) findViewById(R.id.true3);choiceD = (CardView) findViewById(R.id.true4);
        //Text View answer declaration
        a = (TextView)findViewById(R.id.a);b = (TextView)findViewById(R.id.b);c = (TextView)findViewById(R.id.c);d = (TextView)findViewById(R.id.d);
        scoreupdete(number);
        start();

    }

    /*private void updatestatus(){
        try{
            mRootRef.child("Tes/Paket1/1/Status").setValue("Gold");
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    private void scoreupdete(int point){ idnumber.setText("" + questionnumber);}

    private void setLock(){
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                locked.setAlpha((Float) animation.getAnimatedValue());
            }
        });

        animator.start();
    }

    private void setUnlocked(){
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                locked.setAlpha((Float) animation.getAnimatedValue());
            }
        });

        //animator.setDuration(1500);
        animator.end();
    }

    private void start(){
        scoreupdete(number);
        questionnumber ++;
        //audioList ++;
        setLock();
        scoreupdete(audioList);

        DatabaseReference mAudioRef= mRootRef.child("Tes/Paket1/" + questionnumber +"/audioUrl");
        mAudioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String AudioUrl = dataSnapshot.getValue(String.class);
                MediaPlayer mediaPlayer = new MediaPlayer();
                try{
                    mediaPlayer.setDataSource(""+AudioUrl+"");
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            setUnlocked();
                            mediaPlayer.start();
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                public void onCompletion(MediaPlayer mp) {
                                    setLock();
                                    choiceA.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            //audioList ++;
                                            scoreupdete(questionnumber);
                                            start();

                                        }
                                    });
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

        DatabaseReference mAnswerARef= mRootRef.child("Tes/Paket1/" + questionnumber +"/AnswerA");
        mAnswerARef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Answer = dataSnapshot.getValue(String.class);
                a.setText(Answer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mAnswerBRef= mRootRef.child("Tes/Paket1/" + questionnumber +"/AnswerB");
        mAnswerBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Answer = dataSnapshot.getValue(String.class);
                b.setText(Answer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mAnswerCRef= mRootRef.child("Tes/Paket1/" + questionnumber +"/AnswerC");
        mAnswerCRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Answer = dataSnapshot.getValue(String.class);
                c.setText(Answer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mAnswerDRef= mRootRef.child("Tes/Paket1/" + questionnumber +"/AnswerD");
        mAnswerDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Answer = dataSnapshot.getValue(String.class);
                d.setText(Answer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

















        /*final StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("listeningTest/"+audioList+".mp3");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Log.e("Tuts+", "uri: " + uri.toString());
                ok = String.valueOf(uri);
                MediaPlayer mediaPlayer = new MediaPlayer();
                try{
                    mediaPlayer.setDataSource(""+ok+"");
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                public void onCompletion(MediaPlayer mp) {
                                    Toast.makeText(toefl.this,"Choose the answer now",Toast.LENGTH_LONG).show();
                                    choiceA.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            //audioList ++;
                                            scoreupdete(audioList);
                                            start();

                                        }
                                    });
                                }
                            });
                        }
                    });
                    mediaPlayer.prepare();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(toefl.this,"Failed To get Data Please Restart",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });*/
    }


//contructor exstend
}
