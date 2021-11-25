package com.shootinggame.gameshooting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class Activity_9PopUp_Exitgame extends AppCompatActivity {
    private ImageButton ib_Yes_Exitgame,ib_No_Exitgame,ib_Close_Exitgame;
    private String tag;
    Activity_2_5FirstSelect Select = (Activity_2_5FirstSelect) Activity_2_5FirstSelect.activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9popup_exitgame);


        hideNaviUI();
        ib_Yes_Exitgame =findViewById(R.id.popup_exitgame_Yes);
        ib_No_Exitgame =findViewById(R.id.popup_exitgame_No);
        ib_Close_Exitgame =findViewById(R.id.popup_exitgame_Close);

        ib_Yes_Exitgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Select.finish();
                Activity_3Main.activity.finishAndRemoveTask();

            System.exit(0);
            }
        });
        ib_No_Exitgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ib_Close_Exitgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        tag="종료PopUP";
        Log.e(tag, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNaviUI();
        tag="종료PopUP";
        Log.e(tag, "onResume");
    }
}
