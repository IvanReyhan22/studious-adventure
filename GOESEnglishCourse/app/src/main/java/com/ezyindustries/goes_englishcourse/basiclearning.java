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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class basiclearning extends AppCompatActivity {

    private Button next;
    private FirebaseAuth Auth;
    private TextView Stitle,example, paragraf1, paragraf2, paragraf3;
    private String Dtitle, lesson;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref,refU;
    private Integer number = 1;
    private String numberCon;

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

        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Lesson");
        refU = firebaseDatabase.getReference("user");


        getData();

    }


    private void getData(){
        refU.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("lesson").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lesson = dataSnapshot.getValue(String.class);
                ref.child(lesson).child("Part"+number).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String Title = dataSnapshot.child("title").getValue(String.class);
                        String Example = dataSnapshot.child("Example").getValue(String.class);
                        String par1 = dataSnapshot.child("par1").getValue(String.class);
                        String par2 = dataSnapshot.child("par1").getValue(String.class);
                        String par3 = dataSnapshot.child("par3").getValue(String.class);
                        Stitle.setText(Title);
                        example.setText(Example);
                        paragraf1.setText(par1);
                        paragraf2.setText(par2);
                        paragraf3.setText(par3);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer numbercheck = number + 1;
                        ref.child(lesson).child("Part"+numbercheck).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String Tempoary = dataSnapshot.child("title").getValue(String.class);
                                if(Tempoary != null){
                                    number++;
                                    getData();
                                }else{
                                    Intent intent = new Intent(basiclearning.this, ResultLearning.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });

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
