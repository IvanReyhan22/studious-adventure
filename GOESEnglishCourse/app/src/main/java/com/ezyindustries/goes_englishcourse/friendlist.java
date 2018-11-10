package com.ezyindustries.goes_englishcourse;

import android.app.ProgressDialog;
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
import android.widget.LinearLayout;

import com.ezyindustries.goes_englishcourse.RecyclerView.refData;
import com.ezyindustries.goes_englishcourse.RecyclerView.refListFriend;
import com.ezyindustries.goes_englishcourse.RecyclerView.refViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class friendlist extends AppCompatActivity {


    private FirebaseAuth Auth;
    private RecyclerView ListFriend;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        ListFriend =(RecyclerView)findViewById(R.id.ListFriend);

        ListFriend = (RecyclerView) findViewById(R.id.ListFriend);
        ListFriend.setLayoutManager(new LinearLayoutManager(this));
        ListFriend.setHasFixedSize(true);

        getData();
    }

    private void getData(){
        final ProgressDialog dialog = new ProgressDialog(friendlist.this);
        dialog.setMessage("Hold on we loading up data for you....");
        dialog.show();

        Query query = FirebaseDatabase.getInstance().getReference("user").limitToLast(50);

        FirebaseRecyclerOptions<refListFriend> options = new FirebaseRecyclerOptions.Builder<refListFriend>()
                .setQuery(query,refListFriend.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<refListFriend, refViewHolder>(options){
            @Override
            protected void onBindViewHolder(@NonNull refViewHolder holder, final int position, @NonNull refListFriend model) {
                holder.Stitle.setText(model.getUsername());

//                Picasso.get().load(model.getUrl()).into(holder.Simage, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        dialog.hide();
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        Log.e("Image", e.getMessage());}
//                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = firebaseRecyclerAdapter.getRef(position).getKey();

                        Intent intent = new Intent(friendlist.this, Youtube.class);
                        intent.putExtra("Data", id);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public refViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.row, parent, false);
                return new refViewHolder(view);
            }


        };

        firebaseRecyclerAdapter.startListening();
        ListFriend.setAdapter(firebaseRecyclerAdapter);
        dialog.hide();
    }
}
