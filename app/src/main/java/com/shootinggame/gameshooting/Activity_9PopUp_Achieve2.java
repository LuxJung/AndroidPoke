package com.shootinggame.gameshooting;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_9PopUp_Achieve2 extends AppCompatActivity {
    private ImageButton BossGET, MidBossGET, NomalGET, AdventureGET, DieGET, CLOSE;
    private TextView BossProgress, BossMaxProgress, BossDia, BossCoin,
            MidBossProgress, MidBossMaxProgress, MidBossDia, MidBossCoin,
            NomalProgress, NomalMaxProgress, NomalDia, NomalCoin,
            AdventureProgress, AdventureMaxProgress, AdventureDia, AdventureCoin,
            DieProgress, DieMaxProgress, DieDia, DieCoin;
    private ProgressBar BossProgressBar, MidBossProgressBar, NomalProgressBar, AdventureProgressBar, DieProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_9popup_achieve2);
        FindID();

        hideNaviUI();
        Setinit();
        BOSS_ACHIEVE_BTN ();
        MIDBOSS_ACHIEVE_BTN ();
        NOMAL_ACHIEVE_BTN ();
        ADVENTURE_ACHIEVE_BTN ();
        DIE_ACHIEVE_BTN ();

        CLOSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Setinit();
        BOSS_ACHIEVE_BTN ();
        MIDBOSS_ACHIEVE_BTN ();
        NOMAL_ACHIEVE_BTN ();
        ADVENTURE_ACHIEVE_BTN ();
        DIE_ACHIEVE_BTN ();

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
    public void BOSS_ACHIEVE_BTN () {         //BOSS
        BossGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언

                SharedPreferences pref_achieve = getSharedPreferences("ACHIEVE", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor_achieve = pref_achieve.edit();//쉐어드 사용할 'editor'선언

                int userDia = pref.getInt("UserDia", ClassUserData.getInstance().getDiamond());
                int userCoin = pref.getInt("UserGold", ClassUserData.getInstance().getGold());

                int boss = pref_achieve.getInt("ACHIEVE_BOSSKILL", 0);
                int bossMax = pref_achieve.getInt("ACHIEVE_BOSSKILLMAX", 20);
                int bossDia = pref_achieve.getInt("ACHIEVE_BOSSKILLDIA", 50);
                int bossCoin = pref_achieve.getInt("ACHIEVE_BOSSKILLCOIN", 100);

                if (boss >= bossMax) {
                    int newUserDia = userDia + bossDia;
                    int newUserCoin = userCoin + bossCoin;
                    editor.putInt("UserDia", newUserDia);
                    editor.putInt("UserGold", newUserCoin);
                    editor.commit();

                    int newBoss = boss - bossMax;
                    int newBossMax = bossMax * 2;
                    int newBossDia = bossDia * 2;
                    int newBossCoin = bossCoin * 2;

                    editor_achieve.putInt("ACHIEVE_BOSSKILL", newBoss);
                    editor_achieve.putInt("ACHIEVE_BOSSKILLMAX", newBossMax);
                    editor_achieve.putInt("ACHIEVE_BOSSKILLDIA", newBossDia);
                    editor_achieve.putInt("ACHIEVE_BOSSKILLCOIN", newBossCoin);
                    editor_achieve.commit();

                    BossProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_BOSSKILL", 0)));
                    BossMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_BOSSKILLMAX", 0)));
                    BossDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_BOSSKILLDIA", 0)));
                    BossCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_BOSSKILLCOIN", 0)));
                    BossProgressBar.setProgress(newBoss);
                    BossProgressBar.setMax(newBossMax);
                    Setinit();
                } else {
                    Setinit();
                    Toast.makeText(getApplicationContext(), "아직 달성하지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
                BossProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_BOSSKILL", 0)));
                BossMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_BOSSKILLMAX", 0)));
                BossDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_BOSSKILLDIA", 0)));
                BossCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_BOSSKILLCOIN", 0)));
                BossProgressBar.setProgress(pref_achieve.getInt("ACHIEVE_BOSSKILL", 0));
                BossProgressBar.setMax(pref_achieve.getInt("ACHIEVE_BOSSKILLMAX", 0));
            }
        });
        //BOSS
        Setinit();
    }
    public void MIDBOSS_ACHIEVE_BTN () {        //MIDBOSS
        MidBossGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언

                SharedPreferences pref_achieve = getSharedPreferences("ACHIEVE", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor_achieve = pref_achieve.edit();//쉐어드 사용할 'editor'선언

                int userDia = pref.getInt("UserDia", ClassUserData.getInstance().getDiamond());
                int userCoin = pref.getInt("UserGold", ClassUserData.getInstance().getGold());

                int midboss = pref_achieve.getInt("ACHIEVE_MIDBOSSKILL", 0);
                int midbossMax = pref_achieve.getInt("ACHIEVE_MIDBOSSKILLMAX", 10);
                int midbossDia = pref_achieve.getInt("ACHIEVE_MIDBOSSKILLDIA", 30);
                int midbossCoin = pref_achieve.getInt("ACHIEVE_MIDBOSSKILLCOIN", 60);

                if (midboss >= midbossMax) {
                    int newUserDia = userDia + midbossDia;
                    int newUserCoin = userCoin + midbossCoin;
                    editor.putInt("UserDia", newUserDia);
                    editor.putInt("UserGold", newUserCoin);
                    editor.commit();

                    int newMidBoss = midboss - midbossMax;
                    int newMidBossMax = midbossMax * 2;
                    int newMidBossDia = midbossDia * 2;
                    int newMidBossCoin = midbossCoin * 2;

                    editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", newMidBoss);
                    editor_achieve.putInt("ACHIEVE_MIDBOSSKILLMAX", newMidBossMax);
                    editor_achieve.putInt("ACHIEVE_MIDBOSSKILLDIA", newMidBossDia);
                    editor_achieve.putInt("ACHIEVE_MIDBOSSKILLCOIN", newMidBossCoin);
                    editor_achieve.commit();

                    MidBossProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_MIDBOSSKILL", 0)));
                    MidBossMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_MIDBOSSKILLMAX", 0)));
                    MidBossDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_MIDBOSSKILLDIA", 0)));
                    MidBossCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_MIDBOSSKILLCOIN", 0)));
                    MidBossProgressBar.setProgress(newMidBoss);
                    MidBossProgressBar.setMax(newMidBossMax);
                    Setinit();
                } else {
                    Setinit();
                    Toast.makeText(getApplicationContext(), "아직 달성하지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
                MidBossProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_MIDBOSSKILL", 0)));
                MidBossMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_MIDBOSSKILLMAX", 0)));
                MidBossDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_MIDBOSSKILLDIA", 0)));
                MidBossCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_MIDBOSSKILLCOIN", 0)));
                MidBossProgressBar.setProgress(pref_achieve.getInt("ACHIEVE_MIDBOSSKILL", 0));
                MidBossProgressBar.setMax(pref_achieve.getInt("ACHIEVE_MIDBOSSKILLMAX", 0));
            }
        });
        //MIDBOSS
        Setinit();
    }
    public void NOMAL_ACHIEVE_BTN () {//NOMAL
        NomalGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언

                SharedPreferences pref_achieve = getSharedPreferences("ACHIEVE", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor_achieve = pref_achieve.edit();//쉐어드 사용할 'editor'선언

                int userDia = pref.getInt("UserDia", ClassUserData.getInstance().getDiamond());
                int userCoin = pref.getInt("UserGold", ClassUserData.getInstance().getGold());

                int nomal = pref_achieve.getInt("ACHIEVE_NOMALKILL", 0);
                int nomalMax = pref_achieve.getInt("ACHIEVE_NOMALKILLMAX", 50);
                int nomalDia = pref_achieve.getInt("ACHIEVE_NOMALKILLDIA", 10);
                int nomalCoin = pref_achieve.getInt("ACHIEVE_NOMALKILLCOIN", 20);

                if (nomal >= nomalMax) {
                    int newUserDia = userDia + nomalDia;
                    int newUserCoin = userCoin + nomalCoin;
                    editor.putInt("UserDia", newUserDia);
                    editor.putInt("UserGold", newUserCoin);
                    editor.commit();

                    int newNomal = nomal - nomalMax;
                    int newNomalMax = nomalMax * 2;
                    int newNomalDia = nomalDia * 2;
                    int newNomalCoin = nomalCoin * 2;

                    editor_achieve.putInt("ACHIEVE_NOMALKILL", newNomal);
                    editor_achieve.putInt("ACHIEVE_NOMALKILLMAX", newNomalMax);
                    editor_achieve.putInt("ACHIEVE_NOMALKILLDIA", newNomalDia);
                    editor_achieve.putInt("ACHIEVE_NOMALKILLCOIN", newNomalCoin);
                    editor_achieve.commit();

                    NomalProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_NOMALKILL", 0)));
                    NomalMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_NOMALKILLMAX", 0)));
                    NomalDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_NOMALKILLDIA", 0)));
                    NomalCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_NOMALKILLCOIN", 0)));
                    NomalProgressBar.setProgress(newNomal);
                    NomalProgressBar.setMax(newNomalMax);
                    Setinit();
                } else {
                    Setinit();
                    Toast.makeText(getApplicationContext(), "아직 달성하지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
                NomalProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_NOMALKILL", 0)));
                NomalMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_NOMALKILLMAX", 0)));
                NomalDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_NOMALKILLDIA", 0)));
                NomalCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_NOMALKILLCOIN", 0)));
                NomalProgressBar.setProgress(pref_achieve.getInt("ACHIEVE_NOMALKILL", 0));
                NomalProgressBar.setMax(pref_achieve.getInt("ACHIEVE_NOMALKILLMAX", 0));
            }
        });
        //NOMAL
        Setinit();
    }
    public void ADVENTURE_ACHIEVE_BTN () {  //ADVENTURE
        AdventureGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언

                SharedPreferences pref_achieve = getSharedPreferences("ACHIEVE", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor_achieve = pref_achieve.edit();//쉐어드 사용할 'editor'선언

                int userDia = pref.getInt("UserDia", ClassUserData.getInstance().getDiamond());
                int userCoin = pref.getInt("UserGold", ClassUserData.getInstance().getGold());

                int adVenture = pref_achieve.getInt("ACHIEVE_ADVENTURE", 0);
                int adVentureMax = pref_achieve.getInt("ACHIEVE_ADVENTUREMAX", 20);
                int adVentureDia = pref_achieve.getInt("ACHIEVE_ADVENTUREDIA", 10);
                int adVentureCoin = pref_achieve.getInt("ACHIEVE_ADVENTURECOIN", 20);

                if (adVenture >= adVentureMax) {
                    int newUserDia = userDia + adVentureDia;
                    int newUserCoin = userCoin + adVentureCoin;
                    editor.putInt("UserDia", newUserDia);
                    editor.putInt("UserGold", newUserCoin);
                    editor.commit();

                    int newadVenture = adVenture - adVentureMax;
                    int newadVentureMax = adVentureMax * 2;
                    int newadVentureDia = adVentureDia * 2;
                    int newadVentureCoin = adVentureCoin * 2;

                    editor_achieve.putInt("ACHIEVE_ADVENTURE", newadVenture);
                    editor_achieve.putInt("ACHIEVE_ADVENTUREMAX", newadVentureMax);
                    editor_achieve.putInt("ACHIEVE_ADVENTUREDIA", newadVentureDia);
                    editor_achieve.putInt("ACHIEVE_ADVENTURECOIN", newadVentureCoin);
                    editor_achieve.commit();

                    AdventureProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_ADVENTURE", 0)));
                    AdventureMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_ADVENTUREMAX", 0)));
                    AdventureDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_ADVENTUREDIA", 0)));
                    AdventureCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_ADVENTURECOIN", 0)));
                    AdventureProgressBar.setProgress(newadVenture);
                    AdventureProgressBar.setMax(newadVentureMax);

                    Setinit();
                } else {
                    Setinit();
                    Toast.makeText(getApplicationContext(), "아직 달성하지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
                AdventureProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_ADVENTURE", 0)));
                AdventureMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_ADVENTUREMAX", 0)));
                AdventureDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_ADVENTUREDIA", 0)));
                AdventureCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_ADVENTURECOIN", 0)));
                AdventureProgressBar.setProgress(pref_achieve.getInt("ACHIEVE_ADVENTURE", 0));
                AdventureProgressBar.setMax(pref_achieve.getInt("ACHIEVE_ADVENTUREMAX", 0));
            }
        });
        //ADVENTURE
        Setinit();
    }
    public void DIE_ACHIEVE_BTN () { //DIE
        DieGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언

                SharedPreferences pref_achieve = getSharedPreferences("ACHIEVE", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor_achieve = pref_achieve.edit();//쉐어드 사용할 'editor'선언

                int userDia = pref.getInt("UserDia", ClassUserData.getInstance().getDiamond());
                int userCoin = pref.getInt("UserGold", ClassUserData.getInstance().getGold());

                int dIe = pref_achieve.getInt("ACHIEVE_DIE", 0);
                int dIeMax = pref_achieve.getInt("ACHIEVE_DIEMAX", 20);
                int dIeDia = pref_achieve.getInt("ACHIEVE_DIEDIA", 5);
                int dIeCoin = pref_achieve.getInt("ACHIEVE_DIECOIN", 10);

                if (dIe >= dIeMax) {
                    int newUserDia = userDia + dIeDia;
                    int newUserCoin = userCoin + dIeCoin;
                    editor.putInt("UserDia", newUserDia);
                    editor.putInt("UserGold", newUserCoin);
                    editor.commit();

                    int newdIe = dIe - dIeMax;
                    int newdIeMax = dIeMax * 2;
                    int newdIeDia = dIeDia * 2;
                    int newdIeCoin = dIeCoin * 2;

                    editor_achieve.putInt("ACHIEVE_DIE", newdIe);
                    editor_achieve.putInt("ACHIEVE_DIEMAX", newdIeMax);
                    editor_achieve.putInt("ACHIEVE_DIEDIA", newdIeDia);
                    editor_achieve.putInt("ACHIEVE_DIECOIN", newdIeCoin);
                    editor_achieve.commit();

                    DieProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_DIE", 0)));
                    DieMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_DIEMAX", 0)));
                    DieDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_DIEDIA", 0)));
                    DieCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_DIECOIN", 0)));
                    DieProgressBar.setProgress(newdIe);
                    DieProgressBar.setMax(newdIeMax);

                    Setinit();
                } else {
                    Toast.makeText(getApplicationContext(), "아직 달성하지 못했습니다.", Toast.LENGTH_SHORT).show();
                    Setinit();
                }
                DieProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_DIE", 0)));
                DieMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_DIEMAX", 0)));
                DieDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_DIEDIA", 0)));
                DieCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_DIECOIN", 0)));
                DieProgressBar.setProgress(pref_achieve.getInt("ACHIEVE_DIE", 0));
                DieProgressBar.setMax(pref_achieve.getInt("ACHIEVE_DIEMAX", 0));
            }
        });
        //DIE
        Setinit();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Setinit();
        Setinit();
        BOSS_ACHIEVE_BTN ();
        MIDBOSS_ACHIEVE_BTN ();
        NOMAL_ACHIEVE_BTN ();
        ADVENTURE_ACHIEVE_BTN ();
        DIE_ACHIEVE_BTN ();
    }

    public void FindID() {
        BossGET = findViewById(R.id.achieve_getboss);
        BossProgress = findViewById(R.id.achieve_progressboss);
        BossMaxProgress = findViewById(R.id.achieve_progressbossmax);
        BossDia = findViewById(R.id.achieve_diaboss);
        BossCoin = findViewById(R.id.achieve_goldboss);
        BossProgressBar = findViewById(R.id.achieve_progressbar_boss);

        MidBossGET = findViewById(R.id.achieve_getmidboss);
        MidBossProgress = findViewById(R.id.achieve_progressmidboss);
        MidBossMaxProgress = findViewById(R.id.achieve_progressmidbossmax);
        MidBossDia = findViewById(R.id.achieve_diamidboss);
        MidBossCoin = findViewById(R.id.achieve_goldmidboss);
        MidBossProgressBar = findViewById(R.id.achieve_progressbar_midboss);

        NomalGET = findViewById(R.id.achieve_getnomal);
        NomalProgress = findViewById(R.id.achieve_progressnomal);
        NomalMaxProgress = findViewById(R.id.achieve_progressnomalmax);
        NomalDia = findViewById(R.id.achieve_dianomal);
        NomalCoin = findViewById(R.id.achieve_goldnomal);
        NomalProgressBar = findViewById(R.id.achieve_progressbar_nomal);

        AdventureGET = findViewById(R.id.achieve_getadventure);
        AdventureProgress = findViewById(R.id.achieve_progressadventure);
        AdventureMaxProgress = findViewById(R.id.achieve_progressadventuremax);
        AdventureDia = findViewById(R.id.achieve_diaadventure);
        AdventureCoin = findViewById(R.id.achieve_goldadventure);
        AdventureProgressBar = findViewById(R.id.achieve_progressbar_adventure);

        DieGET = findViewById(R.id.achieve_getdie);
        DieProgress = findViewById(R.id.achieve_progressdie);
        DieMaxProgress = findViewById(R.id.achieve_progressdiemax);
        DieDia = findViewById(R.id.achieve_diadie);
        DieCoin = findViewById(R.id.achieve_golddie);
        DieProgressBar = findViewById(R.id.achieve_progressbar_die);
        CLOSE = findViewById(R.id.popup_achieve_close2);


    }

    @SuppressLint("SetTextI18n")
    public void Setinit() {
        SharedPreferences pref_achieve = getSharedPreferences("ACHIEVE", MODE_PRIVATE);//쉐어드 선언 Key : User

        BossProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_BOSSKILL", 0)));
        BossMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_BOSSKILLMAX", 0)));
        BossDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_BOSSKILLDIA", 0)));
        BossCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_BOSSKILLCOIN", 0)));
        BossProgressBar.setProgress(pref_achieve.getInt("ACHIEVE_BOSSKILL", 0));
        BossProgressBar.setMax(pref_achieve.getInt("ACHIEVE_BOSSKILLMAX", 0));


        MidBossProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_MIDBOSSKILL", 0)));
        MidBossMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_MIDBOSSKILLMAX", 0)));
        MidBossDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_MIDBOSSKILLDIA", 0)));
        MidBossCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_MIDBOSSKILLCOIN", 0)));
        MidBossProgressBar.setProgress(pref_achieve.getInt("ACHIEVE_MIDBOSSKILL", 0));
        MidBossProgressBar.setMax(pref_achieve.getInt("ACHIEVE_MIDBOSSKILLMAX", 0));

        NomalProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_NOMALKILL", 0)));
        NomalMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_NOMALKILLMAX", 0)));
        NomalDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_NOMALKILLDIA", 0)));
        NomalCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_NOMALKILLCOIN", 0)));
        NomalProgressBar.setProgress(pref_achieve.getInt("ACHIEVE_NOMALKILL", 0));
        NomalProgressBar.setMax(pref_achieve.getInt("ACHIEVE_NOMALKILLMAX", 0));

        AdventureProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_ADVENTURE", 0)));
        AdventureMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_ADVENTUREMAX", 0)));
        AdventureDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_ADVENTUREDIA", 0)));
        AdventureCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_ADVENTURECOIN", 0)));
        AdventureProgressBar.setProgress(pref_achieve.getInt("ACHIEVE_ADVENTURE", 0));
        AdventureProgressBar.setMax(pref_achieve.getInt("ACHIEVE_ADVENTUREMAX", 0));

        DieProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_DIE", 0)));
        DieMaxProgress.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_DIEMAX", 0)));
        DieDia.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_DIEDIA", 0)));
        DieCoin.setText(String.valueOf(pref_achieve.getInt("ACHIEVE_DIECOIN", 0)));
        DieProgressBar.setProgress(pref_achieve.getInt("ACHIEVE_DIE", 0));
        DieProgressBar.setMax(pref_achieve.getInt("ACHIEVE_DIEMAX", 0));
    }

}