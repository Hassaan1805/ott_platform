package com.hassaan.netflixclone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.AssetDataSource;
import androidx.media3.datasource.DataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.ExoPlayer.Builder;
import androidx.media3.ui.PlayerView;

public class streamWeb extends AppCompatActivity {

    // Declare your variables
    private ExoPlayer player;
    private PlayerView playerView;
    private DataSource.Factory dataSourceFactory;
    private historyContent videoContent;
    private String videoName = "";
    private String username = "";
    private Intent inputIntent;

    String currentPositionString = "";
    String durationString = "";

    @OptIn(markerClass = UnstableApi.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_beekeeper);
        videoContent = new historyContent(this);
        // Initialize ExoPlayer and PlayerView
        player = new Builder(this).build();
        playerView = findViewById(R.id.exo);
        playerView.setPlayer(player);

        inputIntent = getIntent();
        videoName = inputIntent.getStringExtra("videoName");
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPref.getString("username", "");
        // Create DataSource Factory for assets
        dataSourceFactory = new DataSource.Factory() {
            @Override
            public DataSource createDataSource() {
                return new AssetDataSource(streamWeb.this);
            }
        };
        String movieName = "tr6.mp4";
        MediaItem mediaItem = MediaItem.fromUri(getAssetUri(movieName));

        // Set media item to ExoPlayer and prepare
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);
        player.addListener(new Player.Listener() {
            @Override
            public void onPositionDiscontinuity(@NonNull Player.PositionInfo oldPosition, @NonNull Player.PositionInfo newPosition, int reason) {
                long currentPositionMs = player.getCurrentPosition();
                long durationMs = player.getDuration();
                durationString = formatDuration(durationMs);
                currentPositionString = formatDuration(currentPositionMs);
                saveData();
            }
        });

    }


    private void saveData() {
        // Check if the video already exists in the database for the current user
        if (videoContent.videoExists(username, videoName)) {
            // Update timestamp in the database
            videoContent.updateData(videoName, currentPositionString);
            Toast.makeText(getApplicationContext(),"value updated",Toast.LENGTH_SHORT).show();

        } else {
            // Insert new record into the database
            videoContent.insertData(username, videoName, currentPositionString, durationString); // Assuming initial duration is 00:00:00
            Toast.makeText(getApplicationContext(),"value inserted",Toast.LENGTH_SHORT).show();

        }
    }

    // Get asset URI for the given asset file name
    private Uri getAssetUri(String assetFileName) {
        return Uri.parse("asset:///" + assetFileName);
    }

    private String formatDuration(long durationMs) {
        long seconds = durationMs / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        seconds %= 60;
        minutes %= 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
