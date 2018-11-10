package com.ezyindustries.goes_englishcourse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ezyindustries.goes_englishcourse.Score.scoreData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class register extends AppCompatActivity {

    private Button signup;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private EditText InputEmail, InputPassword, username, mobilenumber, nickname;
    private ImageView next;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private RadioGroup rg;
    String Gender;
    private RadioButton l, p;

//   User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signup = (Button) findViewById(R.id.signup);
        auth = FirebaseAuth.getInstance();
        next = (ImageView) findViewById(R.id.next);
        InputEmail = (EditText) findViewById(R.id.emailinput);
        InputPassword = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.username);
        nickname = (EditText) findViewById(R.id.Nickname);
        mobilenumber = (EditText) findViewById(R.id.mobilenumber);

        rg =(RadioGroup) findViewById(R.id.rg);
        l = (RadioButton) findViewById(R.id.l);
        p =(RadioButton) findViewById(R.id.p);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        database = FirebaseDatabase.getInstance();
        ref =  database.getReference("user");


        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = InputEmail.getText().toString().trim();
                String password = InputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                onclick();
                if(username.getText().toString() == null && nickname.getText().toString() == null && InputEmail.getText().toString() == null && mobilenumber.getText().toString() == null && Gender == null){

                    Toast.makeText(getApplicationContext(), "There is an Empty Field", Toast.LENGTH_SHORT).show();
                }



                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(register.this, "Authentication failed." + task.getException(),Toast.LENGTH_SHORT).show();
                                } else {
                                    onclick();
                                    User usr = new User(
                                            username.getText().toString(),
                                            nickname.getText().toString(),
                                            InputEmail.getText().toString(),
                                            mobilenumber.getText().toString(),
                                            "PartA",
                                            "Paket1",
                                            "level1",
                                            Gender,
                                            "No Detail about me yet.....",
                                            "......"

                                    );
                                    ref.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).setValue(usr).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            scoreData sd = new scoreData(
                                                    "0",
                                                    "0",
                                                    "0"
                                            );
                                            ref.child(auth.getCurrentUser().getUid()).child("Score").setValue(sd).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    startActivity(new Intent(register.this, main_page.class));
                                                    finish();
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }


    public void onclick(){
        int RadioButtonID = rg.getCheckedRadioButtonId();

        RadioButton radioButton = (RadioButton) findViewById(RadioButtonID);
        Gender =  radioButton.getText().toString();

    }

}