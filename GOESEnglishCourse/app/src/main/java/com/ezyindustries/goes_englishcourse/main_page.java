package com.ezyindustries.goes_englishcourse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
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
    private DrawerLayout drawerLayout;
    private NavigationView nv;
    private ActionBarDrawerToggle t;


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

        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, drawerLayout,R.string.app_name, R.string.app_name);

        drawerLayout.addDrawerListener(t);
        t.syncState();

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Toast.makeText(getApplicationContext(), "My Account",Toast.LENGTH_SHORT).show();
                    case R.id.email:
                        Toast.makeText(getApplicationContext(), "Configure Email",Toast.LENGTH_SHORT).show();
                    case R.id.friendlist:
                        Toast.makeText(getApplicationContext(), "Friend List",Toast.LENGTH_SHORT).show();
                    default:
                        return true;
                }

            }
        });

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

