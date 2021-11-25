package com.shootinggame.gameshooting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_4Team_Powerup extends AppCompatActivity {
    private TextView tv_PowerUserName, tv_PowerUserLevel, tv_PowerUserCountMax,
            tv_PowerUserGold, tv_PowerUserNeedExp, tv_PowerUserRequestExp, tv_PowerPokeName, tv_PowerUserDiamond;
    private ImageButton ib_PowerUserProfile, ib_PowerUserCount, ib_PowerUserDiamond,
            ib_PowerUserGold, ib_PowerUserMail, ib_PowerUserSetting;
    private ImageButton ib_PowerBook, ib_PowerShop, ib_PowerStart, ib_PowerLeft,
            ib_PowerRight, ib_PowerTeam, ib_PowerBack;
    private ImageView iv_powerIMG;
    private String tag;
    private static TextView tv_PowerTimeCount, tv_PowerUserCount;
    public static String Time;
    public Button bt_powerChoice, bt_atkup, bt_hpup, bt_speedup,bt_atkspeedup,bt_missilespeedup,bt_evolution;
    public Gson gson = new Gson();
    private ProgressBar expVar;
    private ProgressBar progressBarATK, progressBarHP,progressBarSPEED,progressBarATKSPEED,progressBarMISSILESPEED;
    private TextView tv_atk, tv_atkmax, tv_hp, tv_hpmax, tv_need_atkup,tv_need_hpup , tv_speed,tv_speedmax,tv_need_speedup, tv_atkspeed,tv_atkspeedmax,tv_need_atkspeedup, tv_missilespeed,tv_missilespeedmax,tv_need_missilespeedup;
    Handler handler = null;
    Activity_3Main Main = (Activity_3Main) Activity_3Main.activity;

    //뒤로가기 재정의
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Activity_4Team.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4team_powerup);

        hideNaviUI();//핸드폰 네비바 숨기기
        layoutFindID_PowerUP();
        //setTeamText();
        activity_Move();
        viewStatus();
        expVar = findViewById(R.id.pb_powerUp_ExpBar);
        /*expVar.setProgress(ClassUserData.getInstance().getExp());
        expVar.setMax(ClassUserData.getInstance().getExpMax());*/
        final SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        final SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언

        tv_PowerPokeName.setText(pref.getString("PokeName", ""));
        iv_powerIMG.setImageResource(pref.getInt("PokeIMG", 0));
        bt_powerChoice.setVisibility(View.GONE);//필요없음 나중에 레이아웃 수정필요
        Type type = new TypeToken<ArrayList<ClassPoketMon>>() {
        }.getType();

        final ArrayList<ClassPoketMon> parse = gson.fromJson(pref.getString("User_Poketmon", ""), type);
        for (int i = 0; i < Main.UserPoketmon.size(); i++) {
            Log.i("get(i)",
                    Main.UserPoketmon.get(i).getPoketMonName() + " / " +
                            Main.UserPoketmon.get(i).getPoketMonHp());
            if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName().equals(pref.getString("PokeName", ""))) {
                tv_atkmax.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxAtk()));
                tv_atk.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonAtk()));
                tv_need_atkup.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonAtk()*100));
                progressBarATK.setProgress(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonAtk());
                progressBarATK.setMax(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxAtk());

                tv_hpmax.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxHp()));
                tv_hp.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonHp()));
                tv_need_hpup.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonHp()*100));
                progressBarHP.setProgress(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonHp());
                progressBarHP.setMax(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxHp());

                tv_speedmax.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeed()));
                tv_speed.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonSpeed()));
                tv_need_speedup.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonSpeed()*100));
                progressBarSPEED.setProgress(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonSpeed());
                progressBarSPEED.setMax(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeed());

                tv_atkspeedmax.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeedAtk()));
                tv_atkspeed.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedAtk()));
                tv_need_atkspeedup.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedAtk()*100));
                progressBarATKSPEED.setProgress(Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedAtk());
                progressBarATKSPEED.setMax(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeedAtk());

                tv_missilespeedmax.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeedMissile()));
                tv_missilespeed.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedMissile()));
                tv_need_missilespeedup.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedMissile()*100));
                progressBarMISSILESPEED.setProgress(Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedMissile());
                progressBarMISSILESPEED.setMax(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeedMissile());
            }

        }

        bt_atkup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pref.getInt("UserGold", ClassUserData.getInstance().getGold()) > Integer.parseInt(tv_need_atkup.getText().toString())){

                    int UserCoin =  pref.getInt("UserGold", ClassUserData.getInstance().getGold());
                    int old = Integer.parseInt(tv_atk.getText().toString());
                    int max = Integer.parseInt(tv_atkmax.getText().toString());
                    int AtkUpCoin = Integer.parseInt(tv_need_atkup.getText().toString());
                    int newint = old +1;
                    int AfterCostCoin = UserCoin - AtkUpCoin;
                    int newintd = newint *100;
                    if(old<max){
                    ATK_Upgrade();
                    progressBarATK.incrementProgressBy(1);
                    editor.putInt("UserGold",AfterCostCoin);
                    editor.apply();
                    tv_PowerUserGold.setText(String.valueOf(AfterCostCoin));
                    tv_atk.setText(String.valueOf(newint));
                    tv_need_atkup.setText(String.valueOf(newintd));
                    }
                }
            }
        });

        bt_hpup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pref.getInt("UserGold", ClassUserData.getInstance().getGold()) > Integer.parseInt(tv_need_hpup.getText().toString())){
                    int old = Integer.parseInt(tv_hp.getText().toString());
                    int max = Integer.parseInt(tv_hpmax.getText().toString());
                    int UserCoin =  pref.getInt("UserGold", ClassUserData.getInstance().getGold());
                    int HpUpCoin = Integer.parseInt(tv_need_hpup.getText().toString());
                    int newint = old + 1;
                    int AfterCostCoin = UserCoin - HpUpCoin;
                    int newintd = newint * 100;
                    if(old<max) {
                        HP_Upgrade();
                        progressBarHP.incrementProgressBy(1);
                        editor.putInt("UserGold", AfterCostCoin);
                        editor.commit();
                        tv_PowerUserGold.setText(String.valueOf(AfterCostCoin));
                        tv_hp.setText(String.valueOf(newint));
                        tv_need_hpup.setText(String.valueOf(newintd));
                    }

                }
            }
        });

        bt_speedup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pref.getInt("UserGold", ClassUserData.getInstance().getGold()) > Integer.parseInt(tv_need_speedup.getText().toString())){
                    int old = Integer.parseInt(tv_speed.getText().toString());
                    int max = Integer.parseInt(tv_speedmax.getText().toString());
                    int UserCoin =  pref.getInt("UserGold", ClassUserData.getInstance().getGold());
                    int SpeedUpCoin = Integer.parseInt(tv_need_speedup.getText().toString());
                    int newint = old + 1;
                    int AfterCostCoin = UserCoin - SpeedUpCoin;
                    int newintd = newint * 100;
                    if(old<max) {
                        SPEED_Upgrade();
                        progressBarSPEED.incrementProgressBy(1);
                        editor.putInt("UserGold", AfterCostCoin);
                        editor.commit();
                        tv_PowerUserGold.setText(String.valueOf(AfterCostCoin));
                        tv_speed.setText(String.valueOf(newint));
                        tv_need_speedup.setText(String.valueOf(newintd));
                    }

                }
            }
        });

        bt_atkspeedup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pref.getInt("UserGold", ClassUserData.getInstance().getGold()) > Integer.parseInt(tv_need_atkspeedup.getText().toString())){
                    int old = Integer.parseInt(tv_atkspeed.getText().toString());
                    int max = Integer.parseInt(tv_atkspeedmax.getText().toString());
                    int UserCoin =  pref.getInt("UserGold", ClassUserData.getInstance().getGold());
                    int AtkSpeedUpCoin = Integer.parseInt(tv_need_atkspeedup.getText().toString());
                    int newint = old + 1;
                    int AfterCostCoin = UserCoin - AtkSpeedUpCoin;
                    int newintd = newint * 100;
                    if(old<max) {
                        ATKSPEED_Upgrade();
                        progressBarATKSPEED.incrementProgressBy(1);
                        editor.putInt("UserGold", AfterCostCoin);
                        editor.commit();
                        tv_PowerUserGold.setText(String.valueOf(AfterCostCoin));
                        tv_atkspeed.setText(String.valueOf(newint));
                        tv_need_atkspeedup.setText(String.valueOf(newintd));
                    }

                }
            }
        });

        bt_missilespeedup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pref.getInt("UserGold", ClassUserData.getInstance().getGold()) > Integer.parseInt(tv_need_missilespeedup.getText().toString())){
                    int old = Integer.parseInt(tv_missilespeed.getText().toString());
                    int max = Integer.parseInt(tv_missilespeedmax.getText().toString());
                    int UserCoin =  pref.getInt("UserGold", ClassUserData.getInstance().getGold());
                    int MissileSpeedUpCoin = Integer.parseInt(tv_need_missilespeedup.getText().toString());
                    int newint = old + 1;
                    int AfterCostCoin = UserCoin - MissileSpeedUpCoin;
                    int newintd = newint * 100;
                    if(old<max) {
                        MISSILESPEED_Upgrade();
                        progressBarMISSILESPEED.incrementProgressBy(1);
                        editor.putInt("UserGold", AfterCostCoin);
                        editor.commit();
                        tv_PowerUserGold.setText(String.valueOf(AfterCostCoin));
                        tv_missilespeed.setText(String.valueOf(newint));
                        tv_need_missilespeedup.setText(String.valueOf(newintd));
                    }

                }
            }
        });

        bt_evolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int atk = Integer.parseInt(tv_atk.getText().toString());
                int atkmax = Integer.parseInt(tv_atkmax.getText().toString());
                int hp = Integer.parseInt(tv_hp.getText().toString());
                int hpmax = Integer.parseInt(tv_hpmax.getText().toString());
                int speed = Integer.parseInt(tv_speed.getText().toString());
                int speedmax = Integer.parseInt(tv_speedmax.getText().toString());
                int atkspeed = Integer.parseInt(tv_atkspeed.getText().toString());
                int atkspeedmax = Integer.parseInt(tv_atkspeedmax.getText().toString());
                int missilespeed = Integer.parseInt(tv_missilespeed.getText().toString());
                int missilespeedmax = Integer.parseInt(tv_missilespeedmax.getText().toString());

                    if(atk==atkmax&&hp==hpmax&&speed==speedmax&&atkspeed==atkspeedmax&&missilespeed==missilespeedmax) {
                        Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Evolution.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),tv_PowerPokeName.getText().toString()+"의 업그레이드를 전부 완료해주세요",Toast.LENGTH_LONG).show();
                    }


            }
        });
        if(tv_PowerPokeName.getText().toString().equals("거북왕")||
                tv_PowerPokeName.getText().toString().equals("리자몽")||
                tv_PowerPokeName.getText().toString().equals("이상해꽃")||
                tv_PowerPokeName.getText().toString().equals("라이츄")){
            bt_evolution.setClickable(false);
            bt_evolution.setText("최고 진화단계 입니다");
        }

    }

    //타이머 관련 이거 수정 필요함
    public static void set4TeamPowerTimer() {
        Time = get4TeamPowerTimer();
        tv_PowerTimeCount.setText(Time);
    }

    public static void set4TeamPowerTimerText(String a) {
        tv_PowerTimeCount.setText(a);

    }

    public static String get4TeamPowerTimer() {
        return tv_PowerTimeCount.getText().toString();
    }

    public static void set4TeamPowerUserCount(String userCount) {
        tv_PowerUserCount.setText(userCount);
    }

    public void viewStatus() {
        if (ClassUserData.getInstance().getMaxCount() == ClassUserData.getInstance().getCount()) {
            tv_PowerTimeCount.setVisibility(View.GONE);
        } else if (ClassUserData.getInstance().getMaxCount() > ClassUserData.getInstance().getCount()) {
            tv_PowerTimeCount.setVisibility(View.VISIBLE);
        }
    }

    public static void TeamPowerViewGone() {
        tv_PowerTimeCount.setVisibility(View.GONE);
    }

    public static void TeamPowerViewVisible() {
        tv_PowerTimeCount.setVisibility(View.VISIBLE);

    }
    //여기까지 타이머 관련

    public void HP_Upgrade() {
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
        Type type = new TypeToken<ArrayList<ClassPoketMon>>() {
        }.getType();

        ArrayList<ClassPoketMon> parse = gson.fromJson(pref.getString("User_Poketmon", ""), type);
        //저장되어있는 어레이리스트에서 피츄 업그레이드 하는 방법
        for (int i = 0; i < Main.UserPoketmon.size(); i++) {
            Log.i("Main.UserPoketmon 업그레이드 전",
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName() + " / " +"HP: "+
                            Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonHp());
            if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName().equals(pref.getString("PokeName", ""))) {
                if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonHp()<Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxHp()) {
                    int old_hp = Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonHp();
                    int new_hp = old_hp + 1;
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).setPoketMonHp(new_hp);
                }
            }

        }
        //업그레이드 된 피츄를 다시 저장해주는 역할
        String ToJson = gson.toJson(Main.UserPoketmon);
        editor.putString("User_Poketmon", ToJson);
        editor.apply();

        //업그레이드 되었는지 확인해보기
        for (int i = 0; i < Main.UserPoketmon.size(); i++) {
            Log.i("Main.UserPoketmon 업그레이드 후",
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName() + " / " +"HP: "+
                            Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonHp());
        }
    }

    public void ATK_Upgrade() {
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
        Type type = new TypeToken<ArrayList<ClassPoketMon>>() {
        }.getType();

        ArrayList<ClassPoketMon> parse = gson.fromJson(pref.getString("User_Poketmon", ""), type);
        //저장되어있는 어레이리스트에서 피츄 업그레이드 하는 방법
        for (int i = 0; i < Main.UserPoketmon.size(); i++) {
            Log.i("Main.UserPoketmon 업그레이드 전",
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName() + " / " +"ATK: "+
                            Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonAtk());
            if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName().equals(pref.getString("PokeName", ""))) {
                if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonAtk()<Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxAtk()) {
                    int old_atk = Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonAtk();
                    int new_atk = old_atk + 1;
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).setPoketMonAtk(new_atk);
                }
            }
        }
        //업그레이드 된 피츄를 다시 저장해주는 역할
        String ToJson = gson.toJson(Main.UserPoketmon);
        editor.putString("User_Poketmon", ToJson);
        editor.apply();

        //업그레이드 되었는지 확인해보기
        for (int i = 0; i < Main.UserPoketmon.size(); i++) {
            Log.i("Main.UserPoketmon 업그레이드 후",
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName() + " / " +"ATK: "+
                            Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonAtk());
        }
    }

    public void SPEED_Upgrade() {
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
        Type type = new TypeToken<ArrayList<ClassPoketMon>>() {
        }.getType();
        ArrayList<ClassPoketMon> parse = gson.fromJson(pref.getString("User_Poketmon", ""), type);
        //저장되어있는 어레이리스트에서 피츄 업그레이드 하는 방법
        for (int i = 0; i < Main.UserPoketmon.size(); i++) {
            Log.i("Main.UserPoketmon 업그레이드 전",
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName() + " / " +"SPEED: "+
                            Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonSpeed());
            if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName().equals(pref.getString("PokeName", ""))) {
                if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonSpeed()
                        <Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeed()) {
                    int old_speed = Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonSpeed();
                    int new_speed = old_speed + 1;
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).setPoketMonSpeed(new_speed);
                }
            }
        }
        //업그레이드 된 피츄를 다시 저장해주는 역할
        String ToJson = gson.toJson(Main.UserPoketmon);
        editor.putString("User_Poketmon", ToJson);
        editor.apply();

        //업그레이드 되었는지 확인해보기
        for (int i = 0; i < Main.UserPoketmon.size(); i++) {
            Log.i("Main.UserPoketmon 업그레이드 후",
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName() + " / " +"SPEED: "+
                            Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonSpeed());
        }
    }
    public void ATKSPEED_Upgrade() {
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
        Type type = new TypeToken<ArrayList<ClassPoketMon>>() {
        }.getType();

        ArrayList<ClassPoketMon> parse = gson.fromJson(pref.getString("User_Poketmon", ""), type);
        //저장되어있는 어레이리스트에서 피츄 업그레이드 하는 방법
        for (int i = 0; i < Main.UserPoketmon.size(); i++) {
            Log.i("Main.UserPoketmon 업그레이드 전",
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName() + " / " + "ATK SPEED: "+
                            Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedAtk());
            if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName().equals(pref.getString("PokeName", ""))) {
                if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedAtk()
                        <Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeedAtk()) {
                    int old_atkspeed = Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedAtk();
                    int new_atkspeed = old_atkspeed + 1;
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).setSpeedAtk(new_atkspeed);
                }
            }
        }
        //업그레이드 된 피츄를 다시 저장해주는 역할
        String ToJson = gson.toJson(Main.UserPoketmon);
        editor.putString("User_Poketmon", ToJson);
        editor.apply();

        //업그레이드 되었는지 확인해보기
        for (int i = 0; i < Main.UserPoketmon.size(); i++) {
            Log.i("Main.UserPoketmon 업그레이드 후",
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName() + " / " +"ATK SPEED: "+
                            Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedAtk());
        }
    }
    public void MISSILESPEED_Upgrade() {
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
        Type type = new TypeToken<ArrayList<ClassPoketMon>>() {
        }.getType();

        ArrayList<ClassPoketMon> parse = gson.fromJson(pref.getString("User_Poketmon", ""), type);
        //저장되어있는 어레이리스트에서 피츄 업그레이드 하는 방법
        for (int i = 0; i < Main.UserPoketmon.size(); i++) {
            Log.i("Main.UserPoketmon 업그레이드 전",
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName() + " / " + "MISSILE SPEED: "+
                            Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedMissile());
            if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName().equals(pref.getString("PokeName", ""))) {
                if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedMissile()
                        <Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeedMissile()) {
                    int old_missilespeed = Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedMissile();
                    int new_missilespeed = old_missilespeed + 1;
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).setSpeedMissile(new_missilespeed);
                }
            }
        }
        //업그레이드 된 피츄를 다시 저장해주는 역할
        String ToJson = gson.toJson(Main.UserPoketmon);
        editor.putString("User_Poketmon", ToJson);
        editor.apply();

        //업그레이드 되었는지 확인해보기
        for (int i = 0; i < Main.UserPoketmon.size(); i++) {
            Log.i("Main.UserPoketmon 업그레이드 후",
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName() + " / " +"MISSILE SPEED: "+
                            Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedMissile());
        }
    }

    //액티비티 이동메소드
    public void activity_Move() {
        //뒤로가기 이미지버튼
        ib_PowerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_4Team.class);
                startActivity(intent);
                finish();
            }
        });
        //팀 이동버튼(Activity_4Team)
        ib_PowerTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_4Team.class);
                startActivity(intent);
                finish();
            }
        });


        //팝업 프로필
        ib_PowerUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Profile.class);
                startActivity(intent);
            }
        });

        //팝업 셋팅
        ib_PowerUserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Setting.class);
                startActivity(intent);
            }
        });

        //팝업 메일
        ib_PowerUserMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Mail.class);
                startActivity(intent);
            }
        });


        //도감 이동버튼
        ib_PowerBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_5Book.class);
                startActivity(intent);
                finish();
            }
        });
        //상점 이동버튼
        ib_PowerShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_6Store.class);
                startActivity(intent);
                finish();
            }
        });
        //게임 시작버튼
        ib_PowerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_7GameStart_Linear_Table.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //버튼연결
    public void layoutFindID_PowerUP() {
        tv_PowerUserName = findViewById(R.id.tv_powerUp_Name);
        tv_PowerUserLevel = findViewById(R.id.tv_powerUp_Level);

        tv_hp = findViewById(R.id.tv_powerUp_HP);
        tv_hpmax = findViewById(R.id.tv_powerUp_HPMAX);
        tv_need_hpup = findViewById(R.id.tv_powerUp_HP_NEED);
        progressBarHP = findViewById(R.id.progress_powerUp_HP);
        bt_hpup = findViewById(R.id.bt_powerUp_HP);

        tv_atk = findViewById(R.id.tv_powerUp_ATK);
        tv_atkmax = findViewById(R.id.tv_powerUp_ATKMAX);
        tv_need_atkup = findViewById(R.id.tv_powerUp_ATK_NEED);
        progressBarATK = findViewById(R.id.progress_powerUp_ATK);
        bt_atkup = findViewById(R.id.bt_powerUp_ATK);

        tv_speed = findViewById(R.id.tv_powerUp_SPEED);
        tv_speedmax = findViewById(R.id.tv_powerUp_SPEEDPMAX);
        tv_need_speedup = findViewById(R.id.tv_powerUp_SPEED_NEED);
        progressBarSPEED = findViewById(R.id.progress_powerUp_SPEED);
        bt_speedup = findViewById(R.id.bt_powerUp_SPEED);

        tv_atkspeed = findViewById(R.id.tv_powerUp_ATKSPEED);
        tv_atkspeedmax = findViewById(R.id.tv_powerUp_ATKSPEEDMAX);
        tv_need_atkspeedup = findViewById(R.id.tv_powerUp_ATKSPEED_NEED);
        progressBarATKSPEED = findViewById(R.id.progress_powerUp_ATKSPEED);
        bt_atkspeedup = findViewById(R.id.bt_powerUp_ATKSPEED);

        tv_missilespeed = findViewById(R.id.tv_powerUp_MISSILESPEED);
        tv_missilespeedmax = findViewById(R.id.tv_powerUp_MISSILESPEEDMAX);
        tv_need_missilespeedup = findViewById(R.id.tv_powerUp_MISSILESPEED_NEED);
        progressBarMISSILESPEED = findViewById(R.id.progress_powerUp_MISSILESPEED);
        bt_missilespeedup = findViewById(R.id.bt_powerUp_MISSILESPEED);

        tv_PowerUserCount = findViewById(R.id.tv_powerUp_Count);
        tv_PowerUserCountMax = findViewById(R.id.tv_powerUp_CountMax);
        tv_PowerTimeCount = findViewById(R.id.tv_powerUp_Timer);

        bt_powerChoice = findViewById(R.id.bt_powerUp_Choice);
        bt_evolution = findViewById(R.id.bt_powerUp_EVOLUTION);

        iv_powerIMG = findViewById(R.id.iv_powerUp_IMG);

        tv_PowerUserGold = findViewById(R.id.tv_powerUp_Gold);
        tv_PowerUserDiamond = findViewById(R.id.tv_powerUp_Diamond);
        tv_PowerUserNeedExp = findViewById(R.id.tv_powerUp_NeedExp);
        tv_PowerUserRequestExp = findViewById(R.id.tv_powerUp_RequestExp);
        ib_PowerUserProfile = findViewById(R.id.ib_powerUp_Profile);
        ib_PowerUserCount = findViewById(R.id.ib_powerUp_Count);
        ib_PowerUserDiamond = findViewById(R.id.ib_powerUp_Diamond);
        ib_PowerUserGold = findViewById(R.id.ib_powerUp_Gold);
        ib_PowerUserMail = findViewById(R.id.ib_powerUp_Mail);
        ib_PowerUserSetting = findViewById(R.id.ib_powerUp_Setting);
        ib_PowerBook = findViewById(R.id.ib_powerUp_Book);
        ib_PowerShop = findViewById(R.id.ib_powerUp_Shop);
        ib_PowerStart = findViewById(R.id.ib_powerUp_Start);
        ib_PowerLeft = findViewById(R.id.ib_powerUp_Left);
        ib_PowerRight = findViewById(R.id.ib_powerUp_Right);
        ib_PowerTeam = findViewById(R.id.ib_powerUp_Team);
        ib_PowerBack = findViewById(R.id.ib_powerUp_Back);
        tv_PowerPokeName = findViewById(R.id.ib_powerUp_PoketmonName);
    }

    //디바이스 네비 감추기
    @SuppressLint("ObsoleteSdkInt")
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


    @SuppressLint("SetTextI18n")
    //유저 정보(닉,렙,다야,골드)
    public void setTeamText() {
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        tv_PowerUserName.setText(pref.getString("UserName", ClassUserData.getInstance().getName()));
        tv_PowerUserLevel.setText(Integer.toString(pref.getInt("UserLevel", ClassUserData.getInstance().getLevel())));
        tv_PowerUserDiamond.setText(Integer.toString(pref.getInt("UserDia", ClassUserData.getInstance().getDiamond())));
        tv_PowerUserGold.setText(Integer.toString(pref.getInt("UserGold", ClassUserData.getInstance().getGold())));
        ib_PowerUserProfile.setImageResource(pref.getInt("UserProfile", ClassUserData.getInstance().getProfile()));
        tv_PowerUserCount.setText(Integer.toString(pref.getInt("UserCount", ClassUserData.getInstance().getCount())));
        tv_PowerUserCountMax.setText(Integer.toString(pref.getInt("UserMaxCount", ClassUserData.getInstance().getMaxCount())));
        tv_PowerTimeCount.setText(ClassUserData.getInstance().getTime());
        tv_PowerUserNeedExp.setText(Integer.toString(pref.getInt("UserExp", ClassUserData.getInstance().getExp())));
        tv_PowerUserRequestExp.setText(Integer.toString(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax())));


        expVar.setProgress(pref.getInt("UserExp", ClassUserData.getInstance().getExp()));
        expVar.setMax(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNaviUI();
        setTeamText();
        viewStatus();
        tag = "팀업글Activity";
        Log.e(tag, "onResume");
        final SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        Type type = new TypeToken<ArrayList<ClassPoketMon>>() {
        }.getType();

        final ArrayList<ClassPoketMon> parse = gson.fromJson(pref.getString("User_Poketmon", ""), type);
        for (int i = 0; i < Main.UserPoketmon.size(); i++) {
            Log.i("get(i)",
                    Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName() + " / " +
                            Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonHp());
            if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName().equals(pref.getString("PokeName", ""))) {
                tv_atkmax.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxAtk()));
                tv_atk.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonAtk()));
                tv_need_atkup.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonAtk()*100));
                progressBarATK.setProgress(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonAtk());
                progressBarATK.setMax(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxAtk());

                tv_hpmax.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxHp()));
                tv_hp.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonHp()));
                tv_need_hpup.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonHp()*100));
                progressBarHP.setProgress(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonHp());
                progressBarHP.setMax(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxHp());

                tv_speedmax.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeed()));
                tv_speed.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonSpeed()));
                tv_need_speedup.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonSpeed()*100));
                progressBarSPEED.setProgress(Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonSpeed());
                progressBarSPEED.setMax(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeed());

                tv_atkspeedmax.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeedAtk()));
                tv_atkspeed.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedAtk()));
                tv_need_atkspeedup.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedAtk()*100));
                progressBarATKSPEED.setProgress(Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedAtk());
                progressBarATKSPEED.setMax(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeedAtk());

                tv_missilespeedmax.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeedMissile()));
                tv_missilespeed.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedMissile()));
                tv_need_missilespeedup.setText(String.valueOf(Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedMissile()*100));
                progressBarMISSILESPEED.setProgress(Main.UserPoketmon.get(pref.getInt("POS", 0)).getSpeedMissile());
                progressBarMISSILESPEED.setMax(Main.UserPoketmon.get(pref.getInt("POS", 0)).getMaxSpeedMissile());
            }

        }
        tv_PowerPokeName.setText(pref.getString("PokeName", ""));
        iv_powerIMG.setImageResource(pref.getInt("PokeIMG", 0));

        if(tv_PowerPokeName.getText().toString().equals("거북왕")||
                tv_PowerPokeName.getText().toString().equals("리자몽")||
                tv_PowerPokeName.getText().toString().equals("이상해꽃")||
                tv_PowerPokeName.getText().toString().equals("라이츄")){
            bt_evolution.setClickable(false);
            bt_evolution.setText("최고 진화단계 입니다.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideNaviUI();
        setTeamText();
        viewStatus();
        /*expVar.setProgress(ClassUserData.getInstance().getExp());
        expVar.setMax(ClassUserData.getInstance().getExpMax());*/
        tag = "팀업글Activity";
        Log.e(tag, "onPause");
    }
}
