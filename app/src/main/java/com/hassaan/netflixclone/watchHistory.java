package com.hassaan.netflixclone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class watchHistory extends AppCompatActivity {
    String username = "";
    historyContent myContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_history);
        myContent = new historyContent(this);
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPref.getString("username", "");
        TextView txt = (TextView) findViewById(R.id.myText);
        String historyData= retrieveUserData();
        txt.setText(historyData);
        ImageView img = (ImageView) findViewById(R.id.img1);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), profileActivtiy.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public String retrieveUserData() {
        Cursor cursor = myContent.getData1(username);
        StringBuilder userDataBuilder = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve data from the cursor
                String videoName = cursor.getString(cursor.getColumnIndexOrThrow("video_name"));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));
                String duration = cursor.getString(cursor.getColumnIndexOrThrow("duration"));

                // Append data to the StringBuilder
                userDataBuilder.append("Movie Name: ").append(videoName)
                        .append(", Timestamp: ").append(timestamp)
                        .append(", Duration: ").append(duration)
                        .append("\n\n\n");
            } while (cursor.moveToNext());
            cursor.close();
        }

        String userData =  userDataBuilder.toString() + "\n";
        return userData;
    }
    public void clearHistory(View view){
        myContent.deleteData();
    }
}