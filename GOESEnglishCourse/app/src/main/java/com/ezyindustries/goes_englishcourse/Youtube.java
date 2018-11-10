package com.ezyindustries.goes_englishcourse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
    private DatabaseReference databaseReference;
    ImageView back, option;
    TextView test, text;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        getid();

        back = (ImageView)findViewById(R.id.back);
        option = (ImageView) findViewById(R.id.option);
//        test = (TextView) findViewById(R.id.getid);
//        test.setText(id);
        text = (TextView)findViewById(R.id.text);

        youTubePlayerView= (YouTubePlayerView) findViewById(R.id.youtube);
        playyoutube();
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
}
