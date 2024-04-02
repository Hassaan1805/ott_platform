package com.hassaan.netflixclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class shawFragment extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shaw);
        ImageView prev = (ImageView) findViewById(R.id.img1);
        Button btn = findViewById(R.id.likeText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inputIntent = getIntent();
                String title = inputIntent.getStringExtra("title");
                Intent intent = new Intent(getApplicationContext(),StreamBeekeeper.class);
                intent.putExtra("videoName",title);
                startActivity(intent);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        }
}