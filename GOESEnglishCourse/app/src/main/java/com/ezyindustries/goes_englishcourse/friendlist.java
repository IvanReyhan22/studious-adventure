package com.ezyindustries.goes_englishcourse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class friendlist extends AppCompatActivity {

    private LinearLayout friend1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        friend1 = (LinearLayout)findViewById(R.id.friend1);

        friend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(friendlist.this, profile.class);
                startActivity(intent);
            }
        });
    }
}
