package com.joonhee.mario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


public class Play2Activity extends AppCompatActivity {
    int[] life = new int[3];
    int[] chr_life = new int[1];
    int scrw, scrh;
    float xd, yd;
    float[] rxd = new float[3];
    float[] ryd = new float[3];
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        mp = MediaPlayer.create(this, R.raw.boss);
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

