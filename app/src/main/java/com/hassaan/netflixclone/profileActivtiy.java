package com.hassaan.netflixclone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class profileActivtiy extends AppCompatActivity {
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        myDb = new DatabaseHelper(this);

        setContentView(R.layout.activity_profile_activtiy);
        ImageView prev = (ImageView) findViewById(R.id.img1);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String myUser = sharedPref.getString("username", "");
        TextView username = (TextView) findViewById(R.id.username);
        username.setText(myUser);
    }
    public void feedback(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSe_cq4H32hdSbVtzPB24hHzbLhMbf5Lw1_MxflRYpGSR7zdNQ/viewform?usp=sf_link"));
        startActivity(intent);
    }
    public void contact(View view){
        sendEmail();
    }
    public void logout(View view){
        Intent intent = new Intent(getApplicationContext(), loginActivity.class);
        startActivity(intent);
        finish();
    }
    public void history(View view){
        Intent intent = new Intent(getApplicationContext(), watchHistory.class);
        startActivity(intent);
        finish();
    }
    protected void sendEmail(){
        String[] TO = {"articononeg1@gmail.com"};
        String[] CC = {"lonerdzns@gmail.com"};
        String subject = "Compose Email";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_CC,CC);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT,"type here");

        try{
            startActivity(Intent.createChooser(emailIntent,"Sending email..."));
        }catch(android.content.ActivityNotFoundException a){
            Toast.makeText(getApplicationContext(),"Email client can't be installed",Toast.LENGTH_SHORT).show();
        }
    }

}