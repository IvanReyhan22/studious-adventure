package com.ezyindustries.goes_englishcourse;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class setting2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private CardView account;
    private CardView friend;
    private CardView email;
    private LinearLayout logout;
    private FirebaseAuth Auth;

    String[] quality = {"High", "Medium", "Low"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
        account = (CardView) findViewById(R.id.account);
        friend = (CardView) findViewById(R.id.friend);
        email = (CardView) findViewById(R.id.email);

        logout = (LinearLayout) findViewById(R.id.logout);

        Auth = FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting2.this, profile.class);
                startActivity(intent);
            }
        });

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting2.this, friendlist.class);
                startActivity(intent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting2.this, change_email.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub
    }

    public void Dialog(){
        AlertDialog alertDialog =new AlertDialog.Builder(this).create();

        TextView title = new TextView(this);

        title.setText("Log Out");
        title.setPadding(10, 30, 10, 20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        TextView info = new TextView(this);
        info.setText("Are you Sure want to Log out? ");
        info.setGravity(Gravity.CENTER_HORIZONTAL);
        info.setTextColor(Color.BLACK);
        alertDialog.setView(info);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Auth.signOut();
                startActivity(new Intent(setting2.this, login2_0.class));
                finish();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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
