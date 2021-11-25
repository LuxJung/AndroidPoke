package com.shootinggame.gameshooting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_8InGame_Stage2 extends Activity {
    Activity_8InGameView1 surfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_8ingame);
        hideNaviUI();

        surfaceView = new Activity_8InGameView1(this);
        setContentView(surfaceView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNaviUI();
        surfaceView.onResumeMySurfaceView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideNaviUI();
        surfaceView.onPauseMySurfaceView();
    }

    // 기기 네비게이션 숨기기
    public void hideNaviUI() {
        int newUiOptions = 0;
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }


    // 실질적으로 게임뷰를 구성하는 서피스뷰
    class Activity_8InGameView1 extends SurfaceView implements SurfaceHolder.Callback {
        private Activity activity;
        private Display display;
        private int width;
        private int height;
        private Paint clearPaint;
        private boolean isLoop; //스레드를 무한정 돌리기 위한 루프카운트
        private SurfaceHolder mSurfaceHolder; // SurfaceHolder 선언
        private Rect myrect, btup, btdown, btright, btleft, btfire;
        final SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        final SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언

        public Gson gson = new Gson();

        public Activity_8InGameView1(Activity paramAct) {
            super(paramAct);
            activity = paramAct;
        }

        public Activity_8InGameView1(Context context) {
            super(context);
            mSurfaceHolder = getHolder();
            mSurfaceHolder.addCallback(this);// SurfaceHolder 인스턴스에게 게임뷰 클래스를 넘겨준다
            // surfaceview를 감지하게 된다. 이제 surfaceview가 surface에 접근할 때 surfaceHolder를 경유한다.
            display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
            width = dm.widthPixels;
            height = dm.heightPixels;
            setFocusable(true); // Activity에서는 Key 이벤트가 모두 먹히는데

            // View 에서는 해당 View가 Focus될 때만 동작한다
            //위치
        }

        class ImageThread1 extends Thread {
            //여기에 쓰레드로 움직일 것들 다 넣어야 합니다 자바에서 GameFrame 역할 함
            // private SurfaceHolder mSurfaceHolder; // SurfaceHolder 선언
            //기본적으로 파이리를 했으나 추후 유저의 메인 캐릭터에 맞춰서 변경이 되어야 함
            SharedPreferences pref_achieve = getSharedPreferences("ACHIEVE", MODE_PRIVATE);//쉐어드 선언 Key : User
            SharedPreferences.Editor editor_achieve = pref_achieve.edit();//쉐어드 사용할 'editor'선언
            private GameEnemy enemy;
            private GameUser user;
            private GameMissile ms; //미사일클래스 접근키

            private Bitmap[] pairi = new Bitmap[2];
            private Bitmap pairidead;
            private Bitmap[] ggobugi = new Bitmap[2];
            private Bitmap[] unibugi = new Bitmap[4];
            private Bitmap[] gubuckwang = new Bitmap[4];
            private Bitmap[] eve = new Bitmap[3];
            private Bitmap[] buster = new Bitmap[4];
            private Bitmap[] jupithunder = new Bitmap[4];
            private Bitmap[] shamid = new Bitmap[4];
            private Bitmap[] eeve = new Bitmap[4];
            private Bitmap[] blacky = new Bitmap[4];

            private Bitmap[] pichu = new Bitmap[4];
            private Bitmap[] picachu = new Bitmap[4];
            private Bitmap[] esangheassi = new Bitmap[4];
            private Bitmap boackground, boackgroundresized;
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            int height = dm.heightPixels;

            private boolean isWait = false;
            private Bitmap Missile, pause;
            private int bx = 0; // 배경스크롤카운트변수
            private int cnt = 0; // 카운트
            private int Kill = 0; // 카운트
            private Boolean AddMidBoss = true;
            private Boolean AddBoss = true;

            private Boolean MidBossMove0 = true;
            private Boolean MidBossMove0_0 = true;
            private Boolean MidBossMove1 = true;
            private Boolean MidBossMove1_1 = true;
            private Boolean MidBossMove2 = true;
            private Boolean MidBossMove2_2 = true;

            private Boolean EeveMoveX = true;
            private Boolean EeveMoveY = true;
            private Boolean BlackyMoveX = true;
            private Boolean BlackyMoveY = true;
            private Boolean LastBossMoveX = true;
            private Boolean LastBossMoveY = true;
            private int status = 0; //기본 유저 상태 (0:공격x) (1:공격o)
            public int xpos = 30, userx = 30; // 좌표
            public int ypos = 30, usery = 30; // 좌표
            private int name;
            private Boolean JUPITHUNDER_REMOVE = false;
            private Boolean SHAMID_REMOVE = false;
            private Boolean BUSTER_REMOVE = false;
            private Boolean EEVE_REMOVE = false;
            private Boolean BLACKY_REMOVE = false;
            private int ACHIVE_BOSSKILL = pref_achieve.getInt("ACHIEVE_BOSSKILL", 0);
            private int ACHIVE_MIDBOSSKILL = pref_achieve.getInt("ACHIEVE_MIDBOSSKILL", 0);
            private int ACHIVE_NOMALKILL = pref_achieve.getInt("ACHIEVE_NOMALKILL", 0);
            private int ACHIVE_DIE = pref_achieve.getInt("ACHIEVE_DIE", 0);

            private int POINT; //점수
            private int ATK;
            private int SPEED;
            private int HP;
            private int ATKSPEED; // 연사속도
            private int FIRESPEED; // 미사일 발사속도

            private Boolean KR = false;
            private Boolean KL = false;
            private Boolean KU = false;
            private Boolean KD = false;
            private Boolean FIRE = false;

            private ArrayList<GameMissile> Missile_List = new ArrayList<GameMissile>(); // 유저의 미사일리스트
            private ArrayList<GameMissile> BossMissile_List = new ArrayList<GameMissile>(); // 보스의 미사일리스트
            private ArrayList<GameUser> User_List = new ArrayList<GameUser>(); // 유저 움직임 이렇게 관리 가능할까?

            private ArrayList<GameEnemy> Enemy_List = new ArrayList<GameEnemy>(); // 일반 적 리스트
            private ArrayList<GameEnemy> MidBoss_List = new ArrayList<GameEnemy>();
            private ArrayList<GameEnemy> Boss_List = new ArrayList<GameEnemy>(); // 보스 리스트

            private ArrayList<GameEnemy> getMidBoss_List() {
                return MidBoss_List;
            }

            Handler han;

            public ImageThread1(SurfaceHolder surfaceHolder, Context context) {
                //이미지를 홀더에 모두 담는다.
                mSurfaceHolder = surfaceHolder;
                Resources res = context.getResources();
                pause = BitmapFactory.decodeResource(res, R.drawable.pause);
                Missile = BitmapFactory.decodeResource(res, R.drawable.missile_0);
                pairi[0] = BitmapFactory.decodeResource(res, R.drawable.pairi_0);
                pairi[1] = BitmapFactory.decodeResource(res, R.drawable.pairi_1);
                pairidead = BitmapFactory.decodeResource(res, R.drawable.pairi_dead);
                ggobugi[0] = BitmapFactory.decodeResource(res, R.drawable.ggobugi_0);
                ggobugi[1] = BitmapFactory.decodeResource(res, R.drawable.ggobugi_1);
                unibugi[0] = BitmapFactory.decodeResource(res, R.drawable.unibugi_0);
                unibugi[1] = BitmapFactory.decodeResource(res, R.drawable.unibugi_1);
                unibugi[2] = BitmapFactory.decodeResource(res, R.drawable.unibugi_2 );
                unibugi[3] = BitmapFactory.decodeResource(res, R.drawable.unibugi_3);
                gubuckwang[0] = BitmapFactory.decodeResource(res, R.drawable.gubuckwang_0);
                gubuckwang[1] = BitmapFactory.decodeResource(res, R.drawable.gubuckwang_1);
                gubuckwang[2] = BitmapFactory.decodeResource(res, R.drawable.gubuckwang_2);
                gubuckwang[3] = BitmapFactory.decodeResource(res, R.drawable.gubuckwang_3);
                pichu[0] = BitmapFactory.decodeResource(res, R.drawable.pichu_0);
                pichu[1] = BitmapFactory.decodeResource(res, R.drawable.pichu_1);
                pichu[2] = BitmapFactory.decodeResource(res, R.drawable.pichu_2);
                pichu[3] = BitmapFactory.decodeResource(res, R.drawable.pichu_4);
                picachu[0] = BitmapFactory.decodeResource(res, R.drawable.picachu_0);
                picachu[1] = BitmapFactory.decodeResource(res, R.drawable.picachu_1);
                picachu[2] = BitmapFactory.decodeResource(res, R.drawable.picachu_2);
                picachu[3] = BitmapFactory.decodeResource(res, R.drawable.picachu_4);
                esangheassi[0] = BitmapFactory.decodeResource(res, R.drawable.esanheassi_0);
                esangheassi[1] = BitmapFactory.decodeResource(res, R.drawable.esanheassi_1);
                esangheassi[2] = BitmapFactory.decodeResource(res, R.drawable.esanheassi_2);
                esangheassi[3] = BitmapFactory.decodeResource(res, R.drawable.esanheassi_3);
                eve[0] = BitmapFactory.decodeResource(res, R.drawable.eve_0);
                eve[1] = BitmapFactory.decodeResource(res, R.drawable.eve_1);
                eve[2] = BitmapFactory.decodeResource(res, R.drawable.eve_2);
                buster[0] = BitmapFactory.decodeResource(res, R.drawable.evebuster_0);
                buster[1] = BitmapFactory.decodeResource(res, R.drawable.evebuster_1);
                buster[2] = BitmapFactory.decodeResource(res, R.drawable.evebuster_2);
                buster[3] = BitmapFactory.decodeResource(res, R.drawable.evebuster_3);
                jupithunder[0] = BitmapFactory.decodeResource(res, R.drawable.evejupi_0);
                jupithunder[1] = BitmapFactory.decodeResource(res, R.drawable.evejupi_1);
                jupithunder[2] = BitmapFactory.decodeResource(res, R.drawable.evejupi_2);
                jupithunder[3] = BitmapFactory.decodeResource(res, R.drawable.evejupi_3);
                shamid[0] = BitmapFactory.decodeResource(res, R.drawable.eveshami_0);
                shamid[1] = BitmapFactory.decodeResource(res, R.drawable.eveshami_1);
                shamid[2] = BitmapFactory.decodeResource(res, R.drawable.eveshami_2);
                shamid[3] = BitmapFactory.decodeResource(res, R.drawable.eveshami_3);
                eeve[0] = BitmapFactory.decodeResource(res, R.drawable.eveeeve_0);
                eeve[1] = BitmapFactory.decodeResource(res, R.drawable.eveeeve_1);
                eeve[2] = BitmapFactory.decodeResource(res, R.drawable.eveeeve_2);
                eeve[3] = BitmapFactory.decodeResource(res, R.drawable.eveeeve_3);
                blacky[0] = BitmapFactory.decodeResource(res, R.drawable.eveblac_0);
                blacky[1] = BitmapFactory.decodeResource(res, R.drawable.eveblac_1);
                blacky[2] = BitmapFactory.decodeResource(res, R.drawable.eveblac_2);
                blacky[3] = BitmapFactory.decodeResource(res, R.drawable.eveblac_3);


                Type type = new TypeToken<ArrayList<ClassPoketMon>>() {
                }.getType();
                ArrayList<ClassPoketMon> parse = gson.fromJson(pref.getString("User_Poketmon", ""), type);
                //저장되어있는 어레이리스트에서 피츄 업그레이드 하는 방법
                for (int i = 0; i < parse.size(); i++) {

                    if (parse.get(i).getPoketMonName().equals(pref.getString("PokeName", ""))) {
                        editor.putInt("Speed", parse.get(i).getPoketMonSpeed());
                        editor.putInt("Atk", parse.get(i).getPoketMonAtk());
                        editor.putInt("Hp", parse.get(i).getPoketMonHp());
                        editor.putInt("Atkspeed", parse.get(i).getSpeedAtk());
                        editor.putInt("Firespeed", parse.get(i).getSpeedMissile());
                        editor.apply();
                    }

                }
                //인게임에서 포켓몬 공격력 체력 속도 셋팅
                ATK = pref.getInt("Atk", 0); // 유저 공격력
                SPEED = pref.getInt("Speed", 0) * 10; // 유저 이동속도
                HP = pref.getInt("Hp", 0); // 유저 체력
                FIRESPEED = pref.getInt("Firespeed", 0); // 미사일 발사체 속도
                ATKSPEED = 11 - pref.getInt("Atkspeed", 0); // 공격턴

                if (pref.getString("PokeName", "").equals("피츄"))
                    name = 1;
                user = new GameUser(userx, usery, SPEED, ATK, HP);
                if (pref.getString("PokeName", "").equals("피카츄"))
                    name = 2;
                user = new GameUser(userx, usery, SPEED, ATK, HP);
                if (pref.getString("PokeName", "").equals("파이리"))
                    name = 3;
                user = new GameUser(userx, usery, SPEED, ATK, HP);
                if (pref.getString("PokeName", "").equals("꼬부기"))
                    name = 4;
                user = new GameUser(userx, usery, SPEED, ATK, HP);
                if (pref.getString("PokeName", "").equals("이상해씨"))
                    name = 5;
                user = new GameUser(userx, usery, SPEED, ATK, HP);
                if (pref.getString("PokeName", "").equals("어니부기"))
                    name = 6;
                user = new GameUser(userx, usery, SPEED, ATK, HP);
                if (pref.getString("PokeName", "").equals("거북왕"))
                    name = 7;
                user = new GameUser(userx, usery, SPEED, ATK, HP);

                User_List.add(user);


               /* if (Kill == 10) {// 중간보스
                    enemy = new GameEnemy(width - 20, height / 2, 15, 30, 6); // 좌표 체크하여 넘기기
                    MidBoss_List.add(enemy);
                    enemy = new GameEnemy(width - 20 - 100, height / 2 + 50, 15, 30, 6); // 좌표 체크하여 넘기기
                    MidBoss_List.add(enemy);
                    enemy = new GameEnemy(width - 20 - 50, height / 2 - 50, 15, 30, 6); // 좌표 체크하여 넘기기
                    MidBoss_List.add(enemy);
                }
                if (Kill == 20) {// 스테이지 보스
                    enemy = new GameEnemy(width - 20 - 70, height / 2 - 40, 20, 50, 15); // 좌표 체크하여 넘기기
                    Boss_List.add(enemy);
                    enemy = new GameEnemy(width - 20, height / 2, 20, 50, 15); // 좌표 체크하여 넘기기
                    Boss_List.add(enemy);
                }*/


                //백그라운드의 크기를 리사이징 하는작업
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;

                boackground = BitmapFactory.decodeResource(getResources(), R.drawable.playblackgroundtwo, options);
                boackgroundresized = Bitmap.createScaledBitmap(boackground, width, height, true);


                //배경화면을 지우는 작업입니다.

                clearPaint = new Paint();
                Xfermode xmode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
                clearPaint.setXfermode(xmode);


            }


            public void run() {
                while (isLoop) {
                    Canvas canvas = null;
                    try {
                        canvas = mSurfaceHolder.lockCanvas(null); // mSurfaceHolder에 그림을 그린다
                        synchronized (mSurfaceHolder) {
                            cnt++; //카운트 변수를 늘리며 계속 늘려준다.
                            paint(canvas);
                            Process_Enemy();
                            Process_Boss();
                            Process_User();
                            Process_Missile(); // 미사일 프로세스 추가
                            // Process_Cruch();

                            sleep(15);//0.05초 간격으로 움직임을 표현해준다.
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if (canvas != null) {
                            mSurfaceHolder.unlockCanvasAndPost(canvas); // 화면에 나타나게 한다.
                        }
                        if (isWait) {
                            try {
                                synchronized (this) {
                                    wait();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            //캔버스에 담을 녀석들
            public void paint(Canvas c) {
                try {
                    Draw_Background(c);
                    Draw_Missile(c);
                    Draw_User(user.pos.x, user.pos.y, c);
                    Draw_btn(c);//여기에 버튼 다이따
                    Draw_Text(c);
                    if (Kill < 22 && Kill > 12 || Kill < 10) {
                        Draw_Enemy(c);
                    }

                    if (Kill > 9 && Kill < 13 || Kill >= 22) {
                        Draw_Boss(enemy.pos.x, enemy.pos.y, c);
                    }
                    Draw_Pause(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 충돌 처리
            public boolean Crush(int x1, int y1, int x2, int y2, Bitmap img1, Bitmap img2) {
                boolean check = false;
                if (Math.abs((x1 + img1.getWidth() / 2) - (x2 + img2.getWidth() / 2)) < (img2.getWidth() / 2
                        + img1.getWidth() / 2)
                        && Math.abs((y1 + img1.getHeight() / 2)
                        - (y2 + img2.getHeight() / 2)) < (img2.getHeight() / 2 + img1.getHeight() / 2)) {
                    // 이미지 넓이, 높이값을 바로 받아 계산합니다.
                    check = true;// 위 값이 true면 check에 true를 전달합니다.
                } else {
                    check = false;
                }
                return check; // check의 값을 메소드에 리턴 시킵니다.
            }

            public void Draw_btn(Canvas canvas) {
                Resources res = getResources();//drawable에 있는 그림을 로딩해주는 역할이다.
                BitmapDrawable UpBtn = null;
                BitmapDrawable DownBtn = null;
                BitmapDrawable LeftBtn = null;
                BitmapDrawable RightBtn = null;
                BitmapDrawable FireBtn = null;
                BitmapDrawable PauseBtn = null;

                UpBtn = (BitmapDrawable) res.getDrawable(R.drawable.btn_up, null);
                DownBtn = (BitmapDrawable) res.getDrawable(R.drawable.btn_down, null);
                LeftBtn = (BitmapDrawable) res.getDrawable(R.drawable.btn_left, null);
                RightBtn = (BitmapDrawable) res.getDrawable(R.drawable.btn_right, null);
                FireBtn = (BitmapDrawable) res.getDrawable(R.drawable.btn_fire, null);
                PauseBtn = (BitmapDrawable) res.getDrawable(R.drawable.pause, null);

                int maxwidth = canvas.getWidth();
                int maxheight = canvas.getHeight(); //캔버스 높이

                Bitmap bitUP = UpBtn.getBitmap();
                btup = new Rect(200, maxheight - 380, 300, maxheight - 280);
                canvas.drawBitmap(bitUP, null, btup, null);

                Bitmap bitDOWN = DownBtn.getBitmap();
                btdown = new Rect(200, maxheight - 180, 300, maxheight - 80);
                canvas.drawBitmap(bitDOWN, null, btdown, null);

                Bitmap bitLEFT = LeftBtn.getBitmap();
                btleft = new Rect(100, maxheight - 280, 200, maxheight - 180);
                canvas.drawBitmap(bitLEFT, null, btleft, null);

                Bitmap bitRIGHT = RightBtn.getBitmap();
                btright = new Rect(300, maxheight - 280, 400, maxheight - 180);
                canvas.drawBitmap(bitRIGHT, null, btright, null);

                Bitmap bit = FireBtn.getBitmap();
                btfire = new Rect(maxwidth - 300, maxheight - 300, maxwidth - 100, maxheight - 100);
                canvas.drawBitmap(bit, null, btfire, null);

                Bitmap pousebit = PauseBtn.getBitmap();
                myrect = new Rect(maxwidth - 150, 50, maxwidth - 50, 150);
                canvas.drawBitmap(pousebit, null, myrect, null);
            }

            //좌표 알아보기(점수에 활용할 것)
            public void Draw_Text(Canvas canvas) {
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setTextSize(100);
                canvas.drawText("HP=" + HP+", KILL=" + Kill  , 100, 120, paint);
            }

            public void Draw_User(int X, int Y, Canvas canvas) {
                for (int i = 0; i < User_List.size(); ++i) {
                    user = (GameUser) (User_List.get(i));
                    switch (name) {
                        case 1:
                            if ((cnt / 4 % 4) == 1) {
                                canvas.drawBitmap(pichu[0], X, Y, null);
                            } else if ((cnt / 4 % 4) == 2) {
                                canvas.drawBitmap(pichu[1], X, Y, null);
                            } else if ((cnt / 4 % 4) == 3) {
                                canvas.drawBitmap(pichu[2], X, Y, null);
                            } else {
                                canvas.drawBitmap(pichu[3], X, Y, null);
                            }
                            break;
                        case 2:
                            if ((cnt / 4 % 4) == 1) {
                                canvas.drawBitmap(picachu[0], X, Y, null);
                            } else if ((cnt / 4 % 4) == 2) {
                                canvas.drawBitmap(picachu[1], X, Y, null);
                            } else if ((cnt / 4 % 4) == 3) {
                                canvas.drawBitmap(picachu[2], X, Y, null);
                            } else {
                                canvas.drawBitmap(picachu[3], X, Y, null);
                            }
                            break;
                        case 3:
                            if (HP <= 0) {
                                canvas.drawBitmap(pairidead, X, Y, null);
                            } else {
                                if ((cnt / 2 % 2) == 1) {
                                    canvas.drawBitmap(pairi[0], X, Y, null);
                                } else {
                                    canvas.drawBitmap(pairi[1], X, Y, null);
                                }
                            }
                            break;
                        case 4:
                            if ((cnt / 2 % 2) == 1) {
                                canvas.drawBitmap(ggobugi[0], X, Y, null);
                            } else {
                                canvas.drawBitmap(ggobugi[1], X, Y, null);
                            }
                            break;
                        case 5:
                            if ((cnt / 4 % 4) == 1) {
                                canvas.drawBitmap(esangheassi[0], X, Y, null);
                            } else if ((cnt / 4 % 4) == 2) {
                                canvas.drawBitmap(esangheassi[1], X, Y, null);
                            } else if ((cnt / 4 % 4) == 3) {
                                canvas.drawBitmap(esangheassi[2], X, Y, null);
                            } else {
                                canvas.drawBitmap(esangheassi[3], X, Y, null);
                            }
                            break;
                        case 6:
                            if ((cnt / 4 % 4) == 1) {
                                canvas.drawBitmap(unibugi[0], X, Y, null);
                            } else if ((cnt / 4 % 4) == 2) {
                                canvas.drawBitmap(unibugi[1], X, Y, null);
                            } else if ((cnt / 4 % 4) == 3) {
                                canvas.drawBitmap(unibugi[2], X, Y, null);
                            } else {
                                canvas.drawBitmap(unibugi[3], X, Y, null);
                            }
                            break;
                        case 7:
                            if ((cnt / 4 % 4) == 1) {
                                canvas.drawBitmap(gubuckwang[0], X, Y, null);
                            } else if ((cnt / 4 % 4) == 2) {
                                canvas.drawBitmap(gubuckwang[1], X, Y, null);
                            } else if ((cnt / 4 % 4) == 3) {
                                canvas.drawBitmap(gubuckwang[2], X, Y, null);
                            } else {
                                canvas.drawBitmap(gubuckwang[3], X, Y, null);
                            }
                            break;
                    }
                }
            }

            //미사일 그리기 (지우는 작업은 그리기에서 해야 함)
            public void Draw_Missile(Canvas canvas) {
                // 유저 미사일 관리
                for (int i = 0; i < Missile_List.size(); ++i) {// 유저의 미사일 크기를 받아온다
                    ms = (GameMissile) (Missile_List.get(i));
                    if (ms.Who == 0) { // 미사일이 유저의 미사일 일 때
                        Log.e("USER STATUS", "USER STATUS " + String.valueOf(status));
                        // 캔버스에 비트맵 미사일을 미사일x좌표 미사일y좌표 특별한거없이
                        ///////////////////////////////////////////////////////////////여기에 포켓몬별 이미지 추가를 해주자
                        canvas.drawBitmap(Missile, ms.pos.x + 150, ms.pos.y + 150, null);
                        ms.move();// 유저 미사일 이동
                        //  Log.e("ms.pos.x", "ms.pos.x "+String.valueOf(ms.pos.x));
                        int width = canvas.getWidth();
                        if (ms.pos.x > width) { // 미사일이 화면 밖으로 나가면
                            Missile_List.remove(i); // 미사일 지우기
                            Log.e("Missile_List.remove(i)?", "Missile_List.remove(i)? " + String.valueOf(Missile_List.size()));
                        }
                        if (Enemy_List.size() != 0) {
                            for (int j = 0; j < Enemy_List.size(); ++j) {
                                enemy = (GameEnemy) (Enemy_List.get(j));// 적의 숫자를 받아온다
                                if (Crush(ms.pos.x, ms.pos.y + 150, enemy.pos.x, enemy.pos.y, Missile, eve[0])) {
                                    Missile_List.remove(i); // 부딛힘 판정된 미사일 제거
                                    enemy.Hp -= ATK;
                                    if (enemy.Hp <= 0) {
                                        Enemy_List.remove(j); // 적 지우기
                                        Kill += 1;// 1킬 추가
                                        POINT += 30;
                                        ACHIVE_NOMALKILL += 1;
                                        editor_achieve.putInt("ACHIEVE_NOMALKILL", ACHIVE_NOMALKILL);
                                        editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                        editor_achieve.commit();
                                    }
                                }
                            }
                        }
                        if (MidBoss_List.size() != 0) {
                            for (int j = 0; j < MidBoss_List.size(); ++j) {
                                enemy = (GameEnemy) (MidBoss_List.get(j));// 적의 숫자를 받아온다
                                if (Crush(ms.pos.x, ms.pos.y + 150, enemy.pos.x, enemy.pos.y, Missile, jupithunder[0])) {
                                    Missile_List.remove(i);// 부딛힘 판정된 미사일 제거
                                    enemy.Hp -= ATK;
                                    if (enemy.Hp <= 0) {
                                        if (MidBoss_List.size() == 3) {
                                            if (MidBoss_List.get(j).Name.equals("쥬피썬더")) {
                                                MidBoss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                POINT +=40;
                                                ACHIVE_MIDBOSSKILL += 1;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_MIDBOSSKILL);
                                                editor_achieve.commit();
                                                JUPITHUNDER_REMOVE = true;
                                            } else if (MidBoss_List.get(j).Name.equals("부스터")) {
                                                MidBoss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_MIDBOSSKILL += 1;
                                                POINT +=40;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_MIDBOSSKILL);
                                                editor_achieve.commit();
                                                BUSTER_REMOVE = true;
                                            } else if (MidBoss_List.get(j).Name.equals("샤미드")) {
                                                MidBoss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_MIDBOSSKILL += 1;
                                                POINT +=40;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_MIDBOSSKILL);
                                                editor_achieve.commit();
                                                SHAMID_REMOVE = true;
                                            }
                                        } else if (MidBoss_List.size() == 2 && JUPITHUNDER_REMOVE) {
                                            //쥬피썬더만 사라진 상황
                                            if (MidBoss_List.get(j).Name.equals("부스터")) {
                                                MidBoss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_MIDBOSSKILL += 1;
                                                POINT +=40;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_MIDBOSSKILL);
                                                editor_achieve.commit();
                                                BUSTER_REMOVE = true;
                                                //샤미드만 남는다
                                            } else if (MidBoss_List.get(j).Name.equals("샤미드")) {
                                                MidBoss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_MIDBOSSKILL += 1;
                                                POINT +=40;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_MIDBOSSKILL);
                                                editor_achieve.commit();
                                                SHAMID_REMOVE = true;
                                                //부스터만 남는다
                                            }
                                        } else if (MidBoss_List.size() == 2 && BUSTER_REMOVE) {
                                            //부스터만 사라진 상황
                                            if (MidBoss_List.get(j).Name.equals("쥬피썬더")) {
                                                MidBoss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_MIDBOSSKILL += 1;
                                                POINT +=40;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_MIDBOSSKILL);
                                                editor_achieve.commit();
                                                JUPITHUNDER_REMOVE = true;
                                                //샤미드만 남는다
                                            } else if (MidBoss_List.get(j).Name.equals("샤미드")) {
                                                MidBoss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_MIDBOSSKILL += 1;
                                                POINT +=40;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_MIDBOSSKILL);
                                                editor_achieve.commit();
                                                SHAMID_REMOVE = true;
                                            }
                                        } else if (MidBoss_List.size() == 2 && SHAMID_REMOVE) {
                                            //샤미드만 사라진 상황
                                            if (MidBoss_List.get(j).Name.equals("쥬피썬더")) {
                                                MidBoss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_MIDBOSSKILL += 1;
                                                POINT +=40;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_MIDBOSSKILL);
                                                editor_achieve.commit();
                                                JUPITHUNDER_REMOVE = true;
                                            } else if (MidBoss_List.get(j).Name.equals("부스터")) {
                                                MidBoss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_MIDBOSSKILL += 1;
                                                POINT +=40;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_MIDBOSSKILL);
                                                editor_achieve.commit();
                                                BUSTER_REMOVE = true;
                                            }
                                        }//
                                        else if (MidBoss_List.size() == 1 && JUPITHUNDER_REMOVE && SHAMID_REMOVE) {
                                            //쥬피썬더, 샤미드 사라진 상황 (부스터만 남음)
                                            if (MidBoss_List.get(j).Name.equals("부스터")) {
                                                MidBoss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_MIDBOSSKILL += 1;
                                                POINT +=40;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_MIDBOSSKILL);
                                                editor_achieve.commit();
                                                BUSTER_REMOVE = true;
                                            }
                                        } else if (MidBoss_List.size() == 1 && JUPITHUNDER_REMOVE && BUSTER_REMOVE) {
                                            //쥬피썬더, 부스터만 사라진 상황 (샤미드만 남음)
                                            if (MidBoss_List.get(j).Name.equals("샤미드")) {
                                                MidBoss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_MIDBOSSKILL += 1;
                                                POINT +=40;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_MIDBOSSKILL);
                                                editor_achieve.commit();
                                                SHAMID_REMOVE = true;
                                            }
                                        } else if (MidBoss_List.size() == 1 && SHAMID_REMOVE && BUSTER_REMOVE) {
                                            //샤미드만, 부스터만 사라진 상황 (쥬피썬더만 남음)
                                            if (MidBoss_List.get(j).Name.equals("쥬피썬더")) {
                                                MidBoss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_MIDBOSSKILL += 1;
                                                POINT +=40;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_MIDBOSSKILL);
                                                editor_achieve.commit();
                                                JUPITHUNDER_REMOVE = true;
                                            }
                                        }
                                    }//
                                }
                            }
                        }

                        if (Boss_List.size() != 0) {
                            for (int j = 0; j < Boss_List.size(); ++j) {
                                enemy = (GameEnemy) (Boss_List.get(j));// 보스의 숫자를 받아온다
                                if (Crush(ms.pos.x, ms.pos.y + 150, enemy.pos.x, enemy.pos.y, Missile, eeve[0])) {
                                    Missile_List.remove(i);// 부딛힘 판정된 미사일 제거
                                    enemy.Hp -= ATK;
                                    if (enemy.Hp <= 0) {
                                        if (Boss_List.size() == 2) {
                                            if (Boss_List.get(j).Name.equals("에브이")) {
                                                Boss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_BOSSKILL += 1;
                                                POINT +=60;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_BOSSKILL);
                                                editor_achieve.commit();
                                                EEVE_REMOVE = true;
                                            }else if (Boss_List.get(j).Name.equals("블래키")) {
                                                Boss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_BOSSKILL += 1;
                                                POINT +=60;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_BOSSKILL);
                                                editor_achieve.commit();
                                                BLACKY_REMOVE = true;
                                            }
                                        } else if (Boss_List.size() == 1 && EEVE_REMOVE) {
                                            //블래키만 남은 상황
                                            if (Boss_List.get(j).Name.equals("블래키")) {
                                                Boss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_BOSSKILL += 1;
                                                POINT +=60;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_BOSSKILL);
                                                editor_achieve.commit();
                                                BLACKY_REMOVE = true;
                                            }
                                        } else if (Boss_List.size() == 1 && BLACKY_REMOVE) {
                                            //에브이만 남은상황
                                            if (Boss_List.get(j).Name.equals("에브이")) {
                                                Boss_List.remove(j); // 적 지우기
                                                Kill += 1;// 1킬 추가
                                                ACHIVE_BOSSKILL += 1;
                                                POINT +=60;
                                                editor_achieve.putInt("ACHIEVE_POINT", POINT);
                                                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", ACHIVE_BOSSKILL);
                                                editor_achieve.commit();
                                                EEVE_REMOVE = true;
                                            }
                                        }
                                    }//
                                }
                            }
                        }
                    }
                }

                // 보스 미사일 관리
                for (int j = 0; j < BossMissile_List.size(); ++j) {// 보스의 미사일 크기를 받아온다
                    ms = (GameMissile) (BossMissile_List.get(j));
                    if (ms.Who == 1) { // 미사일이 보스의 미사일 일 때
                        // 캔버스에 비트맵 미사일을 미사일x좌표 미사일y좌표 특별한거없이
                        canvas.drawBitmap(Missile, ms.pos.x, ms.pos.y, null);
                        ms.emove();// 보스의 미사일 이동
                        if (enemy.pos.x < -20) { // 보싀의 미사일이 좌측 화면 밖으로 나가면
                            BossMissile_List.remove(j); // 보스의 미사일 지우기
                        }
                        if (Crush(ms.pos.x, ms.pos.y, user.pos.x, user.pos.y + 150, Missile, eve[0])) { //여기서 유저 체력관리
                            HP -= enemy.Atk;
                            BossMissile_List.remove(j); // 보스 미사일 지우기
                        }
                    }
                }

            }


            public void Draw_Boss(int iX, int iY, Canvas canvas) {
                if (Kill == 10 || Kill == 11 || Kill == 12) {
                    if (Kill == 10) {//5일떄는 다그리기
                        for (int i = 0; i < MidBoss_List.size(); ++i) {
                            enemy = (GameEnemy) (MidBoss_List.get(0));
                            if ((cnt / 3 % 3) == 1) {
                                canvas.drawBitmap(jupithunder[0], enemy.pos.x, enemy.pos.y, null);
                            } else if ((cnt / 3 % 3) == 2) {
                                canvas.drawBitmap(jupithunder[1], enemy.pos.x, enemy.pos.y, null);
                            } else if ((cnt / 4 % 4) == 3) {
                                canvas.drawBitmap(jupithunder[2], enemy.pos.x, enemy.pos.y, null);
                            } else {
                                canvas.drawBitmap(jupithunder[3], enemy.pos.x, enemy.pos.y, null);
                            }
                            enemy = (GameEnemy) (MidBoss_List.get(1));
                            if ((cnt / 3 % 3) == 1) {
                                canvas.drawBitmap(buster[0], enemy.pos.x, enemy.pos.y, null);
                            } else if ((cnt / 3 % 3) == 2) {
                                canvas.drawBitmap(buster[1], enemy.pos.x, enemy.pos.y, null);
                            } else if ((cnt / 4 % 4) == 3) {
                                canvas.drawBitmap(buster[2], enemy.pos.x, enemy.pos.y, null);
                            } else {
                                canvas.drawBitmap(buster[3], enemy.pos.x, enemy.pos.y, null);
                            }
                            enemy = (GameEnemy) (MidBoss_List.get(2));
                            if ((cnt / 4 % 4) == 1) {
                                canvas.drawBitmap(shamid[0], enemy.pos.x, enemy.pos.y, null);
                            } else if ((cnt / 4 % 4) == 2) {
                                canvas.drawBitmap(shamid[1], enemy.pos.x, enemy.pos.y, null);
                            } else if ((cnt / 4 % 4) == 3) {
                                canvas.drawBitmap(shamid[2], enemy.pos.x, enemy.pos.y, null);
                            } else {
                                canvas.drawBitmap(shamid[3], enemy.pos.x, enemy.pos.y, null);
                            }


                        }
                        // 중간보스의 미사일을 그려주는 부분
                        if (MidBoss_List.size() != 0) {
                            for (int i = 0; i < MidBoss_List.size(); ++i) {
                                if (cnt % 20 == 0) {
                                    ms = new GameMissile(MidBoss_List.get(i).pos.x, MidBoss_List.get(i).pos.y, 1, 0); // 좌표 체크하여 넘기기
                                    Log.e("뽀스 미사일 좌표", "뽀스 미사일 좌표 = " + String.valueOf(enemy.pos.x));
                                    BossMissile_List.add(ms);
                                }
                            }
                        }
                    }
                    if (Kill == 11) {
                        if (JUPITHUNDER_REMOVE) {
                            for (int i = 0; i < MidBoss_List.size(); ++i) {
                                enemy = (GameEnemy) (MidBoss_List.get(0));
                                if ((cnt / 3 % 3) == 1) {
                                    canvas.drawBitmap(buster[0], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 3 % 3) == 2) {
                                    canvas.drawBitmap(buster[1], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 4 % 4) == 3) {
                                    canvas.drawBitmap(buster[2], enemy.pos.x, enemy.pos.y, null);
                                } else {
                                    canvas.drawBitmap(buster[3], enemy.pos.x, enemy.pos.y, null);
                                }
                                enemy = (GameEnemy) (MidBoss_List.get(1));
                                if ((cnt / 3 % 3) == 1) {
                                    canvas.drawBitmap(shamid[0], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 3 % 3) == 2) {
                                    canvas.drawBitmap(shamid[1], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 4 % 4) == 3) {
                                    canvas.drawBitmap(shamid[2], enemy.pos.x, enemy.pos.y, null);
                                } else {
                                    canvas.drawBitmap(shamid[3], enemy.pos.x, enemy.pos.y, null);
                                }
                            }
                        } else if (BUSTER_REMOVE) {
                            for (int i = 0; i < MidBoss_List.size(); ++i) {
                                enemy = (GameEnemy) (MidBoss_List.get(0));
                                if ((cnt / 3 % 3) == 1) {
                                    canvas.drawBitmap(jupithunder[0], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 3 % 3) == 2) {
                                    canvas.drawBitmap(jupithunder[1], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 4 % 4) == 3) {
                                    canvas.drawBitmap(jupithunder[2], enemy.pos.x, enemy.pos.y, null);
                                } else {
                                    canvas.drawBitmap(jupithunder[3], enemy.pos.x, enemy.pos.y, null);
                                }
                                enemy = (GameEnemy) (MidBoss_List.get(1));
                                if ((cnt / 3 % 3) == 1) {
                                    canvas.drawBitmap(shamid[0], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 3 % 3) == 2) {
                                    canvas.drawBitmap(shamid[1], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 4 % 4) == 3) {
                                    canvas.drawBitmap(shamid[2], enemy.pos.x, enemy.pos.y, null);
                                } else {
                                    canvas.drawBitmap(shamid[3], enemy.pos.x, enemy.pos.y, null);
                                }
                            }
                        } else if (SHAMID_REMOVE) {
                            for (int i = 0; i < MidBoss_List.size(); ++i) {
                                enemy = (GameEnemy) (MidBoss_List.get(0));
                                if ((cnt / 3 % 3) == 1) {
                                    canvas.drawBitmap(jupithunder[0], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 3 % 3) == 2) {
                                    canvas.drawBitmap(jupithunder[1], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 4 % 4) == 3) {
                                    canvas.drawBitmap(jupithunder[2], enemy.pos.x, enemy.pos.y, null);
                                } else {
                                    canvas.drawBitmap(jupithunder[3], enemy.pos.x, enemy.pos.y, null);
                                }
                                enemy = (GameEnemy) (MidBoss_List.get(1));
                                if ((cnt / 3 % 3) == 1) {
                                    canvas.drawBitmap(buster[0], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 3 % 3) == 2) {
                                    canvas.drawBitmap(buster[1], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 4 % 4) == 3) {
                                    canvas.drawBitmap(buster[2], enemy.pos.x, enemy.pos.y, null);
                                } else {
                                    canvas.drawBitmap(buster[3], enemy.pos.x, enemy.pos.y, null);
                                }
                            }
                        }
                        // 중간보스의 미사일을 그려주는 부분
                        if (MidBoss_List.size() != 0) {
                            for (int i = 0; i < MidBoss_List.size(); ++i) {
                                if (cnt % 20 == 0) {
                                    ms = new GameMissile(MidBoss_List.get(i).pos.x, MidBoss_List.get(i).pos.y, 1, 0); // 좌표 체크하여 넘기기
                                    Log.e("뽀스 미사일 좌표", "뽀스 미사일 좌표 = " + String.valueOf(enemy.pos.x));
                                    BossMissile_List.add(ms);
                                }
                            }
                        }
                    }//kill =6
                    if (Kill == 12) {
                        if (JUPITHUNDER_REMOVE && BUSTER_REMOVE) {
                            for (int i = 0; i < MidBoss_List.size(); ++i) {
                                enemy = (GameEnemy) (MidBoss_List.get(0));
                                if ((cnt / 3 % 3) == 1) {
                                    canvas.drawBitmap(shamid[0], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 3 % 3) == 2) {
                                    canvas.drawBitmap(shamid[1], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 4 % 4) == 3) {
                                    canvas.drawBitmap(shamid[2], enemy.pos.x, enemy.pos.y, null);
                                } else {
                                    canvas.drawBitmap(shamid[3], enemy.pos.x, enemy.pos.y, null);
                                }
                            }//샤미드만 남는다.
                        } else if (JUPITHUNDER_REMOVE && SHAMID_REMOVE) {
                            for (int i = 0; i < MidBoss_List.size(); ++i) {
                                enemy = (GameEnemy) (MidBoss_List.get(0));
                                if ((cnt / 3 % 3) == 1) {
                                    canvas.drawBitmap(buster[0], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 3 % 3) == 2) {
                                    canvas.drawBitmap(buster[1], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 4 % 4) == 3) {
                                    canvas.drawBitmap(buster[2], enemy.pos.x, enemy.pos.y, null);
                                } else {
                                    canvas.drawBitmap(buster[3], enemy.pos.x, enemy.pos.y, null);
                                }
                            }//부스터만 남는다
                        } else if (SHAMID_REMOVE && BUSTER_REMOVE) {
                            for (int i = 0; i < MidBoss_List.size(); ++i) {
                                enemy = (GameEnemy) (MidBoss_List.get(0));
                                if ((cnt / 3 % 3) == 1) {
                                    canvas.drawBitmap(jupithunder[0], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 3 % 3) == 2) {
                                    canvas.drawBitmap(jupithunder[1], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 4 % 4) == 3) {
                                    canvas.drawBitmap(jupithunder[2], enemy.pos.x, enemy.pos.y, null);
                                } else {
                                    canvas.drawBitmap(jupithunder[3], enemy.pos.x, enemy.pos.y, null);
                                }
                            }//쥬피썬더만 남는다
                        }
                        // 중간보스의 미사일을 그려주는 부분
                        if (MidBoss_List.size() != 0) {
                            for (int i = 0; i < MidBoss_List.size(); ++i) {
                                if (cnt % 20 == 0) {
                                    ms = new GameMissile(MidBoss_List.get(i).pos.x, MidBoss_List.get(i).pos.y, 1, 0); // 좌표 체크하여 넘기기
                                    Log.e("뽀스 미사일 좌표", "뽀스 미사일 좌표 = " + String.valueOf(enemy.pos.x));
                                    BossMissile_List.add(ms);
                                }
                            }
                        }
                    }//kill =7
                }
                if (Kill == 22 || Kill == 23) {
                    if (Kill == 22) {
                        for (int i = 0; i < Boss_List.size(); ++i) {
                            enemy = (GameEnemy) (Boss_List.get(0));
                            //Log.e("보스 크기", "보스 리스트 크기" + Boss_List.size());
                            if ((cnt / 4 % 4) == 1) {
                                canvas.drawBitmap(eeve[0], enemy.pos.x, enemy.pos.y, null);
                            } else if ((cnt / 4 % 4) == 2) {
                                canvas.drawBitmap(eeve[1], enemy.pos.x, enemy.pos.y, null);
                            } else if ((cnt / 4 % 4) == 3) {
                                canvas.drawBitmap(eeve[2], enemy.pos.x, enemy.pos.y, null);
                            } else {
                                canvas.drawBitmap(eeve[3], enemy.pos.x, enemy.pos.y, null);
                            }
                            enemy = (GameEnemy) (Boss_List.get(1));
                            //Log.e("보스 크기", "보스 리스트 크기" + Boss_List.size());
                            if ((cnt / 4 % 4) == 1) {
                                canvas.drawBitmap(blacky[0], enemy.pos.x, enemy.pos.y, null);
                            } else if ((cnt / 4 % 4) == 2) {
                                canvas.drawBitmap(blacky[1], enemy.pos.x, enemy.pos.y, null);
                            } else if ((cnt / 4 % 4) == 3) {
                                canvas.drawBitmap(blacky[2], enemy.pos.x, enemy.pos.y, null);
                            } else {
                                canvas.drawBitmap(blacky[3], enemy.pos.x, enemy.pos.y, null);
                            }
                        }
                        if (Boss_List.size() != 0) {
                            for (int i = 0; i < Boss_List.size(); ++i) {
                                if (cnt % 10 == 0) {
                                    ms = new GameMissile(Boss_List.get(i).pos.x, Boss_List.get(i).pos.y, 1, 0); // 좌표 체크하여 넘기기
                                    //Log.e("뽀스 미사일 좌표", "뽀스 미사일 좌표 = " + String.valueOf(enemy.pos.x));
                                    BossMissile_List.add(ms);
                                }
                            }
                        }
                    }
                    if (Kill == 23) {
                        if (BLACKY_REMOVE) {
                            for (int i = 0; i < Boss_List.size(); ++i) {
                                enemy = (GameEnemy) (Boss_List.get(0));
                                //Log.e("보스 크기", "보스 리스트 크기" + Boss_List.size());
                                if ((cnt / 4 % 4) == 1) {
                                    canvas.drawBitmap(eeve[0], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 4 % 4) == 2) {
                                    canvas.drawBitmap(eeve[1], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 4 % 4) == 3) {
                                    canvas.drawBitmap(eeve[2], enemy.pos.x, enemy.pos.y, null);
                                } else {
                                    canvas.drawBitmap(eeve[3], enemy.pos.x, enemy.pos.y, null);
                                }
                            }
                        } else if (EEVE_REMOVE) {
                            for (int i = 0; i < Boss_List.size(); ++i) {
                                enemy = (GameEnemy) (Boss_List.get(0));
                                //Log.e("보스 크기", "보스 리스트 크기" + Boss_List.size());
                                if ((cnt / 4 % 4) == 1) {
                                    canvas.drawBitmap(blacky[0], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 4 % 4) == 2) {
                                    canvas.drawBitmap(blacky[1], enemy.pos.x, enemy.pos.y, null);
                                } else if ((cnt / 4 % 4) == 3) {
                                    canvas.drawBitmap(blacky[2], enemy.pos.x, enemy.pos.y, null);
                                } else {
                                    canvas.drawBitmap(blacky[3], enemy.pos.x, enemy.pos.y, null);
                                }
                            }
                        }
                        if (Boss_List.size() != 0) {
                            if (cnt % 10 == 0) {
                                ms = new GameMissile(enemy.pos.x, enemy.pos.y-50, 1, 0); // 좌표 체크하여 넘기기
                                //Log.e("뽀스 미사일 좌표", "뽀스 미사일 좌표 = " + String.valueOf(enemy.pos.x));
                                BossMissile_List.add(ms);
                            }
                        }
                    }
                }
            }

            //백그라운드 그리기//자꾸 널잡는데 이유를 알아보자
            public void Draw_Background(Canvas canvas) {
                canvas.drawBitmap(boackgroundresized, 0, 0, clearPaint);
                if (bx > -width) {
                    canvas.drawBitmap(boackgroundresized, bx, 0, null);
                    canvas.drawBitmap(boackgroundresized, width + bx, 0, null);
                    bx -= 20;
                } else {
                    canvas.drawBitmap(boackgroundresized, 0, 0, null);
                    bx = 0;
                }
            }

            // 적 그리기            //현재까진 ㅇㅋ
            @SuppressLint("ResourceAsColor")
            public void Draw_Enemy(Canvas canvas) {
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setTextSize(100);
                //canvas.drawText("적HP" + enemy.Hp, 2000, 120, paint);
                for (int i = 0; i < Enemy_List.size(); ++i) {
                    enemy = (GameEnemy) (Enemy_List.get(i));
                    if (Kill < 10) {
                        if ((cnt / 3 % 3) == 1) {
                            canvas.drawBitmap(eve[0], enemy.pos.x, enemy.pos.y, null);
                        } else if ((cnt / 3 % 3) == 2) {
                            canvas.drawBitmap(eve[1], enemy.pos.x, enemy.pos.y, null);
                        } else {
                            canvas.drawBitmap(eve[2], enemy.pos.x, enemy.pos.y, null);
                        }
                    }
                    if (Kill < 22 && Kill > 12) {
                        if ((cnt / 3 % 3) == 1) {
                            canvas.drawBitmap(eve[0], enemy.pos.x, enemy.pos.y, null);
                        } else if ((cnt / 3 % 3) == 2) {
                            canvas.drawBitmap(eve[1], enemy.pos.x, enemy.pos.y, null);
                        } else {
                            canvas.drawBitmap(eve[2], enemy.pos.x, enemy.pos.y, null);
                        }
                    }
                    //BossMove = false;
                    enemy.move();
                    if (enemy.pos.x < -200) { // 적이 화면 밖으로 나가면
                        Enemy_List.remove(i); // 적 지우기
                        //Log.e("적 사라졌니?", String.valueOf(Enemy_List.size()));
                    }
                    if (Crush(enemy.pos.x + 150, enemy.pos.y, user.pos.x, user.pos.y, eve[0], picachu[0])) { //여기서 유저 체력관리
                        HP -= enemy.Atk;
                        Enemy_List.remove(i); // 보스 미사일 지우기

                    }

                }

            }


            public void Process_Boss() {
                // 중간보스 상하왕복에대한 패턴
                if (Kill > 9 && Kill < 13) {
                    if (cnt % 40 == 0) {
                        if (AddMidBoss) {
                            enemy = new GameEnemy("쥬피썬더", width + 400, height / 2 - 450, 7, 30, 6); // 좌표 체크하여 넘기기
                            MidBoss_List.add(enemy);
                            enemy = new GameEnemy("부스터", width + 400, height / 2 , 7, 30, 6); // 좌표 체크하여 넘기기
                            MidBoss_List.add(enemy);
                            enemy = new GameEnemy("샤미드", width + 400, height / 2 + 750, 7, 30, 6); // 좌표 체크하여 넘기기
                            MidBoss_List.add(enemy);
                            AddMidBoss = false;
                        }
                        if (MidBoss_List.size() == 3) {
                            Enemy_List.clear();
                        }
                    }
                }
                for (int i = 0; i < MidBoss_List.size(); ++i) {
                    Log.v("중간보스 리스트 사이즈", String.valueOf(MidBoss_List.size()));
                    if (MidBoss_List.size() == 3) {
                        if (MidBoss_List.get(0) != null) {
                            if (MidBossMove0_0) {//쥬피썬더움직임
                                MidBoss_List.get(0).pos.x -= MidBoss_List.get(0).Speed;
                                if (MidBoss_List.get(0).pos.x <= 2000) MidBossMove0_0 = false;
                            }
                            if (MidBossMove0) {
                                MidBoss_List.get(0).pos.y += MidBoss_List.get(0).Speed;
                                if (MidBoss_List.get(0).pos.y >= 950) MidBossMove0 = false;
                            } else {
                                MidBoss_List.get(0).pos.y -= MidBoss_List.get(0).Speed;
                                if (MidBoss_List.get(0).pos.y <= 100) MidBossMove0 = true;
                            }
                        }
                        if (MidBoss_List.get(1) != null) {
                            if (MidBossMove1_1) {
                                MidBoss_List.get(1).pos.x -= MidBoss_List.get(1).Speed;
                                if (MidBoss_List.get(1).pos.x <= 1700) MidBossMove1_1 = false;
                            }
                            if (MidBossMove1) {
                                MidBoss_List.get(1).pos.y += MidBoss_List.get(1).Speed;
                                if (MidBoss_List.get(1).pos.y >= 950) MidBossMove1 = false;
                            } else {
                                MidBoss_List.get(1).pos.y -= MidBoss_List.get(1).Speed;
                                if (MidBoss_List.get(1).pos.y <= 100) MidBossMove1 = true;
                            }
                        }
                        if (MidBoss_List.get(2) != null) {
                            if (MidBossMove2_2) {
                                MidBoss_List.get(2).pos.x -= MidBoss_List.get(2).Speed;
                                if (MidBoss_List.get(2).pos.x <= 1400)MidBossMove2_2 = false;
                            }
                            if (MidBossMove2) {
                                MidBoss_List.get(2).pos.y += MidBoss_List.get(2).Speed;
                                if (MidBoss_List.get(2).pos.y >= 900) MidBossMove2 = false;
                            } else {
                                MidBoss_List.get(2).pos.y -= MidBoss_List.get(2).Speed;
                                if (MidBoss_List.get(2).pos.y <= 100) MidBossMove2 = true;

                            }
                        }
                    } else if (MidBoss_List.size() == 2) {
                        if (MidBoss_List.get(0) != null) {
                            if (MidBossMove0_0) {
                                MidBoss_List.get(0).pos.x -= MidBoss_List.get(0).Speed+4;
                                if (MidBoss_List.get(0).pos.x <= 2000) MidBossMove0_0 = false;
                            }
                            if (MidBossMove0) {
                                MidBoss_List.get(0).pos.y += MidBoss_List.get(0).Speed+4;
                                if (MidBoss_List.get(0).pos.y >= 900) MidBossMove0 = false;
                            } else {
                                MidBoss_List.get(0).pos.y -= MidBoss_List.get(0).Speed+4;
                                if (MidBoss_List.get(0).pos.y <= 100) MidBossMove0 = true;
                            }
                        }
                        if (MidBoss_List.get(1) != null) {
                            if (MidBossMove1_1) {
                                MidBoss_List.get(0).pos.x -= MidBoss_List.get(1).Speed;
                                if (MidBoss_List.get(0).pos.x <= 1700) MidBossMove1_1 = false;
                            }
                            if (MidBossMove1) {
                                MidBoss_List.get(1).pos.y += MidBoss_List.get(1).Speed;
                                if (MidBoss_List.get(1).pos.y >= 900) MidBossMove1 = false;
                            } else {
                                MidBoss_List.get(1).pos.y -= MidBoss_List.get(1).Speed;
                                if (MidBoss_List.get(1).pos.y <= 100) MidBossMove1 = true;
                            }
                        }
                    } else if (MidBoss_List.size() == 1) {
                        if (MidBoss_List.get(0) != null) {
                            if (MidBossMove0_0) {
                                MidBoss_List.get(0).pos.x -= MidBoss_List.get(0).Speed;
                                if (MidBoss_List.get(0).pos.x <= 2000) MidBossMove0_0 = false;
                            }
                            if (MidBossMove0) {
                                MidBoss_List.get(0).pos.y += MidBoss_List.get(0).Speed*2;
                                if (MidBoss_List.get(0).pos.y >= 900) MidBossMove0 = false;
                            } else {
                                MidBoss_List.get(0).pos.y -= MidBoss_List.get(0).Speed*2;
                                if (MidBoss_List.get(0).pos.y <= 100) MidBossMove0 = true;
                            }
                        }
                    }
                }

                // 보스 상하좌우왕복에대한 패턴 Kill 2곱할 것
                if (Kill == 22||Kill ==23) {
                    if (cnt % 10 == 0) {
                        if (AddBoss) {
                            enemy = new GameEnemy("에브이", width + 400, height / 2 - 300, 20, 50, 15); // 좌표 체크하여 넘기기
                            Boss_List.add(enemy);
                            enemy = new GameEnemy("블래키", width + 600, height / 2+150, 20, 50, 15); // 좌표 체크하여 넘기기
                            Boss_List.add(enemy);
                            AddBoss = false;
                        }
                        if (Boss_List.size() == 2) {
                            Enemy_List.clear();
                        }
                    }
                }
                for (int i = 0; i < Boss_List.size(); ++i) {
                    if (Boss_List.size() == 2) {
                        if (Boss_List.get(0) != null) {
                            if (EeveMoveX) {
                                Boss_List.get(0).pos.x -= Boss_List.get(0).Speed/2;
                                if (EeveMoveY) {
                                    Boss_List.get(0).pos.y += Boss_List.get(0).Speed/2;
                                    if (Boss_List.get(0).pos.y >= 600) EeveMoveY = false;
                                    if (Boss_List.get(0).pos.x <= 1500) EeveMoveX = false;
                                } else {
                                    Boss_List.get(0).pos.y -= Boss_List.get(0).Speed/2;
                                    if (Boss_List.get(0).pos.y <= 100) EeveMoveY = true;
                                    if (Boss_List.get(0).pos.x <= 1500) EeveMoveX = false;
                                }
                            } else {
                                Boss_List.get(0).pos.x += Boss_List.get(0).Speed/2;
                                if (EeveMoveY) {
                                    Boss_List.get(0).pos.y += Boss_List.get(0).Speed/2;
                                    if (Boss_List.get(0).pos.y >= 600) EeveMoveY = false;
                                    if (Boss_List.get(0).pos.x >= 2100) EeveMoveX = true;
                                } else {
                                    Boss_List.get(0).pos.y -= Boss_List.get(0).Speed/2;
                                    if (Boss_List.get(0).pos.y <= 100) EeveMoveY = true;
                                    if (Boss_List.get(0).pos.x >= 2100) EeveMoveX = true;
                                }
                            }
                        }
                        if (Boss_List.get(1) != null) {
                            if (BlackyMoveX) {
                                Boss_List.get(1).pos.x -= Boss_List.get(1).Speed/2+5;
                                if (BlackyMoveY) {
                                    Boss_List.get(1).pos.y += Boss_List.get(1).Speed/2;
                                    if (Boss_List.get(1).pos.y >= 800) BlackyMoveY = false;
                                    if (Boss_List.get(1).pos.x <= 1400) BlackyMoveX = false;
                                } else {
                                    Boss_List.get(1).pos.y -= Boss_List.get(1).Speed/2;
                                    if (Boss_List.get(1).pos.y <= 50) BlackyMoveY = true;
                                    if (Boss_List.get(1).pos.x <= 1400) BlackyMoveX = false;
                                }
                            } else {
                                Boss_List.get(1).pos.x += Boss_List.get(1).Speed/2+5;
                                if (BlackyMoveY) {
                                    Boss_List.get(1).pos.y += Boss_List.get(1).Speed/2;
                                    if (Boss_List.get(1).pos.y >= 800) BlackyMoveY = false;
                                    if (Boss_List.get(1).pos.x >= 2200) BlackyMoveX = true;
                                } else {
                                    Boss_List.get(1).pos.y -= Boss_List.get(1).Speed/2;
                                    if (Boss_List.get(1).pos.y <= 50) BlackyMoveY = true;
                                    if (Boss_List.get(1).pos.x >= 2200) BlackyMoveX = true;
                                }
                            }
                        }
                    } else if (Boss_List.size() == 1) {
                        if (Boss_List.get(0) != null) {
                            if ( LastBossMoveX) {
                                Boss_List.get(0).pos.x -= Boss_List.get(0).Speed;
                                if (LastBossMoveY) {
                                    Boss_List.get(0).pos.y += Boss_List.get(0).Speed;
                                    if (Boss_List.get(0).pos.y >= 600) LastBossMoveY = false;
                                    if (Boss_List.get(0).pos.x <= 1500) LastBossMoveX = false;
                                } else {
                                    Boss_List.get(0).pos.y -= Boss_List.get(0).Speed;
                                    if (Boss_List.get(0).pos.y <= 100) LastBossMoveY = true;
                                    if (Boss_List.get(0).pos.x <= 1500) LastBossMoveX = false;
                                }
                            } else {
                                Boss_List.get(0).pos.x += Boss_List.get(0).Speed;
                                if (LastBossMoveY) {
                                    Boss_List.get(0).pos.y += Boss_List.get(0).Speed;
                                    if (Boss_List.get(0).pos.y >= 600) LastBossMoveY = false;
                                    if (Boss_List.get(0).pos.x >= 2100) LastBossMoveX = true;
                                } else {
                                    Boss_List.get(0).pos.y -= Boss_List.get(0).Speed/2;
                                    if (Boss_List.get(0).pos.y <= 100) LastBossMoveY = true;
                                    if (Boss_List.get(0).pos.x >= 2100) LastBossMoveX = true;
                                }
                            }
                        }
                    }
                }
            }

            //여기서 플레이어가 죽었을때나 게임 이겼을때 상황을 정리 할 수 있다.상황판
            public void Draw_Pause(Canvas canvas) {
                if (HP <= -10000) {
                    thread.status = 0;
                    thread.KD = false;
                    thread.KU = false;
                    thread.KL = false;
                    thread.KR = false;
                    thread.FIRE = false;
                    user.pos.x -= 70;
                    if (user.pos.x < -900) {
                        ACHIVE_DIE += 1;
                        editor_achieve.putInt("ACHIEVE_DIE", ACHIVE_DIE);
                        editor_achieve.commit();
                        Intent intent = new Intent(getApplicationContext(), Activity_7GameResult.class);
                        intent.putExtra("killed",Kill);
                        startActivity(intent);
                        finish();
                    }
                    /*
                    Resources res = getResources();//drawable에 있는 그림을 로딩해주는 역할이다.
                    BitmapDrawable UpBtn = null;
                    UpBtn = (BitmapDrawable) res.getDrawable(R.drawable.btn_up, null);
                    int maxwidth = canvas.getWidth();
                    int maxheight = canvas.getHeight(); //캔버스 높이

                    Bitmap bitUP = UpBtn.getBitmap();
                    btup = new Rect(maxwidth/2-1000, maxheight/2-300, maxwidth/2+1000, maxheight/2+300);
                    canvas.drawBitmap(bitUP, null, btup, null);

                    pauseThread();*/

                }
                if (Kill > 23 ) {
                    //Kill 보스 잡고나면 화면 우측 끝으로 이동시켜 마무리 될 수 있도록
                    thread.status = 0;
                    thread.KD = false;
                    thread.KU = false;
                    thread.KL = false;
                    thread.KR = false;
                    thread.FIRE = false;
                    user.pos.x += 70;
                    editor.putInt("Stage1", 1);
                    editor.apply();
                    if (user.pos.x >= 3000) {
                        Intent intent = new Intent(getApplicationContext(), Activity_7GameResult.class);
                        intent.putExtra("killed",Kill);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            // 적 행동            //현재까진 ㅇㅋ
            public void Process_Enemy() {
                DisplayMetrics dm = getResources().getDisplayMetrics();
                int width = dm.widthPixels;
                int height = dm.heightPixels;
                if (Kill < 10) {
                    if (cnt % 50 == 0) {
                        enemy = new GameEnemy("이브이", width - 20, height / 2, 70, 7, 5); // 좌표 체크하여 넘기기
                        Enemy_List.add(enemy);
                        enemy = new GameEnemy("이브이", width - 20, height / 2 - 400, 80, 7, 5);
                        Enemy_List.add(enemy);
                        //Log.e("적 생성됬니?", String.valueOf(Enemy_List.size()));
                    }
                }
                if (Kill > 12 && Kill < 22) {
                    if (cnt % 50 == 0) {
                        enemy = new GameEnemy("이브이", width - 20, height / 2, 70, 14, 8); // 좌표 체크하여 넘기기
                        Enemy_List.add(enemy);
                        enemy = new GameEnemy("이브이", width - 20, height / 2 - 250, 120, 14, 8);
                        Enemy_List.add(enemy);
                        enemy = new GameEnemy("이브이", width - 20, height / 2 - 500, 80, 14, 8);
                        Enemy_List.add(enemy);
                        //Log.e("적 생성됬니?", String.valueOf(Enemy_List.size()));
                    }
                }
            }

            //보스움직임다시볼것

            // 완벽 하다!! 유저의 움직임 표현
            public void Process_User() {
                if (thread.KD == true) {
                    Log.e("ACTION_DOWN -> Process_User", "하단 버튼 눌림 USER STATUS = 5");
                    if (user.pos.y > height - 450) {
                        // 아래쪽 화면 끝쪽에 닿으면 false 로 이동하지 못하도록
                        thread.KU = false;
                    } else {
                        // 아닐 경우 움직임 가능
                        user.setPlayerDown();
                    }
                    status = 5;
                }
                if (thread.KU == true) {
                    Log.e("ACTION_DOWN -> Process_User", "상단 버튼 눌림 USER STATUS = 8");
                    if (user.pos.y < 50) {
                        // 오른쪽 화면 끝쪽에 닿으면 false 로 이동하지 못하도록
                        thread.KU = false;
                    } else {
                        // 아닐 경우 움직임 가능
                        user.setPlayerUp();
                    }
                    status = 8;
                }
                if (thread.KL == true) {
                    Log.e("ACTION_DOWN -> Process_User", "좌측 버튼 눌림 USER STATUS = 4");
                    if (user.pos.x < 50) {
                        // 왼쪽 화면 끝쪽에 닿으면 false 로 이동하지 못하도록
                        thread.KL = false;
                    } else {
                        // 아닐 경우 움직임 가능
                        user.setPlayerLeft();
                    }
                    status = 4;
                }
                if (thread.KR == true) {
                    Log.e("ACTION_DOWN -> Process_User", "우측 버튼 눌림 USER STATUS = 6");
                    if (user.pos.x > width - 200) {
                        // 위쪽 화면 끝쪽에 닿으면 false 로 이동하지 못하도록
                        thread.KR = false;
                    } else {
                        // 아닐 경우 움직임 가능
                        user.setPlayerRight();
                    }
                    status = 6;
                }
            }

            //미사일 처리F
            public void Process_Missile() { // 미사일 처리 메소드
                // 화면터치 상태가 true 면

                if (thread.FIRE == true || thread.status == 1) {
                    if (cnt % ATKSPEED == 0) {
                        Log.e("ACTION_DOWN -> Process_Missile", "USER STATUS = 1 / 미사일 발사");


                        Log.e("Process_Missile()STATUS", "status: " + String.valueOf(status) + " / " + "thread.status: " + thread.status);
                        ms = new GameMissile(user.pos.x, user.pos.y, 0, FIRESPEED); // 좌표 체크하여 넘기기
                        Log.e("유저 미사일 좌표", "유저 미사일 좌표 = " + String.valueOf(user.pos.x));
                        Missile_List.add(ms);
                        Log.e("유저 미사일 추가여부", "추가됨, 미사일을 갯수는? " + String.valueOf(Missile_List.size()) + " 개");
                    }

                }  // 해당 미사일 추가
                else {
                    thread.FIRE = false;
                    status = 0;
                }
            }

            // 일시정지 버튼을 눌렀을때 전체적인 화면 멈춤/깨움 하는 역할
            public void pauseNResume(boolean isWait) {
                synchronized (this) {
                    this.isWait = isWait;
                    notify();
                }
            }

        }

        //여기서부터 스ㅓ피스뷰 스레드
        private ImageThread1 thread;

        //스레드 맨 처음 시작할때
        public void onResumeMySurfaceView() {
            //스레드 시작할때
            isLoop = true;
            thread = new ImageThread1(getHolder(), getContext());
            thread.start();
        }

        @Override// surface 시작 시점에 호출
        public void surfaceCreated(SurfaceHolder holder) {
            onResumeMySurfaceView();
        }

        public void onChangedMySurfaceView() {

        }

        @Override// surface 변경 시점에 호출
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        public void onPauseMySurfaceView() {
            //스레드 종료할때
            boolean retry = true;
            isLoop = false;
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //일시 정지된 스레드를 재시작하는 메소드
        public void resumeThread() {
            thread.pauseNResume(false);
        }

        //스레드 일시정지하는 메소드
        public void pauseThread() {
            thread.pauseNResume(true);

        }

        @Override// surface 종료 시점에 호출
        public void surfaceDestroyed(SurfaceHolder holder) {
            onPauseMySurfaceView();
        }


        @Override// 터치했을때 실질적인 움직임,행동관리
        public boolean onTouchEvent(MotionEvent event) {
            int id[] = new int[3];
            int x[] = new int[3];
            int y[] = new int[3];
            int pointer_count = event.getPointerCount(); //현재 터치 발생한 포인트 수를 얻는다.
            if (pointer_count > 8) pointer_count = 7; //4개 이상의 포인트를 터치했더라도 3개까지만 처리를 한다.
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    int eventX = (int) (event.getX());
                    int eventY = (int) (event.getY());
                    if (myrect.contains(eventX, eventY)) {
                        // pause 버튼 클릭했을때 반응이다 쉑끼야
                        pauseThread();//스레드를 일시 정지하는 역할
                        Toast.makeText(Activity_8InGame_Stage2.this, "Hit", Toast.LENGTH_SHORT).show();
                    }
                    if (btfire.contains(eventX, eventY)) {
                        thread.status = 1;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 1");
                        return thread.FIRE = true;
                    }
                    if (btup.contains(eventX, eventY)) {
                        thread.status = 8;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 8");
                        return thread.KU = true;
                    }
                    if (btdown.contains(eventX, eventY)) {
                        thread.status = 5;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 5");
                        return thread.KD = true;
                    }
                    if (btleft.contains(eventX, eventY)) {
                        thread.status = 4;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 4");
                        return thread.KL = true;
                    }
                    if (btright.contains(eventX, eventY)) {
                        thread.status = 6;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 6");
                        return thread.KR = true;
                    }
                    if (!myrect.contains(eventX, eventY)) {//버튼네모크기에 모션다운의 좌표가 포함이 안 될경우
                        thread.status = 0;
                        resumeThread();
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 0");
                        // Toast.makeText(Activity_8InGame.this, "Miss", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    for (int i = 0; i < pointer_count; i++) {
                        id[i] = event.getPointerId(i); //터치한 순간부터 부여되는 포인트 고유번호.
                        x[i] = (int) (event.getX(i));
                        y[i] = (int) (event.getY(i));

                        if (myrect.contains(x[i], y[i])) {
                            // pause 버튼 클릭했을때 반응이다 쉑끼야
                            pauseThread();//스레드를 일시 정지하는 역할
                            Toast.makeText(Activity_8InGame_Stage2.this, "Hit", Toast.LENGTH_SHORT).show();
                        }
                        if (btfire.contains(x[i], y[i])) {
                            thread.status = 1;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 1");
                            return thread.FIRE = true;
                        }
                        if (btup.contains(x[i], y[i])) {
                            thread.status = 8;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 8");
                            return thread.KU = true;
                        }
                        if (btdown.contains(x[i], y[i])) {
                            thread.status = 5;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 5");
                            return thread.KD = true;
                        }
                        if (btleft.contains(x[i], y[i])) {
                            thread.status = 4;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 4");
                            return thread.KL = true;
                        }
                        if (btright.contains(x[i], y[i])) {
                            thread.status = 6;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 6");
                            return thread.KR = true;
                        }
                        if (!myrect.contains(x[i], y[i])) {//버튼네모크기에 모션다운의 좌표가 포함이 안 될경우
                            thread.status = 0;
                            resumeThread();
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 0");
                            // Toast.makeText(Activity_8InGame.this, "Miss", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    for (int i = 0; i < pointer_count; i++) {
                        id[i] = event.getPointerId(i); //터치한 순간부터 부여되는 포인트 고유번호.
                        x[i] = (int) (event.getX(i));
                        y[i] = (int) (event.getY(i));
                        if (myrect.contains(x[i], y[i])) {
                            // pause 버튼 클릭했을때 반응이다 쉑끼야
                            pauseThread();//스레드를 일시 정지하는 역할
                            Toast.makeText(Activity_8InGame_Stage2.this, "Hit", Toast.LENGTH_SHORT).show();
                        }
                        if (btfire.contains(x[i], y[i])) {
                            thread.status = 1;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 1");
                            return thread.FIRE = true;
                        }
                        if (btup.contains(x[i], y[i])) {
                            thread.status = 8;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 8");
                            return thread.KU = true;
                        }
                        if (btdown.contains(x[i], y[i])) {
                            thread.status = 5;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 5");
                            return thread.KD = true;
                        }
                        if (btleft.contains(x[i], y[i])) {
                            thread.status = 4;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 4");
                            return thread.KL = true;
                        }
                        if (btright.contains(x[i], y[i])) {
                            thread.status = 6;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 6");
                            return thread.KR = true;
                        }
                        if (!myrect.contains(x[i], y[i])) {//버튼네모크기에 모션다운의 좌표가 포함이 안 될경우
                            thread.status = 0;
                            resumeThread();
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 0");
                            // Toast.makeText(Activity_8InGame.this, "Miss", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                /*case MotionEvent.ACTION_CANCEL:
                    for (int i = 0; i < pointer_count; i++) {
                        id[i] = event.getPointerId(i); //터치한 순간부터 부여되는 포인트 고유번호.
                        x[i] = (int) (event.getX(i));
                        y[i] = (int) (event.getY(i));
                        if (myrect.contains(x[i], y[i])) {
                            // pause 버튼 클릭했을때 반응이다 쉑끼야
                            pauseThread();//스레드를 일시 정지하는 역할
                            Toast.makeText(Activity_8InGame_Stage1.this, "Hit", Toast.LENGTH_SHORT).show();
                        }
                        if (!btfire.contains(x[i], y[i])) {
                            thread.status = 1;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 1");
                            return thread.FIRE = false;
                        }
                        if (!btup.contains(x[i], y[i])) {
                            thread.status = 8;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 8");
                            return thread.KU = false;
                        }
                        if (!btdown.contains(x[i], y[i])) {
                            thread.status = 5;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 5");
                            return thread.KD = false;
                        }
                        if (!btleft.contains(x[i], y[i])) {
                            thread.status = 4;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 4");
                            return thread.KL = false;
                        }
                        if (!btright.contains(x[i], y[i])) {
                            thread.status = 6;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 6");
                            return thread.KR = false;
                        }
                        if (!myrect.contains(x[i], y[i])) {//버튼네모크기에 모션다운의 좌표가 포함이 안 될경우
                            thread.status = 0;
                            resumeThread();
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 0");
                            // Toast.makeText(Activity_8InGame.this, "Miss", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;*/
                case MotionEvent.ACTION_POINTER_UP:
                    thread.FIRE = false;
                    thread.KR = false;
                    thread.KL = false;
                    thread.KU = false;
                    thread.KD = false;
                    thread.status = 0;
                    /*for (int i = 0; i < pointer_count; i++) {
                        id[i] = event.getPointerId(i); //터치한 순간부터 부여되는 포인트 고유번호.
                        x[i] = (int) (event.getX(i));
                        y[i] = (int) (event.getY(i));
                        if (myrect.contains(x[i], y[i])) {
                            // pause 버튼 클릭했을때 반응이다 쉑끼야
                            pauseThread();//스레드를 일시 정지하는 역할
                            Toast.makeText(Activity_8InGame_Stage1.this, "Hit", Toast.LENGTH_SHORT).show();
                        }
                        if (btfire.contains(x[i], y[i])) {
                            thread.status = 1;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 1");
                            if(!btfire.contains(x[i], y[i])){
                                return thread.FIRE = false;
                            }
                            return thread.FIRE = true;
                        }
                        if (btup.contains(x[i], y[i])) {
                            thread.status = 8;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 8");
                            if (!btup.contains(x[i], y[i])){
                                return thread.KU = false;
                            }
                            return thread.KU = true;
                        }
                        if (btdown.contains(x[i], y[i])) {
                            thread.status = 5;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 5");
                            if (!btdown.contains(x[i], y[i])){
                                return thread.KD = false;
                            }
                            return thread.KD = true;
                        }
                        if (btleft.contains(x[i], y[i])) {
                            thread.status = 4;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 4");
                            if (!btleft.contains(x[i], y[i])){
                                return thread.KL = false;
                            }
                            return thread.KL = true;
                        }
                        if (btright.contains(x[i], y[i])) {
                            thread.status = 6;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 6");
                            if (!btright.contains(x[i], y[i])){
                                return thread.KR = false;
                            }
                            return thread.KR = true;
                        }
                        if (myrect.contains(x[i], y[i])) {//버튼네모크기에 모션다운의 좌표가 포함이 안 될경우
                            thread.status = 0;
                            resumeThread();
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 0");
                            // Toast.makeText(Activity_8InGame.this, "Miss", Toast.LENGTH_SHORT).show();
                        }
                    }*/
                    break;
                case MotionEvent.ACTION_UP:
                    thread.FIRE = false;
                    thread.KR = false;
                    thread.KL = false;
                    thread.KU = false;
                    thread.KD = false;
                    thread.status = 0;
                    /*for (int i = 0; i < pointer_count; i++) {
                        id[i] = event.getPointerId(i); //터치한 순간부터 부여되는 포인트 고유번호.
                        x[i] = (int) (event.getX(i));
                        y[i] = (int) (event.getY(i));
                        if (myrect.contains(x[i], y[i])) {
                            // pause 버튼 클릭했을때 반응이다 쉑끼야
                            pauseThread();//스레드를 일시 정지하는 역할
                            Toast.makeText(Activity_8InGame_Stage1.this, "Hit", Toast.LENGTH_SHORT).show();
                        }
                        if (btfire.contains(x[i], y[i])) {
                            thread.status = 1;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 1");
                            if(!btfire.contains(x[i], y[i])){
                                return thread.FIRE = false;
                            }
                            return thread.FIRE = true;
                        }
                        if (btup.contains(x[i], y[i])) {
                            thread.status = 8;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 8");
                            if (!btup.contains(x[i], y[i])){
                                return thread.KU = false;
                            }
                            return thread.KU = true;
                        }
                        if (btdown.contains(x[i], y[i])) {
                            thread.status = 5;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 5");
                            if (!btdown.contains(x[i], y[i])){
                                return thread.KD = false;
                            }
                            return thread.KD = true;
                        }
                        if (btleft.contains(x[i], y[i])) {
                            thread.status = 4;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 4");
                            if (!btleft.contains(x[i], y[i])){
                                return thread.KL = false;
                            }
                            return thread.KL = true;
                        }
                        if (btright.contains(x[i], y[i])) {
                            thread.status = 6;
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 6");
                            if (!btright.contains(x[i], y[i])){
                                return thread.KR = false;
                            }
                            return thread.KR = true;
                        }
                        if (myrect.contains(x[i], y[i])) {//버튼네모크기에 모션다운의 좌표가 포함이 안 될경우
                            thread.status = 0;
                            resumeThread();
                            Log.e("ACTION_DOWN", "ACTION_DOWN, status = 0");
                            // Toast.makeText(Activity_8InGame.this, "Miss", Toast.LENGTH_SHORT).show();
                        }
                    }*/
                    break;
            }
            return true;
        }
            /*if (event.getAction() == MotionEvent.ACTION_DOWN ||
                    event.getAction() == MotionEvent.ACTION_POINTER_DOWN ||
                    event.getAction() == MotionEvent.ACTION_MOVE) {
                for (int i = 0; i < pointer_count; i++) {
                    id[i] = event.getPointerId(i); //터치한 순간부터 부여되는 포인트 고유번호.
                    x[i] = (int) (event.getX(i));
                    y[i] = (int) (event.getY(i));
                    if (myrect.contains(x[i], y[i])) {
                        // pause 버튼 클릭했을때 반응이다 쉑끼야
                        pauseThread();//스레드를 일시 정지하는 역할
                        Toast.makeText(Activity_8InGame_Stage1.this, "Hit", Toast.LENGTH_SHORT).show();
                    }
                    if (btfire.contains(x[i], y[i])) {
                        thread.status = 1;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 1");
                        return thread.FIRE = true;
                    }
                    if (btup.contains(x[i], y[i])) {
                        thread.status = 8;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 8");
                        return thread.KU = true;
                    }
                    if (btdown.contains(x[i], y[i])) {
                        thread.status = 5;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 5");
                        return thread.KD = true;
                    }
                    if (btleft.contains(x[i], y[i])) {
                        thread.status = 4;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 4");
                        return thread.KL = true;
                    }
                    if (btright.contains(x[i], y[i])) {
                        thread.status = 6;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 6");
                        return thread.KR = true;
                    }
                    if (!myrect.contains(x[i], y[i])) {//버튼네모크기에 모션다운의 좌표가 포함이 안 될경우
                        thread.status = 0;
                        resumeThread();
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 0");
                        // Toast.makeText(Activity_8InGame.this, "Miss", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            } else if(event.getAction() == MotionEvent.ACTION_POINTER_DOWN){
                for (int i = 0; i < pointer_count; i++) {
                    id[i] = event.getPointerId(i); //터치한 순간부터 부여되는 포인트 고유번호.
                    x[i] = (int) (event.getX(i));
                    y[i] = (int) (event.getY(i));
                    if (myrect.contains(x[i], y[i])) {
                        // pause 버튼 클릭했을때 반응이다 쉑끼야
                        pauseThread();//스레드를 일시 정지하는 역할
                        Toast.makeText(Activity_8InGame_Stage1.this, "Hit", Toast.LENGTH_SHORT).show();
                    }
                    if (btfire.contains(x[i], y[i])) {
                        thread.status = 1;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 1");
                        return thread.FIRE=true;
                    }
                    if (btup.contains(x[i], y[i])) {
                        thread.status = 8;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 8");
                        return thread.KU = true;
                    }
                    if (btdown.contains(x[i], y[i])) {
                        thread.status = 5;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 5");
                        return thread.KD = true;
                    }
                    if (btleft.contains(x[i], y[i])) {
                        thread.status = 4;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 4");
                        return thread.KL = true;
                    }
                    if (btright.contains(x[i], y[i])) {
                        thread.status = 6;
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 6");
                        return thread.KR = true;
                    }
                    if (!myrect.contains(x[i], y[i])) {//버튼네모크기에 모션다운의 좌표가 포함이 안 될경우
                        thread.status = 0;
                        resumeThread();
                        Log.e("ACTION_DOWN", "ACTION_DOWN, status = 0");
                        // Toast.makeText(Activity_8InGame.this, "Miss", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            } else {
                thread.FIRE = false;
                thread.KR = false;
                thread.KL = false;
                thread.KU = false;
                thread.KD = false;
                thread.status = 0;
            }
            return true;
        }*/

        public class GameEnemy {
            Point pos; //미사일 좌표 변수
            int Speed;
            int Hp;
            int Atk;
            String Name;

            GameEnemy(String name, int x, int y, int speed, int hp, int atk) { //미사일 좌표를 입력 받는 메소드
                pos = new Point(x, y); //미사일 좌표를 체크
                Speed = speed;
                Hp = hp;
                Atk = atk;
                Name = name;
            }

            public String getName() {
                return Name;
            }

            public void move() { //미사일 이동을 위한 메소드
                pos.x -= Speed; //x 좌표에 10만큼 미사일 이동
            }


        }


        public class GameUser {
            Point pos; //미사일 좌표 변수
            int X;
            int Y;
            int Speed;
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            int Atk;
            int Hp;

            GameUser(int x, int y, int speed, int atk, int hp) { //미사일 좌표를 입력 받는 메소드
                pos = new Point(x, y); //미사일 좌표를 체크
                Speed = speed;
                Atk = atk;
                Hp = hp;
            }

            //우측으로 30씩 이동 30은 나중에 속도변수줄것
            public void setPlayerRight() {
                Log.v("user.setPlayerRight", "우측 이동 setPlayerRight 진행 되었다.");
                pos.x += Speed;
                if (pos.x > width - 200) thread.KR = false;

            }

            public void setPlayerLeft() {
                Log.v("user.setPlayerLeft", "좌측 이동 setPlayerLeft 진행 되었다.");
                pos.x -= Speed;
                if (pos.x < 50) thread.KL = false;

            }

            public void setPlayerDown() {
                Log.v("user.setPlayerDown", "하향 이동 setPlayerDown 진행 되었다.");
                pos.y += Speed;
                if (pos.y > height - 450) thread.KD = false;
            }

            public void setPlayerUp() {
                Log.v("user.setPlayerUp", "상향 이동 setPlayerUp 진행 되었다.");
                pos.y -= Speed;
                if (pos.y < 50) thread.KU = false;
            }

            public void emove() { //미사일 이동을 위한 메소드
                pos.x -= 100; //x 좌표에 10만큼 미사일 이동
            }

        }//SurfaceView
    }


}//InGame


