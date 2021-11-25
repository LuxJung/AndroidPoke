package com.shootinggame.gameshooting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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

import java.util.ArrayList;

public class Activity_6Store extends AppCompatActivity {
    private String tag;
    private ImageButton ib_ShopProfile, ib_ShopMail, ib_ShopSetting, ib_ShopBack,
            ib_ShopTeam, ib_ShopBook, ib_ShopStart, ib_ShopBuy;
    private ImageView iv_ShopIMG , CoinImg;
    private TextView tv_ShopCostDia, tv_ShopCostCoin, tv_ShopName, tv_ShopLevel, tv_ShopDia,
            tv_ShopGold, tv_ShopItemName,  tv_ShopUserCountMax,tv_ShopUserexp,tv_ShopUserexpMax;
    public static Activity activity;
    private RecyclerView recyclerView;
    private static TextView tv_ShopUserCount,tv_ShopTimeCount;
    public static String Time;
    private ProgressBar expVar;
    private RecyclerVIewAdapter_Shop adapter;
    private GridLayoutManager layoutManager;
    public static ArrayList<ClassStore> slist = new ArrayList<ClassStore>() {{
        add(new ClassStore("이상해씨", R.drawable.esangheassi, 100, 1000));
        add(new ClassStore("파이리", R.drawable.pairi, 100, 1000));
        add(new ClassStore("꼬부기", R.drawable.ggobugi, 100, 1000));
        add(new ClassStore("피츄", R.drawable.pichu, 50, 500));
        add(new ClassStore("피카츄", R.drawable.picachu, 100, 1000));
        add(new ClassStore("5000코인", R.drawable.manicoin, 500, 0));
        //add(new ClassStore("100다이아", R.drawable.diamondmany,  0,1100));

    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6store);
        layoutFindID_Store();
        hideNaviUI();
        activityMove();
        //setShopText();
        viewStatus();
        expVar = findViewById(R.id.pb_shop_ExpBar);
        /*expVar.setProgress(ClassUserData.getInstance().getExp());
        expVar.setMax(ClassUserData.getInstance().getExpMax());*/
        activity = Activity_6Store.this;
        recyclerView = (RecyclerView) findViewById(R.id.store_grid_recyclerview);
        adapter = new RecyclerVIewAdapter_Shop(getApplicationContext(), slist);
        layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //초기 상점 셋팅
        iv_ShopIMG.setImageResource(slist.get(0).image);
        tv_ShopCostDia.setText(String.valueOf(slist.get(0).dia));
        tv_ShopItemName.setText(slist.get(0).name);
        tv_ShopCostCoin.setText(String.valueOf(slist.get(0).coin));


        ib_ShopBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9Popup_Buy.class);
                String name = tv_ShopItemName.getText().toString();
                int dia = Integer.parseInt(tv_ShopCostDia.getText().toString());
                int coin = Integer.parseInt(tv_ShopCostCoin.getText().toString());
                intent.putExtra("name", name);
                intent.putExtra("dia", dia);
                intent.putExtra("coin", coin);
                startActivity(intent);
            }
        });
    }

    public static void set6ShopTimer() {
        Time = get6ShopTimer();
        tv_ShopTimeCount.setText(Time);
    }

    public static void set6ShopTimerText(String a) {
        tv_ShopTimeCount.setText(a);

    }

    public static String get6ShopTimer() {
        return tv_ShopTimeCount.getText().toString();
    }

    public static void StoreViewGone() {
        tv_ShopTimeCount.setVisibility(View.GONE);
    }

    public static void StoreViewVisible() {
        tv_ShopTimeCount.setVisibility(View.VISIBLE);
    }

    public static void set6ShopUserCount(String userCount){
        tv_ShopUserCount.setText(userCount);
    }
    public void viewStatus(){
        if(ClassUserData.getInstance().getMaxCount()==ClassUserData.getInstance().getCount()){
            tv_ShopTimeCount.setVisibility(View.GONE);
        }else if(ClassUserData.getInstance().getMaxCount()>ClassUserData.getInstance().getCount()){
            tv_ShopTimeCount.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setShopText() {
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        tv_ShopName.setText(pref.getString("UserName", ClassUserData.getInstance().getName()));
        tv_ShopLevel.setText(Integer.toString(pref.getInt("UserLevel", ClassUserData.getInstance().getLevel())));
        tv_ShopDia.setText(Integer.toString(pref.getInt("UserDia", ClassUserData.getInstance().getDiamond())));
        tv_ShopGold.setText(Integer.toString( pref.getInt("UserGold", ClassUserData.getInstance().getGold())));
        ib_ShopProfile.setImageResource(pref.getInt("UserProfile",ClassUserData.getInstance().getProfile()));
        tv_ShopUserCount.setText(Integer.toString(pref.getInt("UserCount",ClassUserData.getInstance().getCount())));
        tv_ShopUserCountMax.setText(Integer.toString( pref.getInt("UserMaxCount",ClassUserData.getInstance().getMaxCount())));
        tv_ShopTimeCount.setText(ClassUserData.getInstance().getTime());

        tv_ShopUserexp.setText(Integer.toString(pref.getInt("UserExp", ClassUserData.getInstance().getExp())));
        tv_ShopUserexpMax.setText(Integer.toString(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax())));


        expVar.setProgress(pref.getInt("UserExp", ClassUserData.getInstance().getExp()));
        expVar.setMax(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax()));
    }

    public void layoutFindID_Store() {
        tv_ShopUserexp = findViewById(R.id.tv_shop_NeedExp);
        tv_ShopUserexpMax = findViewById(R.id.tv_shop_RequestExp);
        tv_ShopName = findViewById(R.id.tv_shop_Name);
        tv_ShopLevel = findViewById(R.id.tv_shop_Level);
        tv_ShopDia = findViewById(R.id.tv_shop_Dia);
        tv_ShopGold = findViewById(R.id.tv_shop_Gold);
        tv_ShopItemName = findViewById(R.id.tv_shop_ItemName);

        tv_ShopUserCount = findViewById(R.id.tv_shop_Count);
        tv_ShopUserCountMax = findViewById(R.id.tv_shop_CountMax);
        tv_ShopTimeCount = findViewById(R.id.tv_shop_Timer);

        ib_ShopBuy = findViewById(R.id.ib_shop_Buy);
        ib_ShopBack = findViewById(R.id.ib_shop_Back);
        ib_ShopTeam = findViewById(R.id.ib_shop_Team);
        ib_ShopBook = findViewById(R.id.ib_shop_Book);
        ib_ShopStart = findViewById(R.id.ib_shop_Start);
        ib_ShopProfile = findViewById(R.id.ib_shop_Profile);
        ib_ShopMail = findViewById(R.id.ib_shop_Mail);
        ib_ShopSetting = findViewById(R.id.ib_shop_Setting);
        iv_ShopIMG = findViewById(R.id.select_item);
        tv_ShopCostDia = findViewById(R.id.recylcerview_row_shopitemdia);
        tv_ShopCostCoin = findViewById(R.id.recylcerview_row_shopitemcoin);

        CoinImg = findViewById(R.id.coinimg);
    }

    public void coinimgChange(int a){
        if(a==1){
            CoinImg.setImageResource(R.drawable.won);
        }else{
            CoinImg.setImageResource(R.drawable.coin);
        }
    }

    public void shopImg(Bitmap bitmap) {
        iv_ShopIMG.setImageBitmap(bitmap);
    }

    public void shopTextDia(String s) {
        tv_ShopCostDia.setText(s);
    }

    public void shopTextName(String s) {
        tv_ShopItemName.setText(s);
    }

    public void shopTextCoin(String s) {
        tv_ShopCostCoin.setText(s);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void activityMove() {
        ib_ShopProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Profile.class);
                startActivity(intent);
            }
        });
        ib_ShopMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Mail.class);
                startActivity(intent);
            }
        });
        ib_ShopSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Mail.class);
                startActivity(intent);
            }
        });
        ib_ShopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ib_ShopTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_4Team.class);
                startActivity(intent);
                finish();
            }
        });
        ib_ShopBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_5Book.class);
                startActivity(intent);
                finish();
            }
        });
        ib_ShopStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_7GameStart_Linear_Table.class);
                startActivity(intent);
                finish();
            }
        });
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
        tag = "상점Activity";
        Log.e(tag, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNaviUI();
        setShopText();
        viewStatus();
        tag = "상점Activity";
        Log.e(tag, "onResume");
    }
}
