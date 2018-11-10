package com.ezyindustries.goes_englishcourse;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Window;

public class playGround extends AppCompatActivity {

    private Context mContext;

    private CardView mCardViewTop;
    private CardView mCardViewBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_ground);

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_ground);

        mContext = getApplicationContext();

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFF0004")));


        mCardViewBottom.setRadius(9);
    }
}
