package com.ezyindustries.goes_englishcourse;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
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

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class spalsh_screen extends Activity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private String connection = "bad";

    @SuppressLint("WrongConstant")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("user");

        Timer t = new Timer();
        boolean checkConnection=new ApplicationUtility().checkConnection(this);
        if (checkConnection) {
            t.schedule(new splash(), 1000);

        } else {
            Toast.makeText(spalsh_screen.this, "No Connection on Internet", 3000).show();
        }

    }

        class splash extends TimerTask {

            @Override
            public void run() {
                Intent i = new Intent(spalsh_screen.this, login2_0.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(i);
                finish();
            }
        }

}

