package com.ezyindustries.goes_englishcourse;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
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
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.Objects;

import static android.graphics.Color.rgb;

public class VocabularyEx extends AppCompatActivity {


    private ImageView back;
    private LinearLayout nextl;
    private CardView a, b, c, d;
    private TextView Question, mscore, number, Level;
    private TextView Answer_A, Answer_B, Answer_C, Answer_D;
    private String Correct, questiondata, level;
    private String Ulevel, pLevel,KEY;
    private FirebaseAuth Auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref,refU;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private ExpandableTextView expandableTextView;
    private String LongText="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum";


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


        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Latihan soal");
        refU = firebaseDatabase.getReference("user");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VocabularyEx.this, resultlayout.class);
                startActivity(intent);
                finish();
            }
        });


        getData();
        //checkStatus();

    }

    private void getData(){

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
//                        if(Answer_A.getText().equals(Correct)){
//                            a.setCardBackgroundColor(rgb(39, 174, 96));
//                        }else if(Answer_B.getText().equals(Correct)){
//                            b.setCardBackgroundColor(rgb(39, 174, 96));
//                        }else if(Answer_C.getText().equals(Correct)){
//                            c.setCardBackgroundColor(rgb(39, 174, 96));
//                        }else if(Answer_D.getText().equals(Correct)){
//                            d.setCardBackgroundColor(rgb(39, 174, 96));
//                        }else if(!Answer_A.getText().equals(Correct)){
//                            a.setCardBackgroundColor(rgb(231, 76, 60));
//                        }else if(!Answer_B.getText().equals(Correct)){
//                            b.setCardBackgroundColor(rgb(231, 76, 60));
//                        }else if(!Answer_C.getText().equals(Correct)){
//                            c.setCardBackgroundColor(rgb(231, 76, 60));
//                        }else if(!Answer_D.getText().equals(Correct)){
//                            d.setCardBackgroundColor(rgb(231, 76, 60));
//                        }else{
//                            Toast.makeText(getApplicationContext(), "There's no Correct answer for this question!",Toast.LENGTH_SHORT).show();
//                        }
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
//
//    private void checkStatus() {
//
//        DatabaseReference mCorrectARef = mRootRef.child("Latihan soal/"+pLevel+"/"+KEY+"/Correct");
//        mCorrectARef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String Correct = dataSnapshot.getValue(String.class);
//                String Answer = Answer_A.getText().toString();
//                Answer_C.setText(Answer);
//                if (Answer.equals("What")) {
//                    a.setCardBackgroundColor(Color.GREEN);
//                } else {
//                    a.setCardBackgroundColor(Color.RED);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//        DatabaseReference mCoreectBRef = mRootRef.child("Latihan soal/"+pLevel+"/"+KEY+"/Correct");
//        mCoreectBRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String Correct = dataSnapshot.getValue(String.class);
//                if (Answer_B.getText().equals(Correct)) {
//                    b.setCardBackgroundColor(Color.GREEN);
//                } else {
//                    b.setCardBackgroundColor(Color.RED);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//        DatabaseReference mCorrectCRef = mRootRef.child("Latihan soal/"+pLevel+"/"+KEY+"/Correct");
//        mCorrectCRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String Correct = dataSnapshot.getValue(String.class);
//                if (Answer_C.getText().equals(Correct)) {
//                    c.setCardBackgroundColor(Color.GREEN);
//                } else {
//                    c.setCardBackgroundColor(Color.RED);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//        DatabaseReference mCorrectDRef = mRootRef.child("Latihan soal/"+pLevel+"/"+KEY+"/Correct");
//        mCorrectDRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String Correct = dataSnapshot.getValue(String.class);
//                if (Answer_D.getText().equals(Correct)) {
//                    d.setCardBackgroundColor(Color.GREEN);
//                } else {
//                    d.setCardBackgroundColor(Color.RED);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//    }

}
