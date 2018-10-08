package com.ezyindustries.goes_englishcourse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class main_page extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth Auth;
    private CardView vocabulary;
    private CardView basic;
    private CardView video;
    private ImageView rating;
    private CardView test;
    private TextView Name;


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        vocabulary = (CardView) findViewById(R.id.vocabulary);
        basic = (CardView)findViewById(R.id.lesson);
        video = (CardView) findViewById(R.id.videos) ;
        rating = (ImageView) findViewById(R.id.rating);
        test = (CardView) findViewById(R.id.test);
        Name = (TextView) findViewById(R.id.name);

        Auth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user");

        vocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_page.this, vocabulary10.class);
                startActivity(intent);
            }
        });

        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_page.this, basiclearning.class);
                startActivity(intent);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_page.this, videos.class);
                startActivity(intent);
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_page.this, setting2.class);
                startActivity(intent);
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_page.this, toefl.class);
                startActivity(intent);
            }
        });
        getName();
    }

    private void getName(){
        databaseReference.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("nickname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nama= dataSnapshot.getValue(String.class);
                Name.setText(nama);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

