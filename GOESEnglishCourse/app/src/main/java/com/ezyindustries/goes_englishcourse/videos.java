package com.ezyindustries.goes_englishcourse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ezyindustries.goes_englishcourse.RecyclerView.refData;
import com.ezyindustries.goes_englishcourse.RecyclerView.refViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.api.services.youtube.YouTube;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class videos extends AppCompatActivity {


    private ImageView back;
    private RecyclerView Listvideo;
    private FirebaseAuth Auth;
    private FirebaseDatabase mDatabase;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        back = (ImageView) findViewById(R.id.back);

        Auth = FirebaseAuth.getInstance();
        Listvideo = (RecyclerView) findViewById(R.id.Listvideo);
        Listvideo.setLayoutManager(new LinearLayoutManager(this));
        Listvideo.setHasFixedSize(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(videos.this, main_page.class);
                startActivity(intent);
            }
        });

        getData();

    }

    private void getData(){
        final ProgressDialog dialog = new ProgressDialog(videos.this);
        dialog.setMessage("Hold on we loading up data for you....");
        dialog.show();

        Query query = FirebaseDatabase.getInstance().getReference("Video").limitToLast(50);

        FirebaseRecyclerOptions<refData> options = new FirebaseRecyclerOptions.Builder<refData>()
                .setQuery(query,refData.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<refData, refViewHolder>(options){
            @Override
            protected void onBindViewHolder(@NonNull refViewHolder holder, final int position, @NonNull refData model) {
                holder.Stitle.setText(model.getTitle());
                Picasso.get().load(model.getUrl()).into(holder.Simage, new Callback() {
                    @Override
                    public void onSuccess() {
                        dialog.hide();
                    }

                    @Override
                    public void onError(Exception e) {Log.e("Image", e.getMessage());}
                });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = firebaseRecyclerAdapter.getRef(position).getKey();
                    Intent intent = new Intent(videos.this, Youtube.class);
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
        Listvideo.setAdapter(firebaseRecyclerAdapter);

    }

    private void ProgresDialog(){

    }

}
