package com.ezyindustries.goes_englishcourse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ezyindustries.goes_englishcourse.RecyclerView.refData;
import com.ezyindustries.goes_englishcourse.RecyclerView.refLearnData;
import com.ezyindustries.goes_englishcourse.RecyclerView.refViewHolder;
import com.ezyindustries.goes_englishcourse.RecyclerView.refViewHolderLearn;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResultLearning extends AppCompatActivity {

    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private FirebaseAuth Auth;
    private RecyclerView Listlearned;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private String lesson, updateLesson;
    private Integer no1=0, no2 =0;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_learning);

        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("user");


        Listlearned = (RecyclerView) findViewById(R.id.Listlearned);
        Listlearned.setLayoutManager(new LinearLayoutManager(this));
        Listlearned.setHasFixedSize(true);

        getprofilData();

        next = (Button)findViewById(R.id.done);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
                startActivity(new Intent(ResultLearning.this, main_page.class));
                finish();
            }
        });

    }

    private void update(){
        ref.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("lesson").setValue(updateLesson).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    private void getprofilData(){
        ref.child(Objects.requireNonNull(Auth.getCurrentUser()).getUid()).child("lesson").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lesson = dataSnapshot.getValue(String.class);
                no1 = Integer.parseInt(lesson);
                no2 = no1 + 1;
                updateLesson = String.valueOf(no2);
                recycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void recycler() {
        Query query = FirebaseDatabase.getInstance().getReference("Lesson/"+lesson).limitToLast(50);

        FirebaseRecyclerOptions<refLearnData> options = new FirebaseRecyclerOptions.Builder<refLearnData>()
                .setQuery(query, refLearnData.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<refLearnData, refViewHolderLearn>(options) {
            @Override
            protected void onBindViewHolder(@NonNull refViewHolderLearn holder, final int position, @NonNull refLearnData model) {
                holder.Title.setText(model.getTitle());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = firebaseRecyclerAdapter.getRef(position).getKey();
                        Intent intent = new Intent(ResultLearning.this, Youtube.class);
                        intent.putExtra("Data", id);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public refViewHolderLearn onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.learnrow, parent, false);
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.learnrow, parent, false);
                return new refViewHolderLearn(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        Listlearned.setAdapter(firebaseRecyclerAdapter);
    }

    public void onBackPressed() {
        update();
        startActivity(new Intent(ResultLearning.this, main_page.class));
        finish();
    }
}
