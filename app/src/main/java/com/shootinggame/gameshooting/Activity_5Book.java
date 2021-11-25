package com.shootinggame.gameshooting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Activity_5Book extends AppCompatActivity {
    private String tag;
    private TextView tv_BookUserName, tv_BookLevel, tv_BookDiamond, tv_BookGold,
             tv_BookCountMax ,tv_BookExpMax, tv_BookNeedExp;
    private ImageButton ib_BookTeam, ib_BookShop, ib_BookStart, ib_BookOption,
            ib_BookMail, ib_BookBack, ib_BookProfile;
    private RecyclerView recyclerView;
    private RecyclerVIewAdapter_Book adapter;
    private GridLayoutManager layoutManager;
    private static TextView tv_BookTimeCount , tv_BookUserCount;
    public static String Time;
    public static Activity activity;
    private ProgressBar expVar;

    public static ArrayList<ClassPoketMon> list = new ArrayList<ClassPoketMon>() {{
        add(new ClassPoketMon("이상해씨", R.drawable.esangheassi, R.drawable.esangheassiprofile, 1, 100, 500,1,1,5,1,5,"이상해씨 입니다.\n 이상해풀로 진화 가능합니다.",1,1,5,5,5));
        add(new ClassPoketMon("이상해풀", R.drawable.esangheapul, R.drawable.esangheapulprofile,2, 200,1000,5,5,10,5,10,"이상해씨가 진화한 형태입니다.\n 이상해꽃으로 진화 가능합니다.",5,5,10,10,10));
        add(new ClassPoketMon("이상해꽃", R.drawable.esangheaggot, R.drawable.esangheaggotprofile,3,500,5000,10,10,30,10,30,"이상해씨의 최종진화 형태입니다.\n 이상해풀에서 진화하여 획득 가능합니다.",10,10,30,30,30));
        add(new ClassPoketMon("파이리", R.drawable.pairi, R.drawable.pairiprofile,4, 100, 500,1,1,5,1,5,"파이리 입니다.\n리자드로 진화 가능합니다.",1,1,5,5,5));
        add(new ClassPoketMon("리자드", R.drawable.parijade, R.drawable.parijadeprofile,5,200,1000,1,5,10,5,10,"파이리가 진화한 형태입니다.\n리자몽으로 진화 가능합니다.",5,5,10,10,10));
        add(new ClassPoketMon("리자몽", R.drawable.parijamong, R.drawable.parijamongprofile,6,500,5000,10,10,30,10,30,"파이리의 최종진화 형태입니다.\n 리자드에서 진화하여 획득 가능합니다.",10,10,30,30,30));
        add(new ClassPoketMon("꼬부기", R.drawable.ggobugi, R.drawable.ggobugiprofile,7, 100, 500,1,1,5,1,5,"꼬부기 입니다.\n어니부기로 진화 가능합니다.",1,1,5,5,5));
        add(new ClassPoketMon("어니부기", R.drawable.ggounibugi, R.drawable.ggounibugiprofile,8, 200,1000,5,5,10,5,10,"꼬부기가 진화한 형태입니다.\n거북왕으로 진화 가능합니다.",5,5,10,10,10));
        add(new ClassPoketMon("거북왕", R.drawable.ggogubuckwang, R.drawable.ggogubuckwangprofile,9,500,5000,10,10,30,10,30,"꼬부기의 최종진화 형태입니다.\n 어니부기에서 진화하여 획득 가능합니다.",10,10,30,30,30));
        add(new ClassPoketMon("피츄",R.drawable.pichu, R.drawable.pichuprofile,10, 100, 500,1,1,5,1,5,"피츄 입니다.\n피카츄로 진화 가능합니다.",1,1,5,5,5));
        add(new ClassPoketMon("피카츄", R.drawable.picachu, R.drawable.picachuprofile,11, 200,1000,5,5,10,5,10,"피츄가 진화한 형태입니다.\n 라이츄로 진화 가능합니다.",5,5,10,10,10));
        add(new ClassPoketMon("라이츄", R.drawable.raichu,R.drawable.raichuprofile ,12,500,5000,10,10,30,10,30,"피츄의 최종진화 형태입니다. \n 피카츄 에서 진화하여 획득 가능합니다.",10,10,30,30,30));
    }};

    public static ArrayList<ClassPoketMon> getList() {
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5book);
        layoutFindID_Book();
        hideNaviUI();
        activity_Move();
        ClassUserData.getInstance();
        //setBookText();
        viewStatus();
        //viewStatus();
        expVar = findViewById(R.id.pb_book_ExpBar);
        /*expVar.setProgress(ClassUserData.getInstance().getExp());
        expVar.setMax(ClassUserData.getInstance().getExpMax());*/
        activity = Activity_5Book.this;
        recyclerView = (RecyclerView) findViewById(R.id.grid_recyclerview);
        adapter = new RecyclerVIewAdapter_Book(getApplicationContext(), list);

        layoutManager = new GridLayoutManager(getApplicationContext(), 6);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //어텝터 클릭리스너 인터페이스
        adapter.setOnItemClickListener(new ItemBookClickListener() {
            @Override
            public void onItemClick(RecyclerVIewAdapter_Book.MyViewHolder holder, View view, int position) {
                //어댑터에서 포지션을 가지고 온다.
                int pos = holder.getAdapterPosition();
                //공유 객체로 사용할 레이아웃의 아이디를 지정해준다.
                View img = view.findViewById(R.id.recylcerview_row_image);
                //페어할 이미지를 만든다.                 위 이미지를 교환할 트렌젝션이름을 통해
                Pair<View, String> pair_IMG = Pair.create(img, img.getTransitionName());
                //액티비티 옵션컴팻 중 스크린 트랜지션애니메이션을 설정해주는데, 현재액티비티와, 공유객체할 이미지로 설정한다.
                ActivityOptionsCompat optionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(Activity_5Book.this, pair_IMG);
                //인텐트로 이동을한다.
                Intent intent = new Intent(getApplicationContext(), Activity_5Book_Detail.class);
                intent.putExtra("number", pos);
                intent.putExtra("pokenum", list.get(pos).Pokenum);
                intent.putExtra("name", list.get(pos).Name);
                Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), list.get(pos).Image);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("img", byteArray);
                intent.putExtra("introduce", list.get(pos).Introduce);

                intent.putExtra("atk", list.get(pos).Atk);
                intent.putExtra("atkmax", list.get(pos).MaxAtk);

                intent.putExtra("hp", list.get(pos).Hp);
                intent.putExtra("hpmax", list.get(pos).MaxHp);


                intent.putExtra("speed", list.get(pos).Speed);
                intent.putExtra("speedmax", list.get(pos).MaxSpeed);

                intent.putExtra("atkspeed", list.get(pos).SpeedAtk);
                intent.putExtra("atkspeedmax", list.get(pos).MaxSpeedAtk);

                intent.putExtra("missilespeed", list.get(pos).SpeedMissile);
                intent.putExtra("missilespeedmax", list.get(pos).MaxSpeedMissile);



                startActivity(intent, optionsCompat.toBundle());

            }
        });


    }

    public static void set5BookTimer() {
        Time = get5BookTimer();
        tv_BookTimeCount.setText(Time);
    }

    public static void set5BookTimerText(String a) {
        tv_BookTimeCount.setText(a);

    }

    public static String get5BookTimer() {
        return tv_BookTimeCount.getText().toString();
    }
    public static void set5BookUserCount(String userCount){
        tv_BookUserCount.setText(userCount);

    }
    public static void BookViewGone() {
        tv_BookTimeCount.setVisibility(View.GONE);
    }

    public static void BookViewVisible() {
        tv_BookTimeCount.setVisibility(View.VISIBLE);

    }
    public void viewStatus(){
        if(ClassUserData.getInstance().getMaxCount()==ClassUserData.getInstance().getCount()){
            tv_BookTimeCount.setVisibility(View.GONE);
        }else if(ClassUserData.getInstance().getMaxCount()>ClassUserData.getInstance().getCount()){
            tv_BookTimeCount.setVisibility(View.VISIBLE);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("SetTextI18n")
    //유저 정보(닉,렙,다야,골드)
    public void setBookText() {
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        tv_BookUserName.setText(pref.getString("UserName", ClassUserData.getInstance().getName()));
        tv_BookLevel.setText(Integer.toString(pref.getInt("UserLevel", ClassUserData.getInstance().getLevel())));
        tv_BookDiamond.setText(Integer.toString(pref.getInt("UserDia", ClassUserData.getInstance().getDiamond())));
        tv_BookGold.setText(Integer.toString( pref.getInt("UserGold", ClassUserData.getInstance().getGold())));
        ib_BookProfile.setImageResource(pref.getInt("UserProfile",ClassUserData.getInstance().getProfile()));
        tv_BookUserCount.setText(Integer.toString(pref.getInt("UserCount",ClassUserData.getInstance().getCount())));
        tv_BookCountMax.setText(Integer.toString( pref.getInt("UserMaxCount",ClassUserData.getInstance().getMaxCount())));
        tv_BookTimeCount.setText(ClassUserData.getInstance().getTime());
        tv_BookNeedExp.setText(Integer.toString(pref.getInt("UserExp", ClassUserData.getInstance().getExp())));
        tv_BookExpMax.setText(Integer.toString(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax())));



        expVar.setProgress(pref.getInt("UserExp", ClassUserData.getInstance().getExp()));
        expVar.setMax(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax()));

    }

    public void activity_Move() {

        //팀관리 이동
        ib_BookTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_4Team.class);
                startActivity(intent);
                finish();
            }
        });
        //상점 이동
        ib_BookShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_6Store.class);
                startActivity(intent);
                finish();
            }
        });
        //게임시작이동
        ib_BookStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_7GameStart_Linear_Table.class);
                startActivity(intent);
                finish();
            }
        });
        //옵션팝업
        ib_BookOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Setting.class);
                startActivity(intent);
            }
        });
        //메일팝업
        ib_BookMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Mail.class);
                startActivity(intent);
            }
        });
        //프로필팝업
        ib_BookProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Profile.class);
                startActivity(intent);
            }
        });
        //메인이동
        ib_BookBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void layoutFindID_Book() {
        tv_BookUserName = findViewById(R.id.tv_book_Name);
        tv_BookLevel = findViewById(R.id.tv_book_Level);
        tv_BookDiamond = findViewById(R.id.tv_book_Dia);
        tv_BookGold = findViewById(R.id.tv_book_Gold);
        tv_BookTimeCount = findViewById(R.id.tv_book_Timer);
        tv_BookUserCount = findViewById(R.id.tv_book_Count);
        tv_BookCountMax = findViewById(R.id.tv_book_CountMax);
            tv_BookExpMax= findViewById(R.id.tv_book_RequestExp);
                tv_BookNeedExp= findViewById(R.id.tv_book_NeedExp);
        ib_BookTeam = findViewById(R.id.ib_book_Team);
        ib_BookShop = findViewById(R.id.ib_book_Shop);
        ib_BookStart = findViewById(R.id.ib_book_Start);
        ib_BookOption = findViewById(R.id.ib_book_Option);
        ib_BookMail = findViewById(R.id.ib_book_Mail);
        ib_BookBack = findViewById(R.id.ib_book_Back);
        ib_BookProfile = findViewById(R.id.ib_book_Profile);
    }

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
        setBookText();
        viewStatus();
        tag = "도감Activity";
        Log.e(tag, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Parcelable recyclerViewState;
        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
        // Restore state
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        setBookText();
        hideNaviUI();
        viewStatus();
        /*expVar.setProgress(ClassUserData.getInstance().getExp());
        expVar.setMax(ClassUserData.getInstance().getExpMax());*/
        recyclerView.setAdapter(adapter);
        tag = "도감Activity";
        Log.e(tag, "onResume");
        //Activity_5Book_Detail BookDetail = (Activity_5Book_Detail) Activity_5Book_Detail.activity;
        //supportFinishAfterTransition();
        /*if(BookDetail.isFinishing()==false){
            BookDetail.finish();
        }*/

    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();

    }

}
