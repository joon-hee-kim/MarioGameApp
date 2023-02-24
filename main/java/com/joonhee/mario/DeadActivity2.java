package com.joonhee.mario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class DeadActivity2 extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    //    private DeadMusic deadMusic;
    private VideoView videoView2;
    private MediaController mediaController;
    //    private String videoURL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    private String videoURL = "android.resource://com.joonhee.mario/raw/mario_game_over";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dead);
//        deadMusic = new DeadMusic(this, null);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        try {
            videoView2 = findViewById(R.id.videoView2);
            if (videoView2 != null) {
                videoView2.setOnCompletionListener(this);
//                mediaController = new MediaController(this);
//                mediaController.setAnchorView(videoView2);
                Uri uri = Uri.parse(videoURL);
//                videoView2.setMediaController(mediaController);
                videoView2.setVideoURI(uri);
                videoView2.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Intent startIntent = new Intent(DeadActivity2.this, Wld2Activity.class);
        startActivity(startIntent);
    }


}


//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (deadMusic != null) {
//            deadMusic.onPause();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (deadMusic != null) {
//            deadMusic.onResume();
//        }
//    }








