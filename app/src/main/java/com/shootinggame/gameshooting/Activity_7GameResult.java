package com.shootinggame.gameshooting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_7GameResult extends AppCompatActivity {
    private TextView kill, point,kill_result, point_result, coin,coin_result;
    private Button out;
    Intent Result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7game_result);
        Result=getIntent();
        final int KILL = Result.getIntExtra("killed",0);
        hideNaviUI();
        kill = findViewById(R.id.kill);
        point = findViewById(R.id.point);
        coin = findViewById(R.id.coin);
        out = findViewById(R.id.ok);

        kill.setText(String.valueOf(KILL+" 처치함"));
        point.setText(KILL*30+" 점");
        coin.setText(KILL*30*10+" 코인 획득");

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
                final SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언


                int userCoin = pref.getInt("UserGold", ClassUserData.getInstance().getGold());
                int getCoin = KILL*30*10;
                int afterCoin = userCoin+getCoin;


                int userExp = pref.getInt("UserExp", ClassUserData.getInstance().getExp());
                int userExpMax = pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax());
                int userLevel = pref.getInt("UserLevel",ClassUserData.getInstance().getLevel());

                int userExpresult = userExp+KILL;
                editor.putInt("UserGold",afterCoin);ClassUserData.getInstance().setExp(afterCoin);
                editor.putInt("UserExp",userExpresult);ClassUserData.getInstance().setExp(userExpresult);
                editor.commit();



                int newuserLevel = userLevel=+1;
                int newuserExp = userExp-userExpMax;
                int newuserExpMax= userExpMax *2;

                if(userExp>userExpMax){
                    editor.putInt("UserLevel",newuserLevel); ClassUserData.getInstance().setLevel(newuserLevel);
                    editor.putInt("UserExp",newuserExp);ClassUserData.getInstance().setExp(newuserExp);
                    editor.putInt("UserMaxExp",newuserExpMax);ClassUserData.getInstance().setExpMax(newuserExpMax);
                    editor.commit();
                }



                Intent intent = new Intent(getApplicationContext(), Activity_3Main.class);
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

}