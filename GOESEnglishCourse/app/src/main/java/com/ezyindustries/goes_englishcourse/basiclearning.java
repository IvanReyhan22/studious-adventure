package com.ezyindustries.goes_englishcourse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class basiclearning extends AppCompatActivity {

    private Button next;
    private TextView Stitle,example;
    String Dtitle;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basiclearning);
        next= (Button) findViewById(R.id.continued);
        Stitle = (TextView) findViewById(R.id.title);
        example = (TextView) findViewById(R.id.example);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(basiclearning.this, resultlayout.class);
                startActivity(intent);
            }
        });

        lessondata();
    }

    private void lessondata(){
        DatabaseReference mQuestionRef= mRootRef.child("Lesson/a/Title");
        mQuestionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Dtitle= dataSnapshot.getValue(String.class);
                String title = dataSnapshot.getValue(String.class);
                Stitle.setText(title);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mExampleRef= mRootRef.child("Lesson/a/Example");
        mExampleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Dtitle= dataSnapshot.getValue(String.class);
                String title = dataSnapshot.getValue(String.class);
                example.setText(title);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
