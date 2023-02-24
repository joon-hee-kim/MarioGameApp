package com.joonhee.mario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        // 애니메이션 리소스 로드
        AnimationDrawable marioAnimation = (AnimationDrawable) getResources().getDrawable(R.anim.animate);
        AnimationDrawable luigiAnimation = (AnimationDrawable) getResources().getDrawable(R.anim.animate2);

        // ImageView에 애니메이션 설정
        ImageView marioImage = (ImageView) findViewById(R.id.character07);
        if (marioImage != null) {
            marioImage.setImageDrawable(marioAnimation);
        }

        // ImageView에 애니메이션 설정
        ImageView luigiImage = (ImageView) findViewById(R.id.luigi07);
        if (luigiImage != null) {
            luigiImage.setImageDrawable(luigiAnimation);
        }

        // 애니메이션 시작
        marioAnimation.start();
        luigiAnimation.start();


        mediaPlayer = MediaPlayer.create(this, R.raw.title);
        mediaPlayer.setLooping(true);

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);


        if (button == null) return;
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.mario_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent selIntent = new Intent(MainActivity.this, SelActivity.class);
                MainActivity.this.startActivity(selIntent);
            }
        });

        if (button2 == null) return;
        final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.mario_start);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp2.start();
                Intent selIntent = new Intent(MainActivity.this, SelActivity.class);
                MainActivity.this.startActivity(selIntent);
            }
        });

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



