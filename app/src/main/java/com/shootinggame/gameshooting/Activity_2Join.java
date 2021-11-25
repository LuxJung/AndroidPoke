package com.shootinggame.gameshooting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_2Join extends AppCompatActivity {
    private ImageButton guest_btn, google_btn; //이미지 버튼 변수선언
    public Gson gson = new Gson();
    public static Activity activity;

    //public ArrayList<ClassAchieveData> achieve_ingame = new ArrayList<ClassAchieveData>();
    public static ArrayList<ClassAchieveData> achieve_list = new ArrayList<ClassAchieveData>();
    public static ArrayList<ClassAchieveData> getAchieve_list() {
        return achieve_list;
    }
    public static ArrayList<ClassAchieveData> achieve_this = new ArrayList<ClassAchieveData>();
    public static ArrayList<ClassAchieveData> getAchieve_thislist() {
        return achieve_this;
    }
    public static void setAchieve_this(ArrayList<ClassAchieveData> achieve_this) {
        Activity_2Join.achieve_this = achieve_this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2join);
        hideNaviUI();
        //이비지 버튼 변수에 대한 위젯 아이디 찾아 연결
        guest_btn = findViewById(R.id.guest_btn);
        //구글버튼은 추후 play game api 사용하게 되면 바뀌어야 함
        google_btn = findViewById(R.id.google_btn);
        final SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User

        achieve_list.add(new ClassAchieveData("보스처치", 0, 20, 50, 100));
        achieve_list.add(new ClassAchieveData("중보처치", 0, 10, 30, 60));
        achieve_list.add(new ClassAchieveData("일반처치", 0, 50, 10, 20));
        achieve_list.add(new ClassAchieveData("모험횟수", 0, 20, 10, 20));
        achieve_list.add(new ClassAchieveData("사망횟수", 0, 20, 5, 10));


        String ToJson = gson.toJson(getAchieve_list());
        SharedPreferences pref_achieve = getSharedPreferences("Game", MODE_PRIVATE);//쉐어드 선언 Key : User
        SharedPreferences.Editor editor_achieve = pref_achieve.edit();//쉐어드 사용할 'editor'선언

        Type type = new TypeToken<ArrayList<ClassAchieveData>>() {
        }.getType();
        ArrayList<ClassAchieveData> achieve = gson.fromJson(pref_achieve.getString("Game_Achieve", ""), type);

        if (achieve == null) {
           Log.e("achieve_list size: ", String.valueOf(achieve_this.size()));
            //gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
            Log.e("ToJson", ToJson);
            editor_achieve.putString("Game_Achieve", ToJson); //쉐어드의 editor 를 통해 JSON 스트링 저장 Key : User_PoketMon
            editor_achieve.apply();//쉐어드 에디터 닫아주는데 apply 비동기처리 설명은 맨아래 주석
            Log.e("Shared저장값", pref_achieve.getString("Game_Achieve", ""));
            Log.e("ToJson", ToJson);

        }else{
            if (achieve_this.size() == 0) {
                for (int i = 0; i < achieve.size(); i++) {
                    achieve_this.add(achieve.get(i));
                }
            }
        }












        //버튼에 대한 행동수행
        guest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Type type = new TypeToken<ArrayList<ClassPoketMon>>() {
                }.getType();
                ArrayList<ClassPoketMon> parse = gson.fromJson(pref.getString("User_Poketmon", ""), type);

                if (parse == null) {
                    Intent intent = new Intent(getApplicationContext(), Activity_2_5FirstSelect.class);
                    startActivity(intent);//메인화면으로 이동
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Activity_3Main.class);
                    startActivity(intent);//메인화면으로 이동
                    finish();
                }
            }
        });

        //구글버튼은 추후 play game api 사용하게 되면 바뀌어야 함
        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //구글 버튼 클릭시 할 행동 수행
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();


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
