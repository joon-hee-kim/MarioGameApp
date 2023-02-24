package com.joonhee.mario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;


public class Play3Activity extends AppCompatActivity {
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        mp = MediaPlayer.create(this, R.raw.mario);
        mp.setLooping(true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause(); // 음악 일시 중지
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start(); // 음악 다시 시작
    }

}
