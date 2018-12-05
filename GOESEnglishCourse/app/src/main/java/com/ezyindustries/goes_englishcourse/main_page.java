package com.ezyindustries.goes_englishcourse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyindustries.goes_englishcourse.Score.insertsoal;
import com.ezyindustries.goes_englishcourse.Score.scoreData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class main_page extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private FirebaseAuth Auth;
    private CardView vocabulary, picName;
    private CardView basic;
    private CardView video;
//    private ImageView rating;
    private CardView test;
    private TextView Name, navUser;
    private DrawerLayout drawerLayout;
    private NavigationView nv;
    private ActionBarDrawerToggle t;
    private TextView User,level,Total,toeflscore;


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        vocabulary = (CardView) findViewById(R.id.vocabulary);
        basic = (CardView)findViewById(R.id.lesson);
        video = (CardView) findViewById(R.id.videos) ;
        test = (CardView) findViewById(R.id.test);
        Name = (TextView) findViewById(R.id.name);
        User = (TextView) findViewById(R.id.User);
        level= (TextView) findViewById(R.id.level);
        Total= (TextView) findViewById(R.id.total);
        toeflscore= (TextView) findViewById(R.id.toeflScore);

        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("user");

        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, drawerLayout,R.string.app_name, R.string.app_name);

        drawerLayout.addDrawerListener(t);
        t.syncState();

        picName=(CardView)findViewById(R.id.picname);

        picName.setCardBackgroundColor(Color.TRANSPARENT);

//        LayoutInflater inflater = getLayoutInflater();
//        View listHeaderView = inflater.inflate(R.layout.navigation_header, null, false);
//        User.addHeaderView

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.account:
                        startActivity(new Intent(main_page.this, profile.class));
//                        Toast.makeText(getApplicationContext(), "My Account", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Email:
                        startActivity(new Intent(main_page.this, profile.class));
                        break;
                    case R.id.friendlist:
                        startActivity(new Intent(main_page.this, friendlist.class));
                        break;
                    case R.id.logout:
                        Dialog();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });


        vocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_page.this, Vocabulary_fetchUp.class);
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


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_page.this, toeflFetching.class);
                startActivity(intent);
            }
        });
        getName();
//        getScore();
        getDataScore();
    }

    private void getName(){
        ref.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("nickname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nama= dataSnapshot.getValue(String.class);
                Name.setText(nama);
                View headerView = nv.getHeaderView(0);
                TextView user = (TextView) headerView.findViewById(R.id.User);
                user.setText(nama);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void getDataScore(){
        ref.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("Score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    scoreData sd = dataSnapshot.getValue(scoreData.class);
                    Integer vocab =Integer.parseInt(Objects.requireNonNull(sd).getVocabularyScore());
                    Integer toef = Integer.parseInt(sd.getToeflScore());
                    Integer total = vocab + toef;
                    level.setText(Objects.requireNonNull(sd).getVocabularyScore());
                    toeflscore.setText(sd.getToeflScore());
                    String Ttotal = String.valueOf(total);
                    Total.setText(Ttotal);
                    String key = ref.push().getKey();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(main_page.this, "Failed Retrieve data please restart!." + databaseError.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Dialog(){
        AlertDialog alertDialog =new AlertDialog.Builder(this).create();

        TextView title = new TextView(this);

        title.setText("Log Out");
        title.setPadding(10, 30, 10, 20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        TextView info = new TextView(this);
        info.setText("Are you Sure want to Log out? ");
        info.setGravity(Gravity.CENTER_HORIZONTAL);
        info.setTextColor(Color.BLACK);
        alertDialog.setView(info);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Auth.signOut();
                startActivity(new Intent(main_page.this, login2_0.class));
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
        neuturalBtn.gravity = Gravity.RIGHT;
        okBT.setPadding(50, 10, 10, 10);
        okBT.setTextColor(Color.BLACK);
        okBT.setLayoutParams(neuturalBtn);

        final Button cancleBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negativeBtn = (LinearLayout.LayoutParams) cancleBT.getLayoutParams();
        negativeBtn.gravity = Gravity.RIGHT;
        cancleBT.setPadding(50, 10, 10, 10);
        cancleBT.setTextColor(Color.RED);
        cancleBT.setLayoutParams(negativeBtn);
    }


}

