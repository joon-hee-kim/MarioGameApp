package com.joonhee.mario;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;


public class Game4 extends View {
    int n;
    int m;
    int d;

    int scrw, scrh;
    float xd, yd;
    int count = 0; //캐릭터 이동을 위한 카운트 수 저장할 정수형 변수
    boolean start = false; //캐릭터 방향키 버튼 클릭 유무
    private String DirButton; //캐릭터 정지상태시
    private String DirButton2; // 캐릭터 이동상태시

    float [] rxd= new float [3];
    float [] ryd= new float [3];
    int [] count2= new int [3]; //적군 이동을 위한 카운트 수 저장할 정수형 변수
    int [] life=new int[3];
    int [] chr_life = new int[1];
    private String[] RectDirButton = new String[3];
    //위에서 3은 적군을 화면에 최대 3개까지만 표시할 것을 의미함


    int missileCount; //발사 가능한 최대 미사일 수
    int [] missileNum = new int [10]; //미사일 번호, 왜 1이면 활성화되고 0이면 비활성화되는가: 아래 else if((int) event.getX()>scrw/2) 참조해야할듯.
    float [] mx = new float [10];
    float [] my = new float [10];
    int [] md = new int [10];
    int MD=3; //미사일 초기 방향

    Random random = new Random();
    //위에서 10은 화면에 최대 10개까지의 미사일 표시할 것을 의미


    Paint p = new Paint();
    private GameThread T;

    MediaPlayer mp; //mario.mp3 텍스트 파일로 바뀌어도 잘 작동함!

    public Game4(Context con, AttributeSet at) {
        //부모 클래스의 생성자를 불러와서 초기화시킨다.
        super (con, at);
//        mp = MediaPlayer.create(con, R.raw.boss);
//        mp.start();
//        mp.setLooping(true);
    }

    @Override  //뷰의 크기가 변경될 때 호출
    protected void onSizeChanged(int sw, int sh, int esw, int esh) {
        super.onSizeChanged(sw, sh, esw, esh);
        this.scrw = sw;
        this.scrh = sh;

        //적군의 생명력 저장
        for(int i=0; i<3; i++)
        {
            life[i] = 10; //미사일에 2번까지 견딤
        }

        for(int i=0; i<1; i++)
        {
            chr_life[i] = 30;
        }

        if (T == null) {
            T = new GameThread();
            T.start();
        }
    }

    @Override
    // 뷰가 원도우에서 분리될 때마다 발생
    protected void onDetachedFromWindow() {
        T.run = false;
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint rect = new Paint();
        rect.setColor(Color.BLACK);
        canvas.drawRect(0, 0, scrw, scrh, rect);

//        Paint rect2 = new Paint();
//        rect2.setColor(Color.WHITE);
//        canvas.drawRect(scrw % 64 / 2, scrh % 32 / 2, scrw - scrw % 64 / 2, scrh - scrh % 32 / 2, rect2);
        //나머지 scrw%64=2, scrh%32=24, scrw-scrw%64/2=1794-1, scrh-scrh%32/2=1080-12=1068
        p.setColor(Color.RED);
        p.setTextSize(scrh / 16);
//        canvas.drawText("sw" + scrw + "sh" + scrh, 0, scrh / 16, p);


        Bitmap[] bg = new Bitmap[1];

        bg[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bg01);
        bg[0] = Bitmap.createScaledBitmap(bg[0], scrw, scrh, true);
        canvas.drawBitmap(bg[0], scrw % 64 / 2, scrh % 32 / 2, null);


//        Bitmap[] main = new Bitmap[2];
//        for (int i = 0; i < 2; i++) {
//            main[i] = BitmapFactory.decodeResource(getResources(), R.drawable.main1 + i);
//            main[i] = Bitmap.createScaledBitmap(main[i], scrw / 8, scrh / 4, true);
//            if (i == n) {
//                canvas.drawBitmap(main[i], d, scrh - scrh / 4, null);
//            }
//        }

//        for (int i = 0; i < 12; i++) {
//            main[i] = BitmapFactory.decodeResource(getResources(), R.drawable.character01 + i);
//            main[i] = Bitmap.createScaledBitmap(main[i], (scrw-scrw%64) / 8, (scrh-scrh%32) / 4, true);
//            if (i == n) {
//                canvas.drawBitmap(main[i], scrw/2 + xd, scrh/2 + yd, null);
//            }
//        }


        Bitmap[] missile = new Bitmap[10];

        missileCount = 0; // 없애면 무슨 값 기준인지 몰라 에러떠서 발생가능한 수가 아래 식으로 인해 10씩 무한히 증가되고, 0으로 설정하면 10까지만 증가하는 그런 원리인듯

        for (int i = 0; i < 10; i++) {
            missile[i] = BitmapFactory.decodeResource(getResources(), R.drawable.fire);
            missile[i] = Bitmap.createScaledBitmap(missile[i], scrw / 16, scrw / 16, true);

            if(missileNum[i] == 0) {
                missileCount += 1;
            }

            if (missileNum[i] == 1) {
                canvas.drawBitmap(missile[i], mx[i], my[i], null);
                if (md[i] == 1) {//왼쪽
                    mx[i] -= scrw / 64; //미사일 방향만 바뀌도록 자동적으로 설정함
                }
                if (md[i] == 2) {//오른쪽
                    mx[i] += scrw / 64;
                }
                if (md[i] == 3) {//위쪽
                    my[i] -= scrh / 32;
                }
                if (md[i] == 4) {//아래쪽
                    my[i] += scrh / 32;
                }

//                mx[i] = scrw/2 + (scrw/8 - scrw/16)/2 + xd; 참고사항
                // 897+(224-112)/2 = 897+56 = 953. 953 + xd

                for (int j = 0; j < 3; j++) { //생명력이 존재하는 j번째 적군 캐릭터가 i번째 미사일에 맞았다면
                    if (life[j] > 0 && mx[i] <= scrw / 2 + (scrw - scrw % 64) / 8 + rxd[j] && mx[i] >= scrw / 2 + rxd[j]) {
                        //mx[i] <= 897+224+rxd[j]     mx[i] >= 897+rxd[j]   rxd[j] +-= 28.xx
                        // (scrw - scrw % 64) / 8 이 수식과 scrw / 2 이 수식은 rec3의 left right 인용한듯
                        life[j] -= 1;
                        missileNum[i] = 0;
                        if (life[0]==0 && life[1]==0 && life[2]==0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("게임을 클리어하셨습니다. 클리어화면으로 이동하겠습니까?")
                                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // "예" 버튼을 클릭했을 때 실행할 코드
                                            Intent intent = new Intent(getContext(), EndActivity2.class);
                                            getContext().startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // "아니오" 버튼을 클릭했을 때 실행할 코드
                                            Intent intent = new Intent(getContext(), Wld2Activity.class);
                                            getContext().startActivity(intent);
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }
                }
            }
            if (mx[i] > scrw - scrw / 16 || mx[i] < 0 || my[i] > scrh - scrw / 16 || my[i] < 0) {
                missileNum[i] = 0;
            }
        }


        Bitmap AS[][] = new Bitmap[1][4];
        Bitmap A = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.dir);
        A = Bitmap.createScaledBitmap(A, scrw / 8, scrh, true);

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                AS[i][j] = Bitmap.createBitmap(A, i * scrw / 8, j * scrh / 4, scrw / 8, scrh / 4);
            }
        }
        canvas.drawBitmap(AS[0][0], scrw / 8, scrh - scrh / 2, null);
        canvas.drawBitmap(AS[0][1], 0, scrh - scrh / 4, null);
        canvas.drawBitmap(AS[0][2], scrw / 4, scrh - scrh / 4, null);
        canvas.drawBitmap(AS[0][3], scrw / 8, scrh - scrh / 4, null);

//        n 9,10,11 대신 0,1,2 넣었을때 작동하지만 에러 메시지 뜸. 다른 그림의 아이디 character는 n 0~11까지 넣어도 됨
//        n 9,10,11에 오른쪽 이동 사진 넣었는데 에러뜸. n 6,7,8에 그래서 위로가는 사진 넣음 -> 위로 가는 사진 잘 나옴(사진 에러가 아니다)
//        n9,10,11 자체에 문제: mr아이디가 뭔가 문제있어서 에러가 나는 건가?? mr아이디가 짧아서 그런지 컴퓨터가 헷갈려 시행안된듯. character를 넣주니 됨.
        Bitmap[] main = new Bitmap[12];

        for(int i=0; i<12; i++) {
            for(int j=0; j<1; j++) {
                for(int k=0; k<3; k++) {
                    //            main[i] = BitmapFactory.decodeResource(getResources(), R.drawable.mr01 + i, options);
                    main[i] = BitmapFactory.decodeResource(getResources(), R.drawable.luigi01 + i);
                    if (main[i] == null) return;
                    main[i] = Bitmap.createScaledBitmap(main[i], scrw / 8, scrh / 4, true);
                    if (i == n && chr_life[j] > 0) {
                        canvas.drawBitmap(main[i], scrw * 7 / 16 + xd, scrh - scrh / 4 + yd, null);
                        if (scrw * 7 / 16 + xd <= scrw/2+rxd[k]+scrw/4 && scrw * 7 / 16 + xd >= scrw/2+rxd[k] && scrh - scrh / 4 + yd <= scrh/2+ryd[k]+scrh/2 && scrh - scrh / 4 + yd >= scrh/2+ryd[k]) {
                            chr_life[j] -= 1;
                            if (chr_life[0] == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("게임오버 하셨습니다. 다시 도전하겠습니까?")
                                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // "예" 버튼을 클릭했을 때 실행할 코드
                                                Intent intent = new Intent(getContext(), Play4Activity.class);
                                                getContext().startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // "아니오" 버튼을 클릭했을 때 실행할 코드
                                                Intent intent = new Intent(getContext(), DeadActivity2.class);
                                                getContext().startActivity(intent);
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        }
                    }
                }
            }
        }
        //어떻게해서 캐릭터 이미지를 버튼 이미지처럼 쪼개서 할 수 있는지 모르겠다.

        Bitmap[] man = new Bitmap[3];

        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                man[j] = BitmapFactory.decodeResource(getResources(), R.drawable.bowser01 + j);
                man[j] = Bitmap.createScaledBitmap(man[j], scrw/4, scrh/2, true);
                if(life[i] > 0) {
                    canvas.drawBitmap(man[j],scrw/2+rxd[i], scrh/2+ryd[i], null);
                }
            }
        }
//        for(int i=0; i<3; i++) {
//            Paint rect3 = new Paint();
//            rect3.setColor(Color.RED);    //적군의 생명이 있다면, 적군을 그려준다. (왼쪽, 위, 오른쪽, 아래) 좌표를 사용하여 상자를 그려준다.
//            if(life[i] > 0) canvas.drawRect(scrw/2+rxd[i], scrh/2+ryd[i], scrw/2+(scrw-scrw%64)/8 + rxd[i], scrh/2+(scrh-scrh%32)/4 + ryd[i], rect3);
//        }                                   // rxd[i] +-= 28.xx  ryd[i] +-= 33.xx
        canvas.drawText("HP:"+chr_life[0]+"적 생명력:"+life[0]+"적 생명력:"+life[1]+"적 생명력:"+life[2]+"발사 가능한 수"+missileCount, 0, scrh/16, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
            if ((int) event.getX() > scrw / 4 && (int) event.getX() < scrw * 3 / 8 && (int) event.getY() < scrh && (int) event.getY() > scrh - scrh / 4) {
                if (start == false && count == 0) {
                    start = true;
                    DirButton = "Right";
                }
                DirButton2 = "Right";
            } else if ((int) event.getX() > 0 && (int) event.getX() < scrw / 8 && (int) event.getY() < scrh && (int) event.getY() > scrh - scrh / 4) {
                if (start == false && count == 0) {
                    start = true;
                    DirButton = "Left";
                }
                DirButton2 = "Left";
            } else if ((int) event.getX() > scrw / 8 && event.getX() < scrw / 4 && (int) event.getY() < scrh - scrh / 4 && (int) event.getY() > scrh - scrh / 2) {
                if (start == false && count == 0) {
                    start = true;
                    DirButton = "Up";
                }
                DirButton2 = "Up";
            } else if ((int) event.getX() > scrw / 8 && (int) event.getX() < scrw / 4 && (int) event.getY() < scrh && (int) event.getY() > scrh - scrh / 4) {
                if (start == false && count == 0) {
                    start = true;
                    DirButton = "Down";
                }
                DirButton2 = "Down";
            }
            else if ((int) event.getX()>scrw/2) {
                for(int i=0; i<10; i++) {
                    if(missileNum[i] == 0) {
                        mx[i] = scrw/2 + (scrw/8 - scrw/16)/4 + xd;  //캐릭터 좌표 기준. 897+(224-112)/2 = 897+56 = 953. 953 + xd
                        my[i] = scrh/2 + (scrh/2 - scrh/4) + yd;
                        md[i] = MD;
                        missileNum[i] = 1;
                        if(missileCount!=0) missileCount -= 1;
                        break;
                    }
                }
            }

//            else if ((int) event.getX()>scrw/2) {
//                if(missileCount >= 0) {
//                    missileNum[missileCount] = 1;
//                    mx[missileCount]=scrw*7/16+xd;
//                    my[missileCount]=scrh-scrh/4+yd;
//                    md[missileCount] = MD;
//                    if(missileCount == 0) {
//                        missileCount=99;
//                    }
//                    missileCount -= 1;
//                }
//            } // E/MessageQueue-JNI: java.lang.ArrayIndexOutOfBoundsException: length=10; index=10, length가 missileCount인듯

            else {
                start = false;
            }
        }



        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP) {
            if ((int) event.getX() > scrw / 4 && (int) event.getX() < scrw * 3 / 8 && (int) event.getY() < scrh && (int) event.getY() > scrh - scrh / 4) {
                start = false;
            } else if ((int) event.getX() > 0 && (int) event.getX() < scrw / 8 && (int) event.getY() < scrh && (int) event.getY() > scrh - scrh / 4) {
                start = false;
            } else if ((int) event.getX() > scrw / 8 && event.getX() < scrw / 4 && (int) event.getY() < scrh - scrh / 4 && (int) event.getY() > scrh - scrh / 2) {
                start = false;
            } else if ((int) event.getX() > scrw / 8 && (int) event.getX() < scrw / 4 && (int) event.getY() < scrh && (int) event.getY() > scrh - scrh / 4) {
                start = false;
            }
        }
        return true;
    }



    class GameThread extends Thread {
        public boolean run = true;

        @Override
        public void run() {
            while (run) {
                try {
                    postInvalidate(); //뷰에서 이미지를 분리시킨다.
                    if (count==8) {
                        count=0;
                        DirButton=DirButton2;
                    }
                    for (int i=0; i<3; i++) {
                        if(count2[i]==8) {
                            count2[i]=0;
                        }
                        if(count2[i]==0) {
                            //정수형 변수r은 1~4 사이의 정수 값을 가진다.
                            // (4-1 + 1) + 1 -> 1~4
                            // (4-3 + 1) + 3 -> 3~4
                            int r = random.nextInt(4-1+1)+1;
                            if (r == 1) {
                                RectDirButton[i] = "Left";
                            }
                            if (r == 2) {
                                RectDirButton[i] = "Right";
                            }
                            if (r == 3) {
                                RectDirButton[i] = "Up";
                            }
                            if (r == 4) {
                                RectDirButton[i] = "Down";
                            }
                        }
                        // rxd[i] +-= 28.xx  ryd[i] +-= 33.xx
                        // 참고사항 if(life[i] > 0) canvas.drawRect(왼쪽scrw/2+rxd[i], 위scrh/2+ryd[i], 오른쪽scrw/2+(scrw-scrw%64)/8 + rxd[i], 아래scrh/2+(scrh-scrh%32)/4 + ryd[i], rect3);
                        if (life[i] > 0 && RectDirButton[i] == "Down") {
                            // scrh - scrh/4 - (scrh%32)/2 = 1080 - 270 - 12. scrw%64=2, scrw/64=28
                            if(scrh/2 + ryd[i] < scrh - scrh/4 - (scrh%32)/2) {  //원리가 잘 이해가지는 않음!!!!!!! rec3 크기 관련?
                                ryd[i] += scrh/32;
                            }
                        }
                        if (life[i] > 0 && RectDirButton[i] == "Up") {
                            if(scrh/2 + ryd[i] > (scrh%32)/2) {
                                ryd[i] -= scrh/32;
                            }
                        }
                        if (life[i] > 0 && RectDirButton[i] == "Left") {
                            if(scrw/2 + rxd[i] > (scrw%64)/2) {
                                rxd[i] -= scrw/64;
                            }
                        }
                        if (life[i] > 0 && RectDirButton[i] == "Right") {
                            if(scrw/2 + ryd[i] < scrw-scrw/8-(scrw%64)/2) {
                                rxd[i] += scrw/64;
                            }
                        }
                    }

//                    if(count==20) {
//                        count=0;
//                        DirButton=DirButton2;
//                    }
//                    if(start==true && count==0) {
//                        count+=1;
//                    }
//                    if(count>0 && count<20) {
//                        count+=1;
//                    }
                    for(int i=0; i<1; i++) {
                        if(chr_life[i]>0 && start==true&&DirButton=="Down"&&count!=8 || start==false&&count>0&&count<8&&DirButton=="Down") {
                            if(count%4==0) {
                                yd+=scrh/80;
                                n=9;
                                MD=4;
                            }
                            else if(count%4==1 | count%4==3) {
                                yd+=scrh/80;
                                n=10;
                            }
                            else if(count%4==2) {
                                yd+=scrh/80;
                                n=11;
                            }
                        }

                        if(chr_life[i]>0 && start==true&&DirButton=="Up"&&count!=8 || start==false&&count>0&&count<8&&DirButton=="Up") {
                            if(count%4==0) {
                                yd-=scrh/80;
                                n=0;
                                MD=3;
                            }
                            else if(count%4==1 | count%4==3) {
                                yd-=scrh/80;
                                n=1;
                            }
                            else if(count%4==2) {
                                yd-=scrh/80;
                                n=2;
                            }
                        }

                        if(chr_life[i]>0 && start==true&&DirButton=="Right"&&count!=8 || start==false&&count>0&&count<8&&DirButton=="Right") {
                            if(count%4==0) {
                                xd+=scrw/160;
                                n=6;
                                MD=2;
                            }
                            else if(count%4==1 | count%4==3) {
                                xd+=scrw/160;
                                n=7;
                            }
                            else if(count%4==2) {
                                xd+=scrw/160;
                                n=8;
                            }
                        }

                        if(chr_life[i]>0 && start==true&&DirButton=="Left"&&count!=8 || start==false&&count>0&&count<8&&DirButton=="Left") {
                            if(count%4==0) {
                                xd-=scrw/160;
                                n=3;
                                MD=1;
                            }
                            else if(count%4==1 | count%4==3) {
                                xd-=scrw/160;
                                n=4;
                            }
                            else if(count%4==2) {
                                xd-=scrw/80;
                                n=5;
                            }
                        }
                    }
                    for(int i=0; i<1; i++)
                    {
                        if(chr_life[i]>0) {
                            if (start==true && count==0) {
                                count+=1;
                            } else {
                                if (count>0 && count<8) count+=1;
                            }
                        }
                    }

                    //i는 0부터 3보다 작은 동안 1씩 증가한다.
                    for(int i=0; i<3; i++)
                    {
                        //적군의 생명이 0보다 크다면
                        if(life[i] > 0) {
                            //카운트 수를 1씩 증가시킨다.
                            count2[i] += 1;
                        }
                    }
                    sleep(40);
                } catch (Exception e) {

                }
            }

        }
    }
}
//mr 이미지들을 어떻게 쪼개나??







