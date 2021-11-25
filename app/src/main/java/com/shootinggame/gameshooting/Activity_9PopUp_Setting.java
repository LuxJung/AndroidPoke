package com.shootinggame.gameshooting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Switch;

public class Activity_9PopUp_Setting extends AppCompatActivity {
    private ImageButton ib_Close_Setting;
    private Switch switch_BGM, switch_Motion, switch_Vibrate;
    private String tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_9popup_setting);
        hideNaviUI();
        popFindID_Setting();


        ib_Close_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //아이디 찾아주기
    public void popFindID_Setting() {
        ib_Close_Setting = findViewById(R.id.popup_setting_Close);
        switch_BGM = findViewById(R.id.popup_setting_BGMSound);
        switch_Motion = findViewById(R.id.popup_setting_MotionSound);
        switch_Vibrate = findViewById(R.id.popup_setting_Vibrate);
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
    @Override
    protected void onPause() {
        super.onPause();
        hideNaviUI();
        tag="셋팅PopUP";
        Log.e(tag, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNaviUI();
        tag="셋팅PopUP";
        Log.e(tag, "onResume");
    }

}
