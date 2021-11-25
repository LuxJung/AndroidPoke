package com.shootinggame.gameshooting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_7GameStart_Linear_Table extends AppCompatActivity {
    private String tag;
    private ImageButton ib_GameStartBack,ib_GameStartProfile,ib_GameStartMail,ib_GameStartSetting;
    private TextView tv_GameStartName,tv_GameStartLevel,tv_GameStartDia,tv_GameStartGold,
            tv_GameStartUserCountMax, tv_GameStartUserexp,tv_GameStartUserexpMax;
    private Button bt_stage1, bt_stage2;
    private static TextView tv_GameStartUserCount,tv_GameStartTimeCount;
    public static String Time;
    private ProgressBar expVar;
    public Gson gson = new Gson();
    Activity_2Join join = (Activity_2Join) Activity_2Join.activity;
    private RecyclerVIewAdapter_Achieve adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7gamestart_linear_table);
        hideNaviUI();
        layoutFindID_Start();
        //setGameStartText();
        activityMove();
        viewStatus();
        expVar = findViewById(R.id.pb_gamestart_ExpBar);
        /*expVar.setProgress(ClassUserData.getInstance().getExp());
        expVar.setMax(ClassUserData.getInstance().getExpMax());*/


        bt_stage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountChange();
                Intent intent = new Intent(getApplicationContext(), Activity_8InGame_Stage1.class);
                Count_ACHIEVE();//모험 횟수 추가
                startActivity(intent);
                finish();
            }
        });
        bt_stage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
                if(pref.getInt("Stage1",0)==1){
                    CountChange();
                    Intent intent = new Intent(getApplicationContext(), Activity_8InGame_Stage2.class);
                    Count_ACHIEVE();//모험 횟수 추가
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Stage1을 Clear 해주세요!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void whileChange(){
        //while (Service_CountDown_Thread.State.NEW){

        //}
    }
    public static void set7GameStartTimer(){
        Time = get7GameStartTimer();
        tv_GameStartTimeCount.setText(Time);
    }
    public static void set7GameStartTimerText(String a){
        tv_GameStartTimeCount.setText(a);

    }
    public static String get7GameStartTimer(){
        return tv_GameStartTimeCount.getText().toString();
    }
    public static void GameStartViewGone(){
        tv_GameStartTimeCount.setVisibility(View.GONE);
    }
    public static void GameStartViewVisible(){
        tv_GameStartTimeCount.setVisibility(View.VISIBLE);
    }
    public static void set7GameStartUserCount(String userCount){
        tv_GameStartUserCount.setText(userCount);
    }
    public void viewStatus(){
        if(ClassUserData.getInstance().getMaxCount()==ClassUserData.getInstance().getCount()){
            tv_GameStartTimeCount.setVisibility(View.GONE);
        }else if(ClassUserData.getInstance().getMaxCount()>ClassUserData.getInstance().getCount()){
            tv_GameStartTimeCount.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setGameStartText(){
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        tv_GameStartName.setText(pref.getString("UserName", ClassUserData.getInstance().getName()));
        tv_GameStartLevel.setText(Integer.toString(pref.getInt("UserLevel", ClassUserData.getInstance().getLevel())));
        tv_GameStartDia.setText(Integer.toString(pref.getInt("UserDia", ClassUserData.getInstance().getDiamond())));
        tv_GameStartGold.setText(Integer.toString( pref.getInt("UserGold", ClassUserData.getInstance().getGold())));
        ib_GameStartProfile.setImageResource(pref.getInt("UserProfile",ClassUserData.getInstance().getProfile()));
        tv_GameStartUserCount.setText(Integer.toString(pref.getInt("UserCount",ClassUserData.getInstance().getCount())));
        tv_GameStartUserCountMax.setText(Integer.toString( pref.getInt("UserMaxCount",ClassUserData.getInstance().getMaxCount())));
        tv_GameStartTimeCount.setText(ClassUserData.getInstance().getTime());
        tv_GameStartUserexp.setText(Integer.toString(pref.getInt("UserExp", ClassUserData.getInstance().getExp())));
        tv_GameStartUserexpMax.setText(Integer.toString(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax())));

        expVar.setProgress(pref.getInt("UserExp", ClassUserData.getInstance().getExp()));
        expVar.setMax(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax()));
    }

    public void layoutFindID_Start() {
        tv_GameStartName = findViewById(R.id.tv_gamestart_Name);
        tv_GameStartLevel = findViewById(R.id.tv_gamestart_Level);
        tv_GameStartDia = findViewById(R.id.tv_gamestart_Dia);
        tv_GameStartGold = findViewById(R.id.tv_gamestart_Gold);

        tv_GameStartUserexp = findViewById(R.id.tv_gamestart_NeedExp);
                tv_GameStartUserexpMax = findViewById(R.id.tv_gamestart_RequestExp);


        tv_GameStartTimeCount=findViewById(R.id.tv_gamestart_Timer);
        tv_GameStartUserCount = findViewById(R.id.tv_gamestart_Count);
        tv_GameStartUserCountMax=findViewById(R.id.tv_gamestart_CountMax);

        ib_GameStartBack = findViewById(R.id.ib_gamestart_Back);
        ib_GameStartProfile = findViewById(R.id.ib_gamestart_Profile);
        ib_GameStartMail = findViewById(R.id.ib_gamestart_Mail);
        ib_GameStartSetting = findViewById(R.id.ib_gamestart_Setting);

        bt_stage1 = findViewById(R.id.stage1);
        bt_stage2 = findViewById(R.id.stage2);

    }
    public void CountChange(){
        int countChange = ClassUserData.getInstance().getCount() -1;
        ClassUserData.getInstance().setCount(countChange);
        startService(new Intent(getApplicationContext(), Service_CountDown.class));
    }

    public void activityMove() {
        ib_GameStartProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Profile.class);
                startActivity(intent);
            }
        });
        ib_GameStartMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Mail.class);
                startActivity(intent);
            }
        });
        ib_GameStartSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Mail.class);
                startActivity(intent);
            }
        });
        ib_GameStartBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    public void Count_ACHIEVE(){
        SharedPreferences pref_achieve = getSharedPreferences("ACHIEVE", MODE_PRIVATE);//쉐어드 선언 Key : User
        SharedPreferences.Editor editor_achieve = pref_achieve.edit();//쉐어드 사용할 'editor'선언
        int ACHIVE_ADVENTURE = pref_achieve.getInt("ACHIEVE_ADVENTURE",0);
        int NEW_ACHIVE_ADVENTURE = ACHIVE_ADVENTURE+1;
        editor_achieve.putInt("ACHIEVE_ADVENTURE",NEW_ACHIVE_ADVENTURE);
        editor_achieve.commit();
    }

    public void Count_Adventure(){
        SharedPreferences pref = getSharedPreferences("Game", MODE_PRIVATE);//쉐어드 선언 Key : User
        SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
        Type type = new TypeToken<ArrayList<ClassAchieveData>>() {
        }.getType();

        ArrayList<ClassAchieveData> parse = gson.fromJson(pref.getString("Game_Achieve", ""), type);
        //저장되어있는 어레이리스트에서 피츄 업그레이드 하는 방법
        for (int i = 0; i < parse.size(); i++) {
            Log.i("get(i)",
                    parse.get(i).getTitle() + " / " +
                            parse.get(i).getProgress() + " / " +parse.get(i).getProgressmax());
            if (parse.get(i).getTitle().equals("모험횟수")) {
                if (parse.get(i).getProgress()<parse.get(i).getProgressmax()) {
                    int old_Progress = parse.get(i).getProgress();
                    int new_Progress = old_Progress + 1;
                    parse.get(i).setProgress(new_Progress);
                    Log.e("모험횟수",String.valueOf(parse.get(i).getProgress()));
                }
            }
        }
        //업그레이드 된 피츄를 다시 저장해주는 역할
        String ToJson = gson.toJson(parse);
        editor.putString("Game_Achieve", ToJson);
        editor.commit();

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
        viewStatus();
       /* expVar.setProgress(ClassUserData.getInstance().getExp());
        expVar.setMax(ClassUserData.getInstance().getExpMax());*/
        tag = "겜식작Activity";
        Log.e(tag, "onPause");



    }

    @Override
    protected void onResume() {
        super.onResume();
        setGameStartText();
        hideNaviUI();
        viewStatus();
        /*expVar.setProgress(ClassUserData.getInstance().getExp());
        expVar.setMax(ClassUserData.getInstance().getExpMax());*/
        tag = "겜시작Activity";
        Log.e(tag, "onResume");
    }
}
