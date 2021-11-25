package com.shootinggame.gameshooting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_3Main extends AppCompatActivity {
    private TextView tv_UserName, tv_UserLevel, tv_UserMaxCount, tv_UserDiamond, tv_UserGold, tv_UserNeedExp, tv_UserRequestExp;
    private static ImageView iv_MainPoke;
    private ImageButton ib_UserProfile, ib_UserCount, ib_UserDiamond, ib_UserGold, ib_UserMail, ib_UserSetting;
    private ImageButton ib_MainTeam, ib_MainBook, ib_MainShop, ib_MainQuest, ib_MainAchieve, ib_MainStart, ib_MainExitGameYes, ib_MainExitGameNo, ib_MainExitGameClose;
    private ProgressBar pb_MainExpBar;
    private String tag;
    private static TextView tv_TimeCount, tv_UserCount;
    public static String Time;
    public static Activity activity;
    private int maxCount = 5;

    public Gson gson = new Gson();
    static ArrayList<ClassPoketMon> list = new ArrayList<ClassPoketMon>();


    ArrayList<ClassPoketMon> UserPoketmon = new ArrayList<ClassPoketMon>();
    ArrayList<ClassPoketMon> ONLY_PROFILE_UserPoketmon = new ArrayList<ClassPoketMon>();
    ArrayList<ClassPoketMon> getUserPoketmon() {
        return UserPoketmon;
    }
    ArrayList<ClassPoketMon> getONLY_PROFILE_UserPoketmon() {
        return ONLY_PROFILE_UserPoketmon;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3main);
        activity = Activity_3Main.this;
        layoutFindID_Main();// 각 위젯아디찾기
        //onEnterAnimationComplete();
        hideNaviUI();// 기기 네비숨기기
        activity_Move(); // 액티비티 이동

        //ClassUserData.getInstance(); // 유저의 인스턴스
        //setMainText(); //유저 인스턴스 가지고 와서 셋팅
        viewStatus();
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        iv_MainPoke.setImageResource(pref.getInt("PokeIMG", R.drawable.whoareyou));
        Log.e("MAIN / Shared저장값 확인", "\n"+pref.getString("User_Poketmon", "")+"\n");

        SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언




        pref.getString("UserName", ClassUserData.getInstance().getName());
        pref.getInt("UserLevel", ClassUserData.getInstance().getLevel());
        pref.getInt("UserExp", ClassUserData.getInstance().getExp());
        pref.getInt("UserMaxCount", ClassUserData.getInstance().getMaxCount());
        pref.getInt("UserCount", ClassUserData.getInstance().getCount());
        pref.getInt("UserGold", ClassUserData.getInstance().getGold());
        pref.getInt("UserDia", ClassUserData.getInstance().getDiamond());
        pref.getInt("UserProfile", ClassUserData.getInstance().getProfile());

/*
        int exp = Integer.parseInt(tv_UserNeedExp.getText().toString());//앞에꺼
        int expMax = Integer.parseInt(tv_UserRequestExp.getText().toString());//뒤에꺼
        pb_MainExpBar.setProgress(exp);
        pb_MainExpBar.setMax(expMax);*/


        //SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        //static ArrayList<ClassPoketMon> parse = new ArrayList<ClassPoketMon>();
        Type type = new TypeToken<ArrayList<ClassPoketMon>>() {
        }.getType();
        ArrayList<ClassPoketMon> parse = gson.fromJson(pref.getString("User_Poketmon", ""), type);

        if (parse == null) {
            list.add(new ClassPoketMon("피츄",R.drawable.pichu, R.drawable.pichuprofile,10, 100, 500,1,1,5,1,10,"피츄 입니다.",1,1,5,5,5));
            Log.e("list main size: ", String.valueOf(list.size()));
            String ToJson = gson.toJson(list);//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
            editor.putString("User_Poketmon", ToJson); //쉐어드의 editor 를 통해 JSON 스트링 저장 Key : User_PoketMon
            editor.apply();//쉐어드 에디터 닫아주는데 apply 비동기처리 설명은 맨아래 주석
            Log.e("MAIN /Shared저장값", pref.getString("User_Poketmon", ""));
            Log.e("MAIN /ToJson", ToJson);

        } else {
            Log.e("MAIN /parse(gson.fromJson) content", pref.getString("User_Poketmon", ""));
            Log.e("MAIN /parse(gson.fromJson) size", String.valueOf(parse.size()));
            if (UserPoketmon.size() == 0) {
                for (int i = 0; i < parse.size(); i++) {
                    //쉐어드에 저장된 친구들을 하나씩 넣어주는 역할
                    UserPoketmon.add(parse.get(i));
                    ONLY_PROFILE_UserPoketmon.add(parse.get(i));
                    Log.e("UserPoketmon", parse.get(i).getPoketMonName() + " 들어감");
                    Log.e("UserPoketmon", UserPoketmon.get(i).getPoketMonName() + " 들어옴");
                    Log.e("UserPoketmon", "UserPoketmon " + UserPoketmon.size() + " 마리 존재");
                }
            }
        }


    }//OnCreate


    public static void setMainPoke(int img) {
        iv_MainPoke.setImageResource(img);
    }

    public void addUserList(ClassPoketMon classPoketMon) {
        list.add(classPoketMon);
    }

    public static void set3MainTimer() {
        Time = get3MainTimer();
        tv_TimeCount.setText(Time);
    }

    public static void set3MainTimerText(String a) {
        tv_TimeCount.setText(a);
    }

    public static String get3MainTimer() {
        return tv_TimeCount.getText().toString();
    }

    public static void set3MainUserCount(String userCount) {
        tv_UserCount.setText(userCount);
    }

    public static void MainViewGone() {
        tv_TimeCount.setVisibility(View.GONE);
    }

    public static void MainViewVisible() {
        tv_TimeCount.setVisibility(View.VISIBLE);
    }

    public void viewStatus() {
        if (ClassUserData.getInstance().getMaxCount() > ClassUserData.getInstance().getCount()) {
            tv_TimeCount.setVisibility(View.VISIBLE);
        } else if (ClassUserData.getInstance().getMaxCount() == ClassUserData.getInstance().getCount()) {
            tv_TimeCount.setVisibility(View.GONE);
        }
    }

    public void activity_Move() {
        ib_UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Profile.class);
                startActivityForResult(intent, 1);
            }
        });

        ib_UserMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Mail.class);
                startActivity(intent);
            }
        });
        ib_UserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Setting.class);
                startActivity(intent);
            }
        });
        ib_MainQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Quest.class);
                startActivity(intent);
            }
        });
        ib_MainAchieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Achieve2.class);
                startActivity(intent);
            }
        });

        ib_MainTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_4Team.class);
                startActivity(intent);
            }
        });
        ib_MainBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_5Book.class);
                startActivity(intent);
            }
        });
        ib_MainShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_6Store.class);
                startActivity(intent);
            }
        });
        ib_MainStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_7GameStart_Linear_Table.class);
                startActivity(intent);
            }
        });
    }

    //네비 감추기
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

    //아이디 찾아주기
    public void layoutFindID_Main() {
        // 위젯 레이아웃과 연결
        tv_UserMaxCount = findViewById(R.id.tv_main_CountMax);
        tv_UserName = findViewById(R.id.tv_main_Name);        // 유저 이름
        tv_UserLevel = findViewById(R.id.tv_main_Level);      // 유저 레벨
        tv_UserCount = findViewById(R.id.tv_main_Count);      // 유저 행동 가능횟수
        tv_TimeCount = findViewById(R.id.tv_main_Timer);      // 유저 행동 충전시간
        tv_UserDiamond = findViewById(R.id.tv_main_Diamond);  // 유저 다이아
        tv_UserGold = findViewById(R.id.tv_main_Gold);        // 유저 골드
        tv_UserNeedExp = findViewById(R.id.tv_main_NeedExp);
        tv_UserRequestExp = findViewById(R.id.tv_main_RequestExp);


        iv_MainPoke = findViewById(R.id.iv_main_Image);

        ib_UserProfile = findViewById(R.id.ib_main_Profile);  // 유저 프로필 이미지 버튼
        ib_UserCount = findViewById(R.id.ib_main_Count);      // 유저 횟수 이미지 버튼
        ib_UserDiamond = findViewById(R.id.ib_main_Diamond);  // 유저 다이아 이미지 버튼
        ib_UserGold = findViewById(R.id.ib_main_Gold);        // 유저 골드 이미지 버튼
        ib_UserMail = findViewById(R.id.ib_main_Mail);        // 유저 메일 이미지 버튼
        ib_UserSetting = findViewById(R.id.ib_main_Setting);  // 유저 셋팅 이미지 버튼

        ib_MainTeam = findViewById(R.id.ib_main_Team);      // 유저 프로필 이미지 버튼
        ib_MainBook = findViewById(R.id.ib_main_Book);      // 유저 횟수 이미지 버튼
        ib_MainShop = findViewById(R.id.ib_main_Shop);      // 유저 다이아 이미지 버튼
        ib_MainStart = findViewById(R.id.ib_main_Start);    // 유저 골드 이미지 버튼
        ib_MainQuest = findViewById(R.id.ib_main_Quest);  // 유저 메일 이미지 버튼
        ib_MainAchieve = findViewById(R.id.ib_main_Achieve);  // 유저 셋팅 이미지 버튼

        pb_MainExpBar = findViewById(R.id.pb_main_ExpBar);  // 유저 프로그래스바
        ib_MainExitGameYes = findViewById(R.id.popup_exitgame_Yes);
        ib_MainExitGameNo = findViewById(R.id.popup_exitgame_No);
        ib_MainExitGameClose = findViewById(R.id.popup_exitgame_Close);

    }

    //종료 팝업띄우기
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Activity_9PopUp_Exitgame.class);
        startActivity(intent);
    }


    @SuppressLint("SetTextI18n")
    public void setMainText() {
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User

        tv_UserName.setText(pref.getString("UserName", ClassUserData.getInstance().getName()));
        tv_UserLevel.setText(Integer.toString(pref.getInt("UserLevel", ClassUserData.getInstance().getLevel())));
        tv_UserDiamond.setText(Integer.toString(pref.getInt("UserDia", ClassUserData.getInstance().getDiamond())));
        tv_UserGold.setText(Integer.toString(pref.getInt("UserGold", ClassUserData.getInstance().getGold())));
        ib_UserProfile.setImageResource(pref.getInt("UserProfile", ClassUserData.getInstance().getProfile()));
        tv_UserMaxCount.setText(Integer.toString(pref.getInt("UserMaxCount", ClassUserData.getInstance().getMaxCount())));
        tv_UserCount.setText(Integer.toString(pref.getInt("UserCount", ClassUserData.getInstance().getCount())));
        tv_TimeCount.setText(ClassUserData.getInstance().getTime());
        tv_UserNeedExp.setText(Integer.toString(pref.getInt("UserExp", ClassUserData.getInstance().getExp())));
        tv_UserRequestExp.setText(Integer.toString(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax())));

        pb_MainExpBar.setProgress(pref.getInt("UserExp", ClassUserData.getInstance().getExp()));
        pb_MainExpBar.setMax(pref.getInt("UserMaxExp", ClassUserData.getInstance().getExpMax()));
    }
    /*@Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        layoutFindID_Main();
    }*/



    @Override
    protected void onResume() {
        super.onResume();
        hideNaviUI();
        setMainText();
        viewStatus();
        /*pb_MainExpBar.setProgress(ClassUserData.getInstance().getExp());
        pb_MainExpBar.setMax(ClassUserData.getInstance().getExpMax());*/
        //onEnterAnimationComplete();
        tag = "메인Activity";
        Log.e(tag, "onResume");

        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
        iv_MainPoke.setImageResource(pref.getInt("PokeIMG", R.drawable.whoareyou));
        if(pref.getInt("PokeIMG", R.drawable.whoareyou)==R.drawable.ggogubuckwang||
                pref.getInt("PokeIMG", R.drawable.whoareyou)==R.drawable.esangheaggot||
                pref.getInt("PokeIMG", R.drawable.whoareyou)==R.drawable.parijamong||
                pref.getInt("PokeIMG", R.drawable.whoareyou)==R.drawable.raichu){
            iv_MainPoke.setPadding(0,200,0,0);
        }
        if(pref.getInt("PokeIMG", R.drawable.whoareyou)==R.drawable.ggounibugi||
                pref.getInt("PokeIMG", R.drawable.whoareyou)==R.drawable.esangheapul||
                pref.getInt("PokeIMG", R.drawable.whoareyou)==R.drawable.parijade||
                pref.getInt("PokeIMG", R.drawable.whoareyou)==R.drawable.picachu){
            iv_MainPoke.setPadding(0,300,0,0);
        }
        if(pref.getInt("PokeIMG", R.drawable.whoareyou)==R.drawable.ggobugi||
                pref.getInt("PokeIMG", R.drawable.whoareyou)==R.drawable.esangheassi||
                pref.getInt("PokeIMG", R.drawable.whoareyou)==R.drawable.pairi||
                pref.getInt("PokeIMG", R.drawable.whoareyou)==R.drawable.pichu){
            iv_MainPoke.setPadding(0,400,0,0);
        }

        Type type = new TypeToken<ArrayList<ClassPoketMon>>() {
        }.getType();
        ArrayList<ClassPoketMon> parse = gson.fromJson(pref.getString("User_Poketmon", ""), type);
       /* if (parse == null) {
            list.add(new ClassPoketMon("피츄", R.drawable.pichu, R.drawable.pichuprofile, 10, 100, 500, 1, 1, 1));
            Log.e("list main size: ", String.valueOf(list.size()));

            String ToJson = gson.toJson(list);//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환

            editor.putString("User_Poketmon", ToJson); //쉐어드의 editor 를 통해 JSON 스트링 저장 Key : User_PoketMon
            editor.apply();//쉐어드 에디터 닫아주는데 apply 비동기처리 설명은 맨아래 주석
            Log.e("Shared저장값", pref.getString("User_Poketmon", ""));
            Log.e("ToJson", ToJson);
        } else {
            Log.e("parse(gson.fromJson) content", pref.getString("User_Poketmon", ""));
            Log.e("parse(gson.fromJson) size", String.valueOf(parse.size()));*/
        if (UserPoketmon.size() == 0) {
            for (int i = 0; i < parse.size(); i++) {
                UserPoketmon.add(parse.get(i));
                Log.e("UserPoketmon", parse.get(i).getPoketMonName() + " 들어감");
                Log.e("UserPoketmon", UserPoketmon.get(i).getPoketMonName() + " 들어옴");
                Log.e("UserPoketmon", "UserPoketmon " + UserPoketmon.size() + " 마리 존재");
            }
        }

            /*
            //저장되어있는 어레이리스트에서 피츄 업그레이드 하는 방법
            for (int i = 0; i < parse.size(); i++) {
                Log.i("get(i)",
                        parse.get(i).getPoketMonName() + " / " +
                                parse.get(i).getPoketMonAtk() + " / " +
                                parse.get(i).getPoketMonCoin() + " / " +
                                parse.get(i).getPoketMonDia() + " / " +
                                parse.get(i).getPoketMonHp() + " / " +
                                parse.get(i).getPoketMonImage() + " / " +
                                parse.get(i).getPoketMonPokenum() + " / " +
                                parse.get(i).getPoketMonProfile() + " / " +
                                parse.get(i).getPoketMonSpeed());
                if (parse.get(i).getPoketMonName().equals("피츄")) {
                    int old_atk = parse.get(i).getPoketMonAtk();
                    int new_atk = old_atk + 1;
                    parse.get(i).setPoketMonAtk(new_atk);
                }

            }
            //업그레이드 된 피츄를 다시 저장해주는 역할
            String ToJson = gson.toJson(parse);
            editor.putString("User_Poketmon", ToJson);
            editor.apply();

            //업그레이드 되었는지 확인해보기
            for (int i = 0; i < parse.size(); i++) {
                Log.i("get new(i)",
                        parse.get(i).getPoketMonName() + " / " +
                                parse.get(i).getPoketMonAtk() + " / " +
                                parse.get(i).getPoketMonCoin() + " / " +
                                parse.get(i).getPoketMonDia() + " / " +
                                parse.get(i).getPoketMonHp() + " / " +
                                parse.get(i).getPoketMonImage() + " / " +
                                parse.get(i).getPoketMonPokenum() + " / " +
                                parse.get(i).getPoketMonProfile() + " / " +
                                parse.get(i).getPoketMonSpeed());
            }*/

        //로그용
        SharedPreferences pref1 = getSharedPreferences("Game", MODE_PRIVATE);//쉐어드 선언 Key : User
        //SharedPreferences.Editor editor = pref1.edit();//쉐어드 사용할 'editor'선언
        Type type1 = new TypeToken<ArrayList<ClassAchieveData>>() {
        }.getType();

        ArrayList<ClassAchieveData> parse1 = gson.fromJson(pref1.getString("Game_Achieve", ""), type1);
        //저장되어있는 어레이리스트에서 피츄 업그레이드 하는 방법
        for (int i = 0; i < parse1.size(); i++) {
            Log.i("get(i)",
                    parse1.get(i).getTitle() + " / " +
                            parse1.get(i).getProgress() + " / " +parse1.get(i).getProgressmax());
            if (parse1.get(i).getTitle().equals("모험횟수")) {


                    Log.e("모험횟수",String.valueOf(parse1.get(i).getProgress()));

            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        hideNaviUI();
        setMainText();
        viewStatus();
        /*pb_MainExpBar.setProgress(ClassUserData.getInstance().getExp());
        pb_MainExpBar.setMax(ClassUserData.getInstance().getExpMax());*/
        tag = "메인Activity";
        Log.e(tag, "onPause");
    }

    /*@Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }*/

}//메인 끝자락

//void apply () : API 9(2.3) 에서 추가. 호출후 곧바로 리턴되어 스레드를 블록시키지 않는다.
//boolean commit () : 호출시 스레드는 block 되고 커널에서 파일 저장 완료호 함수는 리턴되고
//스레드는 다시 작동하며 처리결과를 true/false 로 반환한다.
//굳이 결과값이 필요 없다면 apply 를 사용하는게 반응성 면에서 좋다.
