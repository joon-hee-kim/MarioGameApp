package com.joonhee.mario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


public class PlayActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
//    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        mediaPlayer = MediaPlayer.create(this, R.raw.mario);
        mediaPlayer.setLooping(true);


//        Button button7 = (Button) findViewById(R.id.button7);
//        Button button8 = (Button) findViewById(R.id.button8);
//

//        if (button7 == null) return; //게임 오버
//        button7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent deadIntent = new Intent(PlayActivity.this, DeadActivity.class);
//                PlayActivity.this.startActivity(deadIntent);
//            }
//        });
//
//        if (button8 == null) return; //게임 클리어
//        button8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent endIntent = new Intent(PlayActivity.this, EndActivity.class);
//                PlayActivity.this.startActivity(endIntent);
//            }
//        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause(); // 음악 일시 중지
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start(); // 음악 다시 시작
    }

}

