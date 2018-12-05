package com.ezyindustries.goes_englishcourse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Youtube extends YouTubeBaseActivity {

    private String id;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    ImageView back, option;
    TextView test, text;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private TextView par1, par2, par3, par4, par5, title,head;


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        getid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Video");

        back = (ImageView)findViewById(R.id.back);
        text = (TextView)findViewById(R.id.text);

        head = (TextView)findViewById(R.id.head);
        title = (TextView)findViewById(R.id.Title);
        par1 = (TextView)findViewById(R.id.par1);
        par2 = (TextView)findViewById(R.id.par2);
        par4 = (TextView)findViewById(R.id.par3);
        par3 = (TextView)findViewById(R.id.par4);
        par5 = (TextView)findViewById(R.id.par5);

        youTubePlayerView= (YouTubePlayerView) findViewById(R.id.youtube);
        playyoutube();
        getExplanation();
    }

    private void getid(){
        Intent intent = getIntent();
        id = intent.getStringExtra("Data");
    }

    private void playyoutube(){
        DatabaseReference mUrlRef= mRootRef.child("Video/"+id+"/videoUrl");
        mUrlRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String Url = dataSnapshot.getValue(String.class);
                onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo(Url);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                        Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                    }
                };

                youTubePlayerView.initialize(youtubeConfig.getApiKey(),onInitializedListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getExplanation(){
        ref.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Title =dataSnapshot.child("title").getValue(String.class);
                String P1 =dataSnapshot.child("par1").getValue(String.class);
                String P2 =dataSnapshot.child("par2").getValue(String.class);
                String P3 =dataSnapshot.child("par3").getValue(String.class);
                String P4 =dataSnapshot.child("par4").getValue(String.class);
                String P5 =dataSnapshot.child("par5").getValue(String.class);
                title.setText(Title);
                head.setText(Title);
                par1.setText(P1);
                par2.setText(P2);
                par3.setText(P3);
                par4.setText(P4);
                par5.setText(P5);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
