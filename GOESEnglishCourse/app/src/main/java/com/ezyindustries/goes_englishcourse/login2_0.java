package com.ezyindustries.goes_englishcourse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login2_0 extends AppCompatActivity {

    private Button login;
    private TextView forgot;
    private TextView register;
    private EditText InputEmail, InputPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2_0);
        auth = FirebaseAuth.getInstance();


        login = (Button)findViewById(R.id.signin);
        forgot = (TextView)findViewById(R.id.forget);
        register = (TextView)findViewById(R.id.register);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        InputEmail = (EditText) findViewById(R.id.email);
        InputPassword = (EditText) findViewById(R.id.password);

        auth = FirebaseAuth.getInstance();


        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(login2_0.this, main_page.class));
            finish();
        }

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login2_0.this, forgot_password.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login2_0.this, register.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = InputEmail.getText().toString();
                final String password = InputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login2_0.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {

                                    if (password.length() < 6) {
                                        Toast.makeText(getApplicationContext(), "Password To Short!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "Failed" + task.getException(),Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(login2_0.this, main_page.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });


            }
        });

    }
}
