package com.hassaan.netflixclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Wonka extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wonka);
    }
    public void prev(View view){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}