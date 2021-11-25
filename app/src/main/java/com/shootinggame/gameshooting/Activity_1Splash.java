package com.shootinggame.gameshooting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Activity_1Splash extends AppCompatActivity {
    Animation fade_in, fade_out;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1splash);
        hideNaviUI();


























        imageView = findViewById(R.id.Slash_Background);
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fade_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(getApplicationContext(), Activity_2Join.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(fade_in);
    }

    public void hideNaviUI(){
        int newUiOptions = 0;
        if(Build.VERSION.SDK_INT>=14){
            newUiOptions^=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        if(Build.VERSION.SDK_INT>=18){
            newUiOptions^=View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public void UserDataInfo(){
        SharedPreferences pref = getSharedPreferences("User", MODE_PRIVATE);//쉐어드 선언 Key : User
        SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
        editor.putString("UserName", "연습생");
    }
}
