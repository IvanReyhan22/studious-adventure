package com.ezyindustries.goes_englishcourse;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class tes_reading extends AppCompatActivity {

    private ImageView locked, unlocked;
    private CardView choiceA,choiceB,choiceC,choiceD;
    private TextView a, b,c,d, idnumber, question;
    private Integer number = 0, audioList= 0,questionnumber = 0;
    private String ok, Correct;
    private FirebaseDatabase firebaseDatabase;
    private String  questiondata, level;
    private DatabaseReference ref;
    private Integer no= 4;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("toeflListening");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes_reading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Tes");
        idnumber = (TextView)findViewById(R.id.idnumber);
        question = (TextView)findViewById(R.id.questionreading);
        a = (TextView)findViewById(R.id.a);b = (TextView)findViewById(R.id.b);c = (TextView)findViewById(R.id.c);d = (TextView)findViewById(R.id.d);
        choiceA = (CardView) findViewById(R.id.true1);choiceB = (CardView) findViewById(R.id.true2);choiceC = (CardView) findViewById(R.id.true3);choiceD = (CardView) findViewById(R.id.true4);


        getData();
        idnumber.setText("4");
        choiceA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a.getText().equals(Correct)) {
                    Toast.makeText(tes_reading.this, "Good Job", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(tes_reading.this, "Oops! Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        choiceB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a.getText().equals(Correct)) {
                    Toast.makeText(tes_reading.this, "Good Job", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(tes_reading.this, "Oops! Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        choiceC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a.getText().equals(Correct)) {
                    Toast.makeText(tes_reading.this, "Good Job", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(tes_reading.this, "Oops! Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        choiceD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a.getText().equals(Correct)) {
                    Toast.makeText(tes_reading.this, "Good Job", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(tes_reading.this, "Oops! Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getData(){

        DatabaseReference mQuestionRef= mRootRef.child("Tes/Paket1/"+no+"/Question");
        mQuestionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                questiondata = dataSnapshot.getValue(String.class);
                String Question = dataSnapshot.getValue(String.class);
                question.setText(Question);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference mAnswerARef= mRootRef.child("Tes/Paket1/"+no+"/AnswerA");
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


        DatabaseReference mAnswerBRef= mRootRef.child("Tes/Paket1/"+no+"/AnswerB");
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


        DatabaseReference mAnswerCRef= mRootRef.child("Tes/Paket1/"+no+"/AnswerC");
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


        DatabaseReference mAnswerDRef= mRootRef.child("Tes/Paket1/"+no+"/AnswerD");
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

        DatabaseReference mCorrectDRef= mRootRef.child("Tes/Paket1/"+no+"/Correct");
        mCorrectDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Correct= dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        no +=1;
    }
}
