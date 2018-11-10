package com.ezyindustries.goes_englishcourse;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Objects;

public class toeflFetching extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth Auth;
    private DatabaseReference ref;
    private DatabaseReference refU;
    private String TES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toefl_fetching);

        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Tes");
        refU = firebaseDatabase.getReference("user");
        FetchData();
    }


    private void FetchData() {

        refU.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("tes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TES = dataSnapshot.getValue(String.class);

                ref.child(TES).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                            if (Objects.requireNonNull(Snapshot.getKey()).equalsIgnoreCase("Direction1")) {
                                String AudioUrl = dataSnapshot.getValue(String.class);
                                MediaPlayer mediaPlayer = new MediaPlayer();
                                try{
                                    mediaPlayer.setDataSource(""+AudioUrl+"");
                                    mediaPlayer.prepare();
                                }catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        startActivity(new Intent(toeflFetching.this, Direction.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();

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


}
