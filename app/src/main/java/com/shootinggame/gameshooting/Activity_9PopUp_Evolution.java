package com.shootinggame.gameshooting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_9PopUp_Evolution extends AppCompatActivity {
    private String tag;
    private ImageView image;
    private ImageButton yes, no;
    public Gson gson = new Gson();
    private Activity_3Main Main = (Activity_3Main) Activity_3Main.activity;
    Boolean UNIBUGI_PROFILE = true;
    Boolean GUBUCKWANG_PROFILE = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9popup_evolution);
        FindID();
        hideNaviUI();
        final SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        final SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언

        final Type type = new TypeToken<ArrayList<ClassPoketMon>>() {
        }.getType();

        final ArrayList<ClassPoketMon> parse = gson.fromJson(pref.getString("User_Poketmon", ""), type);

        image.setImageResource(pref.getInt("PokeIMG", 0));
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  for (int i = 0; i < Main.UserPoketmon.size(); i++) {
                    //for (int j = 0; j < parse.size(); j++) {
//                        Log.e("진화 yes 클릭시작", "parse" + parse.get(pref.getInt("POS", 0)).Name + "있음" + "\n" + "UserPoketmon" + Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName() + "있음");
                    Log.e("pref.getInt(\"POS\", 0)",String.valueOf(pref.getInt("POS", 0)));
                        if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName().equals("꼬부기") && parse.get(pref.getInt("POS", 0)).Name.equals("꼬부기")) {
                            Main.UserPoketmon.remove(pref.getInt("POS", 0));
                            parse.remove(pref.getInt("POS", 0));
                            Main.UserPoketmon.add(pref.getInt("POS", 0), new ClassPoketMon("어니부기", R.drawable.ggounibugi, R.drawable.ggounibugiprofile, 8, 200, 1000, 5, 5, 10, 5, 10, "꼬부기가 진화한 형태입니다.\n거북왕으로 진화 가능합니다.", 5, 5, 10, 10, 10));
                            String ToJson = gson.toJson(Main.getUserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                            editor.putString("User_Poketmon", ToJson);
                            editor.putString("PokeName", "어니부기");
                            editor.putInt("PokeIMG", R.drawable.ggounibugi);
                            editor.commit();
                            for (int k = 0; k < Main.ONLY_PROFILE_UserPoketmon.size(); k++) {
                                if (Main.ONLY_PROFILE_UserPoketmon.get(k).Name.equals("어니부기")) {
                                    UNIBUGI_PROFILE = false;
                                }
                                if (Main.ONLY_PROFILE_UserPoketmon.get(k).Name.equals("거북왕")) {
                                    GUBUCKWANG_PROFILE = false;
                                }
                            }
                            if (UNIBUGI_PROFILE) {
                                Main.ONLY_PROFILE_UserPoketmon.add(new ClassPoketMon("어니부기", R.drawable.ggounibugi, R.drawable.ggounibugiprofile, 8, 200, 1000, 5, 5, 10, 5, 10, "꼬부기가 진화한 형태입니다.\n거북왕으로 진화 가능합니다.", 5, 5, 10, 10, 10));
                                String ToJson2 = gson.toJson(Main.getONLY_PROFILE_UserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                                editor.putString("User_Poketmon_PROFILE", ToJson2);
                                editor.commit();
                            }
                        }
                        else if (Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName().equals("어니부기") && parse.get(pref.getInt("POS", 0)).Name.equals("어니부기")) {
                            Log.e("진화 yes 클릭시작", "parse" + parse.get(pref.getInt("POS", 0)).Name + "있음" + "\n" + "UserPoketmon" + Main.UserPoketmon.get(pref.getInt("POS", 0)).getPoketMonName() + "있음");
                            Main.UserPoketmon.remove(pref.getInt("POS", 0));
                            parse.remove(pref.getInt("POS", 0));
                            Main.UserPoketmon.add(pref.getInt("POS", 0),new ClassPoketMon("거북왕", R.drawable.ggogubuckwang, R.drawable.ggogubuckwangprofile, 9, 500, 5000, 10, 10, 30, 10, 30, "꼬부기의 최종진화 형태입니다.\n 어니부기에서 진화하여 획득 가능합니다.", 10, 10, 30, 30, 30));
                            String ToJson = gson.toJson(Main.getUserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                            editor.putString("User_Poketmon", ToJson);
                            editor.putString("PokeName", "거북왕");
                            editor.putInt("PokeIMG", R.drawable.ggogubuckwang);//이미지 잘 넣어주기
                            editor.commit();

                           for (int k = 0; k < Main.ONLY_PROFILE_UserPoketmon.size(); k++) {
                                if (Main.ONLY_PROFILE_UserPoketmon.get(k).Name.equals("거북왕")) {
                                    GUBUCKWANG_PROFILE = false;
                                }
                            }
                            if (GUBUCKWANG_PROFILE) {
                                Main.ONLY_PROFILE_UserPoketmon.add(new ClassPoketMon("거북왕", R.drawable.ggogubuckwang, R.drawable.ggogubuckwangprofile, 9, 500, 5000, 10, 10, 30, 10, 30, "꼬부기의 최종진화 형태입니다.\n 어니부기에서 진화하여 획득 가능합니다.", 10, 10, 30, 30, 30));
                                String ToJson2 = gson.toJson(Main.getONLY_PROFILE_UserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                                editor.putString("User_Poketmon_PROFILE", ToJson2);
                                editor.commit();
                            }
                        }
                    //}
                //}
                Log.e("Shared저장값", "\n" + pref.getString("User_Poketmon", "") + "\n");
                Log.e("parse삭제 후", "크기" + String.valueOf(parse.size()));
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

    public void FindID() {
        image = findViewById(R.id.evolution);
        yes = findViewById(R.id.evolution_Yes);
        no = findViewById(R.id.evolution_No);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideNaviUI();
        tag = "진화PopUP";
        Log.e(tag, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNaviUI();
        tag = "진화PopUP";
        Log.e(tag, "onResume");
    }
}