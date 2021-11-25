package com.shootinggame.gameshooting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class Activity_9PopUp_Quest extends AppCompatActivity {
    private ImageButton ib_Close_Quest;
    private String tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_9popup_quest);
        hideNaviUI();
        ib_Close_Quest = findViewById(R.id.popup_quest_Close);
        ib_Close_Quest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
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
        tag="퀘스트PopUP";
        Log.e(tag, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNaviUI();
        tag="퀘스트PopUP";
        Log.e(tag, "onResume");
    }
}
