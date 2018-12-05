package com.ezyindustries.goes_englishcourse;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
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

import okhttp3.internal.cache.DiskLruCache;

public class toeflFetching extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth Auth;
    private DatabaseReference ref;
    private DatabaseReference refU;
    private String TES, par1,par2,par3;

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

        ref.child("Direction").child("Direction1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    par1 = dataSnapshot.child("Par1").getValue(String.class);
                    par2 = dataSnapshot.child("Par2").getValue(String.class);
                    par3 = dataSnapshot.child("Par3").getValue(String.class);
                    if(par1 != null){
                        if(par2 != null){
                            if(par3!= null){
                                Bundle extras = new Bundle();
                                extras.putString("1",par1);
                                extras.putString("2",par2);
                                extras.putString("3",par3);
                                Intent intent = new Intent(toeflFetching.this, Direction.class);
                                intent.putExtras(extras);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                startActivity(intent);
                                finish();
                            }else {
                                FetchData();
                            }
                        }else {
                            FetchData();
                        }
                    }else{
                        FetchData();
                    }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
