package com.ezyindustries.goes_englishcourse;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class edit_form extends AppCompatActivity {

    private ImageView confirm, cancel;
    private EditText name, nickname, descirption, mobilephone, email, website;
    private FirebaseAuth Auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String lesson, tes, latihan, Gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);
        confirm = (ImageView) findViewById(R.id.confirm);
        cancel = (ImageView) findViewById(R.id.cancel);

        name = (EditText) findViewById(R.id.name);
        nickname = (EditText) findViewById(R.id.nickname);
        descirption = (EditText) findViewById(R.id.description);
        mobilephone = (EditText) findViewById(R.id.number);
        email = (EditText) findViewById(R.id.email);
        website = (EditText) findViewById(R.id.website);

        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user");

        getData();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

    }

    private void getData(){
        databaseReference.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    name.setText(Objects.requireNonNull(user).getUsername());
                    nickname.setText(user.getNickname());
                    descirption.setText(user.getDeskripsion());
                    email.setText(user.getEmail());
                    website.setText(user.getWebsite());
                    mobilephone.setText(user.getPhone());
                    lesson = user.getLesson();
                    tes = user.getTes();
                    Gender = user.getGender();
                    latihan = user.getLatihan();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(edit_form.this, "Update Failed Logcat   " + databaseError.getDetails(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void update(){
        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User usr = new User(
                        name.getText().toString(),
                        nickname.getText().toString(),
                        email.getText().toString(),
                        mobilephone.getText().toString(),
                        lesson,
                        tes,
                        latihan,
                        Gender,
                        descirption.getText().toString(),
                        website.getText().toString()

                );
                databaseReference.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).setValue(usr).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(edit_form.this, "Insert Data Failed", Toast.LENGTH_LONG).show();
            }
        });

    }
}
