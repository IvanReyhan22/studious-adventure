package com.ezyindustries.goes_englishcourse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Vocabulary_fetchUp extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_fetch_up);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Latihan Soal");
        FetchData();
    }

    private void FetchData(){

        ref.child("level1").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot Snapshot: dataSnapshot.getChildren()){
                    if(Objects.requireNonNull(Snapshot.getKey()).equalsIgnoreCase("audioUrl")){
                        String temp = dataSnapshot.getValue(String.class);
                    }
                }
                startActivity(new Intent(Vocabulary_fetchUp.this, vocabulary10  .class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
