package com.ezyindustries.goes_englishcourse;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class basiclearning extends AppCompatActivity {

    private Button next;
    private TextView Stitle,example, paragraf1, paragraf2, paragraf3;
    String Dtitle;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basiclearning);
        next= (Button) findViewById(R.id.continued);
        Stitle = (TextView) findViewById(R.id.title);
        example = (TextView) findViewById(R.id.example);
        paragraf1 = (TextView) findViewById(R.id.paragraf1);
        paragraf2 = (TextView) findViewById(R.id.paragraf2);
        paragraf3 = (TextView) findViewById(R.id.paragraf3);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Lesson");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(basiclearning.this, main_page.class);
                startActivity(intent);
            }
        });

        lessondata();
    }

    private void lessondata(){
        DatabaseReference mQuestionRef= mRootRef.child("Lesson/a/Title");
        mQuestionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Dtitle= dataSnapshot.getValue(String.class);
                String title = dataSnapshot.getValue(String.class);
                Stitle.setText(title);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mExampleRef= mRootRef.child("Lesson/a/Example");
        mExampleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Dtitle= dataSnapshot.getValue(String.class);
                String title = dataSnapshot.getValue(String.class);
                example.setText(title);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("a").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String par1 = dataSnapshot.child("par1").getValue(String.class);
                    String par2 = dataSnapshot.child("par2").getValue(String.class);
                    String par3 = dataSnapshot.child("par3").getValue(String.class);
                    paragraf1.setText(par1);
                    paragraf2.setText(par2);
                    paragraf3.setText(par3);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void Dialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        TextView title = new TextView(this);

        title.setText("Learn");
        title.setPadding(10, 30, 10, 20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        TextView info = new TextView(this);
        info.setText("Do You Want to do some Vocabulary learning now?");
        info.setGravity(Gravity.CENTER_HORIZONTAL);
        info.setTextColor(Color.BLACK);
        alertDialog.setView(info);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(basiclearning.this, vocabulary10.class));
                finish();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(basiclearning.this, main_page.class));
                finish();
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
