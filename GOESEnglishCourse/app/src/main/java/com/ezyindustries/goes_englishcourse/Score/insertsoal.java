package com.ezyindustries.goes_englishcourse.Score;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezyindustries.goes_englishcourse.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class insertsoal extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private Button simpan;
    private EditText a, b, c, d, correct, question,status;
    private Integer soal = 0;
    private Integer soalP = 0;
    private String Soal="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertsoal);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Latihan soal");

        simpan = (Button)findViewById(R.id.save);

        a = (EditText)findViewById(R.id.a);
        b = (EditText)findViewById(R.id.b);
        c = (EditText)findViewById(R.id.c);
        d = (EditText)findViewById(R.id.d);
        correct = (EditText)findViewById(R.id.Correct);
        question = (EditText)findViewById(R.id.question);
        status = (EditText)findViewById(R.id.status);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run();
            }
        });
    }
    private void run(){
        soal sl = new soal(
                a.getText().toString(),
                b.getText().toString(),
                c.getText().toString(),
                d.getText().toString(),
                correct.getText().toString(),
                question.getText().toString(),
                "Zonk"
        );

        ref.child("level2").child("1").setValue(sl).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Succes",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void soal(){
        soal = Integer.parseInt(Soal);
        soalP +=1;
        Soal = String.valueOf(soalP);
    }
}
