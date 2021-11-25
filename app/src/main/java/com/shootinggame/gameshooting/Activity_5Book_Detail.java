package com.shootinggame.gameshooting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Activity_5Book_Detail extends AppCompatActivity {
    private String tag;
    private ImageButton ib_BookDetailBack, ib_BookDetailTeam, ib_BookDetailBook, ib_BookDetailShop,
            ib_BookDetailStart, ib_BookDetailProfile, ib_BookDetailSetting, ib_BookDetailMail, ib_BookDetailLeft, ib_BookDetailRight;
    private TextView tv_BookDetailPoketmonName, tv_BookDetailName, tv_BookDetailLevel, tv_BookDetailDia, tv_BookDetailGold,
            tv_BookDetailCountMax,tv_bookdetail_NeedExp,  tv_bookdetail_RequestExp;
    private RecyclerView rv_BookDetailRecyclerview;
    private ImageView iv_BookDetailPoketmon;
    private Intent BD_Intent;
    private ProgressBar expVar;
    private String PoketmonName;
    private int num;
    public static Activity activity;
    private static TextView tv_BookDetailCount, tv_BookDetailTimeCount;
    public static String Time;
    private GridLayoutManager layoutManager;
    private ProgressBar progressBarAtk,progressBarHp,progressBarSpeed,progressBarAtkSpeed,progressBarMissileSpeed;

    private Activity_5Book book;
    private String Poketmonimage;

    TextView introduce, atk,maxatk,hp,maxhp, speed, maxspeed,atkspeed,maxatkspeed, missilespeed, maxmissilespeed;
    Activity_5Book Book = (Activity_5Book) Activity_5Book.activity;

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        layoutFindID_BookDetail();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_5book_detail);

        hideNaviUI();
        BD_Intent = getIntent();
        num = BD_Intent.getIntExtra("number", -1);
        layoutFindID_BookDetail();
        onEnterAnimationComplete();
        activity_Move();

        //setBookText();
        viewStatus();
        selectPoketmon(num);

        expVar = findViewById(R.id.pb_bookdetail_ExpBar);
        /*expVar.setProgress(ClassUserData.getInstance().getExp());
        expVar.setMax(ClassUserData.getInstance().getExpMax());*/
    }

    public static void set5BookDetailTimer() {
        Time = get5BookDetailTimer();
        tv_BookDetailTimeCount.setText(Time);
    }

    public static void set5BookDetailTimerText(String a) {
        tv_BookDetailTimeCount.setText(a);

    }

    public static String get5BookDetailTimer() {
        return tv_BookDetailTimeCount.getText().toString();
    }

    public static void set5BookDetailUserCount(String userCount) {
        tv_BookDetailCount.setText(userCount);
    }

    public static void BookDetailViewGone() {
        tv_BookDetailTimeCount.setVisibility(View.GONE);
    }

    public static void BookDetailViewVisible() {
        if (ClassUserData.getInstance().getMaxCount() > ClassUserData.getInstance().getCount()) {
            tv_BookDetailTimeCount.setVisibility(View.VISIBLE);
        }
    }

    public void viewStatus() {
        if (ClassUserData.getInstance().getMaxCount() == ClassUserData.getInstance().getCount()) {
            tv_BookDetailTimeCount.setVisibility(View.GONE);
        } else if (ClassUserData.getInstance().getMaxCount() > ClassUserData.getInstance().getCount()) {
            tv_BookDetailTimeCount.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }

    @SuppressLint("SetTextI18n")
    public void setBookText() {
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        tv_BookDetailName.setText(pref.getString("UserName", ClassUserData.getInstance().getName()));
        tv_BookDetailLevel.setText(Integer.toString(pref.getInt("UserLevel", ClassUserData.getInstance().getLevel())));
        tv_BookDetailDia.setText(Integer.toString(pref.getInt("UserDia", ClassUserData.getInstance().getDiamond())));
        tv_BookDetailGold.setText(Integer.toString( pref.getInt("UserGold", ClassUserData.getInstance().getGold())));
        ib_BookDetailProfile.setImageResource(pref.getInt("UserProfile",ClassUserData.getInstance().getProfile()));
        tv_BookDetailCount.setText(Integer.toString(pref.getInt("UserCount",ClassUserData.getInstance().getCount())));
        tv_BookDetailCountMax.setText(Integer.toString( pref.getInt("UserMaxCount",ClassUserData.getInstance().getMaxCount())));
        tv_BookDetailTimeCount.setText(ClassUserData.getInstance().getTime());
        tv_bookdetail_NeedExp.setText(Integer.toString(pref.getInt("UserExp", ClassUserData.getInstance().getExp())));
        tv_bookdetail_RequestExp.setText(Integer.toString(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax())));


        expVar.setProgress(pref.getInt("UserExp", ClassUserData.getInstance().getExp()));
        expVar.setMax(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax()));
    }

    public void activity_Move() {
        // 도감이동(백버튼)
        ib_BookDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // 도감이동
        ib_BookDetailBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 팀관리 이동
        ib_BookDetailTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_4Team.class);
                startActivity(intent);
                Activity_5Book.activity.finishAfterTransition();
                finish();
            }
        });
        // 상점 이동
        ib_BookDetailShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_6Store.class);
                startActivity(intent);
                Activity_5Book.activity.finishAfterTransition();
                finish();
            }
        });
        // 게임시작 이동
        ib_BookDetailStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_7GameStart_Linear_Table.class);
                startActivity(intent);
                Activity_5Book.activity.finishAfterTransition();
                finish();

            }
        });
        // 프로필 팝업
        ib_BookDetailProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Profile.class);
                startActivity(intent);
            }
        });
        // 메일 팝업
        ib_BookDetailMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Mail.class);
                startActivity(intent);
            }
        });
        // 프로필 팝업
        ib_BookDetailSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Setting.class);
                startActivity(intent);
            }
        });
    }


    //도감선택하여 넘어옴
    public void selectPoketmon(int num) {
        //리사이클러뷰의 포지션값을 넘버로 받아와서 이름 이미지를 설정한다.
        PoketmonName = BD_Intent.getStringExtra("name");
        byte[] byteArray = BD_Intent.getByteArrayExtra("img");
        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        iv_BookDetailPoketmon.setImageBitmap(image);
        tv_BookDetailPoketmonName.setText(PoketmonName);


        progressBarAtk.setMax(BD_Intent.getIntExtra("atkmax",0));
        progressBarAtk.setProgress(BD_Intent.getIntExtra("atk",0));
        atk.setText(String.valueOf(BD_Intent.getIntExtra("atk",0)));
        maxatk.setText(String.valueOf(BD_Intent.getIntExtra("atkmax",0)));

        progressBarHp.setMax(BD_Intent.getIntExtra("hpmax",0));
        progressBarHp.setProgress(BD_Intent.getIntExtra("hp",0));
        hp.setText(String.valueOf(BD_Intent.getIntExtra("hp",0)));
        maxhp.setText(String.valueOf(BD_Intent.getIntExtra("hpmax",0)));

        progressBarAtkSpeed.setMax(BD_Intent.getIntExtra("atkspeedmax",0));
        progressBarAtkSpeed.setProgress(BD_Intent.getIntExtra("atkspeed",0));
        atkspeed.setText(String.valueOf(BD_Intent.getIntExtra("atkspeed",0)));
        maxatkspeed.setText(String.valueOf(BD_Intent.getIntExtra("atkspeedmax",0)));

        progressBarSpeed.setMax(BD_Intent.getIntExtra("speedmax",0));
        progressBarSpeed.setProgress(BD_Intent.getIntExtra("speed",0));
        speed.setText(String.valueOf(BD_Intent.getIntExtra("speed",0)));
        maxspeed.setText(String.valueOf(BD_Intent.getIntExtra("speedmax",0)));

        progressBarMissileSpeed.setMax(BD_Intent.getIntExtra("missilespeedmax",0));
        progressBarMissileSpeed.setProgress(BD_Intent.getIntExtra("missilespeed",0));
        missilespeed.setText(String.valueOf(BD_Intent.getIntExtra("missilespeed",0)));
        maxmissilespeed.setText(String.valueOf(BD_Intent.getIntExtra("missilespeedmax",0)));




        introduce.setText(String.valueOf(BD_Intent.getStringExtra("introduce")));

    }

    //아이디 찾아주기
    public void layoutFindID_BookDetail() {
        tv_BookDetailName = findViewById(R.id.tv_bookdetail_Name);
        tv_BookDetailLevel = findViewById(R.id.tv_bookdetail_Level);
        tv_BookDetailDia = findViewById(R.id.tv_bookdetail_Dia);
        tv_BookDetailGold = findViewById(R.id.tv_bookdetail_Gold);

        tv_BookDetailTimeCount = findViewById(R.id.tv_bookdetail_Timer);
        tv_BookDetailCount = findViewById(R.id.tv_bookdetail_Count);
        tv_BookDetailCountMax = findViewById(R.id.tv_bookdetail_CountMax);
        tv_bookdetail_NeedExp= findViewById(R.id.tv_bookdetail_NeedExp);
                tv_bookdetail_RequestExp= findViewById(R.id.tv_bookdetail_RequestExp);


        progressBarAtk = findViewById(R.id.detail_Atk_Progress);
        atk = findViewById(R.id.detail_Atk);
        maxatk = findViewById(R.id.detail_maxAtk);
        progressBarHp = findViewById(R.id.detail_Hp_Progress);
        hp = findViewById(R.id.detail_Hp);
        maxhp = findViewById(R.id.detail_maxHp);
        introduce = findViewById(R.id.detail_introduce);
        progressBarSpeed = findViewById(R.id.detail_Speed_Progress);
        speed = findViewById(R.id.detail_Speed);
        maxspeed = findViewById(R.id.detail_maxSpeed);
        progressBarAtkSpeed = findViewById(R.id.detail_AtkSpeed_Progress);
        atkspeed = findViewById(R.id.detail_AtkSpeed);
        maxatkspeed = findViewById(R.id.detail_maxAtkSpeed);
        progressBarMissileSpeed = findViewById(R.id.detail_AtkSpeedMissile_Progress);
        missilespeed = findViewById(R.id.detail_AtkSpeedMissile);
        maxmissilespeed = findViewById(R.id.detail_maxAtkSpeedMissile);


        iv_BookDetailPoketmon = findViewById(R.id.recylcerview_image);
        tv_BookDetailPoketmonName = findViewById(R.id.tv_bookdetail_PoketmonName);
        ib_BookDetailBack = findViewById(R.id.ib_bookdetail_Back);
        ib_BookDetailTeam = findViewById(R.id.ib_bookdetail_Team);
        ib_BookDetailBook = findViewById(R.id.ib_bookdetail_Book);
        ib_BookDetailShop = findViewById(R.id.ib_bookdetail_Shop);
        ib_BookDetailStart = findViewById(R.id.ib_bookdetail_Start);
        ib_BookDetailProfile = findViewById(R.id.ib_bookdetail_Profile);
        ib_BookDetailMail = findViewById(R.id.ib_bookdetail_Mail);
        ib_BookDetailSetting = findViewById(R.id.ib_bookdetail_Setting);
        ib_BookDetailLeft = findViewById(R.id.ib_bookdetail_Left);
        ib_BookDetailRight = findViewById(R.id.ib_bookdetail_Right);
    }

    //UI감추기
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

    @Override
    protected void onPause() {
        super.onPause();
        hideNaviUI();
        tag = "도감상세Activity";
        viewStatus();
        Log.e(tag, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNaviUI();
        setBookText();
        viewStatus();
        onEnterAnimationComplete();
        tag = "도감상세Activity";
        Log.e(tag, "onResume");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onEnterAnimationComplete();
        tag = "도감상세Activity";
        Log.e(tag, "onDestroy");
    }
}
