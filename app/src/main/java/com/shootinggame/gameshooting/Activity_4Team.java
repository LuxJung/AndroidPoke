package com.shootinggame.gameshooting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;

public class Activity_4Team extends AppCompatActivity {
    private TextView tv_TeamUserName, tv_TeamUserLevel,  tv_TeamUserCountMax
                    , tv_TeamUserDiamond, tv_TeamUserGold, tv_TeamUserNeedExp, tv_TeamUserRequestExp;
    private ImageButton ib_TeamUserProfile, ib_TeamUserCount, ib_TeamUserDiamond, ib_TeamUserGold, ib_TeamUserMail, ib_TeamUserSetting;
    private ImageButton ib_TeamBook, ib_TeamShop, ib_TeamStart,ib_TeamLeft,ib_TeamRight,ib_TeamPowerUp, ib_TeamBack;
    private ImageView iv_SelectIMG;
    private RecyclerView rv_TeamRecycler;
    private RecyclerVIewAdapter_Team adapter;
    private GridLayoutManager layoutManager;
    private String tag;
    private ProgressBar expVar;
    private static TextView tv_TeamTimeCount,tv_TeamUserCount;
    public static String Time;
    public static Activity activity;
    Activity_3Main Main = (Activity_3Main) Activity_3Main.activity;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4team);
        //버튼연결
        layoutFindID_Team();
        //디바이스네비감추기
        hideNaviUI();
        //유저 정보(닉,렙,다야,골드)
        //setTeamText();
        activity_Move();
        viewStatus();
        tag="액티비티 상태";
        expVar = findViewById(R.id.pb_team_ExpBar);
        /*expVar.setProgress(ClassUserData.getInstance().getExp());
        expVar.setMax(ClassUserData.getInstance().getExpMax());*/
        final SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        final SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
        iv_SelectIMG.setImageResource(pref.getInt("PokeIMG",R.drawable.whoareyou));
        Log.e("Main에서 Shared저장값 확인", "\n"+pref.getString("User_Poketmon", "")+"\n");


        activity = Activity_4Team.this;

        adapter = new RecyclerVIewAdapter_Team(getApplicationContext(),Main.getUserPoketmon());

        layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        rv_TeamRecycler.setLayoutManager(layoutManager);
        rv_TeamRecycler.setAdapter(adapter);
        iv_SelectIMG.setImageResource(pref.getInt("PokeIMG",0));
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new ItemTeamClickListener() {
            @Override
            public void onItemClick(RecyclerVIewAdapter_Team.MyViewHolder holder, View view, int position) {
                //어댑터에서 포지션을 가지고 온다.
                int pos = holder.getAdapterPosition();
                int click = Main.getUserPoketmon().get(pos).Image;
                String PokeName = Main.getUserPoketmon().get(pos).Name;
                editor.putString("PokeName",PokeName);
                editor.putInt("PokeIMG",click);
                editor.putInt("POS",pos);
                editor.commit();
                iv_SelectIMG.setImageResource(click);
            }
        });



    }
    public static void set4TeamTimer(){
        Time = get4TeamTimer();
        tv_TeamTimeCount.setText(Time);
    }
    public static void set4TeamTimerText(String a){
        tv_TeamTimeCount.setText(a);

    }
    public static String get4TeamTimer(){
        return tv_TeamTimeCount.getText().toString();
    }
    public static void set4TeamUserCount(String userCount){
        tv_TeamUserCount.setText(userCount);
    }
    public static void TeamViewGone(){
        tv_TeamTimeCount.setVisibility(View.GONE);

    }
    public void viewStatus(){
        if(ClassUserData.getInstance().getMaxCount()==ClassUserData.getInstance().getCount()){
            tv_TeamTimeCount.setVisibility(View.GONE);
        }else if(ClassUserData.getInstance().getMaxCount()>ClassUserData.getInstance().getCount()){
            tv_TeamTimeCount.setVisibility(View.VISIBLE);
        }
    }
    public static void TeamViewVisible(){
        if(ClassUserData.getInstance().getMaxCount()>ClassUserData.getInstance().getCount()){
            tv_TeamTimeCount.setVisibility(View.VISIBLE);
        }
    }
    public void activity_Move(){
        //뒤로가기 이미지 버튼
        ib_TeamBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //캐릭터 강화이동\
        ib_TeamPowerUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Activity_4Team_Powerup.class);
                startActivity(intent);
                finish();
            }
        });
        //도감이동
        ib_TeamBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_5Book.class);
                startActivity(intent);
                finish();//현재 액티비티는 종료되어야 한다.
            }
        });
        //상점이동
        ib_TeamShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_6Store.class);
                startActivity(intent);
                finish();//현재 액티비티는 종료되어야 한다.
            }
        });
        //게임실행창으로
        ib_TeamStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_7GameStart_Linear_Table.class);
                startActivity(intent);
                finish();//현재 액티비티는 종료되어야 한다.
            }
        });
        ib_TeamUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Activity_9PopUp_Profile.class);
                startActivity(intent);
            }
        });
        //셋팅창 팝업
        ib_TeamUserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Setting.class);
                startActivity(intent);
            }
        });

        //메일함 팝업
        ib_TeamUserMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Mail.class);
                startActivity(intent);
            }
        });

    }@SuppressLint("SetTextI18n")
    public void setTeamText(){
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        tv_TeamUserName.setText(pref.getString("UserName", ClassUserData.getInstance().getName()));
        tv_TeamUserLevel.setText(Integer.toString(pref.getInt("UserLevel", ClassUserData.getInstance().getLevel())));
        tv_TeamUserDiamond.setText(Integer.toString(pref.getInt("UserDia", ClassUserData.getInstance().getDiamond())));
        tv_TeamUserGold.setText(Integer.toString( pref.getInt("UserGold", ClassUserData.getInstance().getGold())));
        ib_TeamUserProfile.setImageResource(pref.getInt("UserProfile",ClassUserData.getInstance().getProfile()));
        tv_TeamUserCountMax.setText(Integer.toString( pref.getInt("UserMaxCount",ClassUserData.getInstance().getMaxCount())));
        tv_TeamUserCount.setText(Integer.toString(pref.getInt("UserCount",ClassUserData.getInstance().getCount())));
        tv_TeamTimeCount.setText(ClassUserData.getInstance().getTime());
        tv_TeamUserNeedExp.setText(Integer.toString(pref.getInt("UserExp", ClassUserData.getInstance().getExp())));
        tv_TeamUserRequestExp.setText(Integer.toString(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax())));

        expVar.setProgress(pref.getInt("UserExp", ClassUserData.getInstance().getExp()));
        expVar.setMax(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax()));

    }
    @Override
    protected void onPause() {
        super.onPause();
        hideNaviUI();
        viewStatus();
        tag="팀관리Activity";
        Log.e(tag, "onPause");


    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNaviUI();
        setTeamText();
        viewStatus();
        int exp = Integer.parseInt(tv_TeamUserNeedExp.getText().toString());//앞에꺼
        int expMax = Integer.parseInt(tv_TeamUserRequestExp.getText().toString());//뒤에꺼
        expVar.setProgress(exp);
        expVar.setMax(expMax);
        adapter.notifyDataSetChanged();
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        tag="팀관리Activity";
        Log.e(tag, "onResume");
        Log.e("Team에서 Shared저장값 확인", "\n"+pref.getString("User_Poketmon", "")+"\n");

    }
    public void hideNaviUI() {
        int newUiOptions = 0;
        if(Build.VERSION.SDK_INT>=14){
            newUiOptions^=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        if(Build.VERSION.SDK_INT>=18){
            newUiOptions^=View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
    public void layoutFindID_Team() {
        iv_SelectIMG = findViewById(R.id.iv_team_SelectIMG);
        tv_TeamUserName = findViewById(R.id.tv_team_Name);
        tv_TeamUserLevel= findViewById(R.id.tv_team_Level);
        tv_TeamUserCount=findViewById(R.id.tv_team_Count);
        tv_TeamUserCountMax=findViewById(R.id.tv_team_CountMax);
        tv_TeamTimeCount=findViewById(R.id.tv_team_Timer);
        tv_TeamUserDiamond=findViewById(R.id.tv_team_Diamond);
        tv_TeamUserGold=findViewById(R.id.tv_team_Gold);
        tv_TeamUserNeedExp=findViewById(R.id.tv_team_NeedExp);
        tv_TeamUserRequestExp=findViewById(R.id.tv_team_RequestExp);
        ib_TeamUserProfile=findViewById(R.id.ib_team_Profile);
        ib_TeamUserCount=findViewById(R.id.ib_team_Count);
        ib_TeamUserDiamond=findViewById(R.id.ib_team_Diamond);
        ib_TeamUserGold=findViewById(R.id.ib_team_Gold);
        ib_TeamUserMail=findViewById(R.id.ib_team_Mail);
        ib_TeamUserSetting=findViewById(R.id.ib_team_Setting);
        ib_TeamBook=findViewById(R.id.ib_team_Book);
        ib_TeamShop=findViewById(R.id.ib_team_Shop);
        ib_TeamStart=findViewById(R.id.ib_team_Start);
        ib_TeamLeft=findViewById(R.id.ib_team_Left);
        ib_TeamRight=findViewById(R.id.ib_team_Right);
        ib_TeamPowerUp=findViewById(R.id.ib_team_PowerUp);
        ib_TeamBack=findViewById(R.id.ib_team_Back);
        rv_TeamRecycler=findViewById(R.id.rv_team_Recycler);

    }
}
