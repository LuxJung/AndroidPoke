package com.shootinggame.gameshooting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Activity_9PopUp_Profile extends AppCompatActivity implements TextView.OnEditorActionListener {
    private EditText et_ChangeName_Profile;
    private ImageButton ib_Close_Profile;
    private Button bt_ChangeNameBtn_Profile, bt_ChangeProfileBtn_Profile;
    private String tag;
    private ImageView iv_Profile_Profile;
    private RecyclerView recyclerView;
    private RecyclerVIewAdapter_Profile adapter;
    private GridLayoutManager layoutManager;
    public static Activity activity;
    private InputMethodManager imm;
    private LinearLayout ll;

    //포켓몬을 보유하게 될 경우 여기에 추가시켜주어야 프로필이 나올 것 같습니다.


    /* = new ArrayList<ClassPoketMon>() {{
        // po0 = (new ClassPoketMon("이상해씨", R.drawable.esangheassi, R.drawable.esangheassiprofile, 1, 100, 500,1,1,1));
        //ClassPoketMon po1 = (new ClassPoketMon("이상해풀", R.drawable.esangheapul, R.drawable.esangheapulprofile,2, 200,1000,5,5,5));
        //ClassPoketMon po2 = (new ClassPoketMon("이상해꽃", R.drawable.esangheaggot, R.drawable.esangheaggotprofile,3,500,5000,10,10,10));
        //ClassPoketMon po3 = (new ClassPoketMon("파이리", R.drawable.pairi, R.drawable.pairiprofile,4, 100, 500,1,1,1));
        ///ClassPoketMon po4 = (new ClassPoketMon("리자드", R.drawable.parijade, R.drawable.parijadeprofile,5,200,1000,5,5,5));
        //add(new ClassPoketMon("리자몽", R.drawable.parijamong, R.drawable.parijamongprofile,6,500,5000,10,10,10));
        //ClassPoketMon po5 = (new ClassPoketMon("리자몽", R.drawable.parijamong, R.drawable.parijamongprofile,6,500,5000,10,10,10));
        //ClassPoketMon po6 = (new ClassPoketMon("꼬부기", R.drawable.ggobugi, R.drawable.ggobugiprofile,7, 100, 500,1,1,1));
        //ClassPoketMon po7 = (new ClassPoketMon("어니부기", R.drawable.ggounibugi, R.drawable.ggounibugiprofile,8, 200,1000,5,5,5));
        //ClassPoketMon po8 = (new ClassPoketMon("거북왕", R.drawable.ggogubuckwang, R.drawable.ggogubuckwangprofile,9,500,5000,10,10,10));
        add(new ClassPoketMon("피츄", R.drawable.pichu, R.drawable.pichuprofile, 10, 100, 500, 1, 1, 1));
        add(new ClassPoketMon("피카츄", R.drawable.picachu, R.drawable.picachuprofile,11, 200,1000,5,5,5));
        // po11 = (new ClassPoketMon("라이츄", R.drawable.raichu,R.drawable.raichuprofile ,12,500,5000,10,10,10));
        //list.add(po0);list.add(po1);list.add(po2);list.add(po3);list.add(po4);list.add(po5);list.add(po6);list.add(po7);list.add(po8);list.add(po10);list.add(po11);

        //list.add(po9);
    }};*/
    Activity_3Main Main = (Activity_3Main) Activity_3Main.activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_9popup_profile);
        hideNaviUI();
        popFindID_Profile();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        et_ChangeName_Profile.setText(pref.getString("UserName", ClassUserData.getInstance().getName()));



        recyclerView = (RecyclerView) findViewById(R.id.profile_grid_recyclerview);
        adapter = new RecyclerVIewAdapter_Profile(getApplicationContext(), Main.getONLY_PROFILE_UserPoketmon());
        layoutManager = new GridLayoutManager(getApplicationContext(), 4);


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //현재 이미지보기
        iv_Profile_Profile.setImageResource(pref.getInt("UserProfile", ClassUserData.getInstance().getProfile()));



        //어뎁터 클릭리스너 인터페이스
        adapter.setOnItemClickListener(new ItemProfileClickListener() {
            @Override
            public void onItemClick(RecyclerVIewAdapter_Profile.MyViewHolder holder, View view, int position) {
                SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
                int pos = holder.getAdapterPosition();//Adapter 의 holder 에서 position 을 받아온다.
                int click = Main.getONLY_PROFILE_UserPoketmon().get(pos).Profile;
                ClassUserData.getInstance().setProfile(click);
                Toast.makeText(getApplicationContext(), Main.getONLY_PROFILE_UserPoketmon().get(pos).Name, Toast.LENGTH_SHORT).show();
                iv_Profile_Profile.setImageResource(click);
                editor.putInt("UserProfile", ClassUserData.getInstance().getProfile());
                editor.commit();
            }
        });

        ll.setOnClickListener(myClickListener);
/*
        et_ChangeName_Profile.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE :
                        // TODO : process "actionDone"
                        hideNaviUI();
                        System.out.println("actionDone") ;
                        break ;
                }
                return true ;
            }
        });*/

        et_ChangeName_Profile.setOnEditorActionListener(this);

        //프로필설정 닫기부분
        ib_Close_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "프로필 변경 완료!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //아이디 설정의 글자 입력수 제한
        et_ChangeName_Profile.setFilters(new InputFilter[]{filterKor, new InputFilter.LengthFilter(6)});
    }

    //한글 입력만 받기위한 필터
    public InputFilter filterKor = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[ㄱ-ㅣ가-힣]*$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };

    //레이아웃 아이디 찾아주기 findViewById
    public void popFindID_Profile() {
        et_ChangeName_Profile = findViewById(R.id.popup_profile_Name);
        ib_Close_Profile = findViewById(R.id.popup_profile_Close);
        bt_ChangeNameBtn_Profile = findViewById(R.id.popup_profile_check);
        bt_ChangeProfileBtn_Profile = findViewById(R.id.popup_profile_imgcheck);
        iv_Profile_Profile = findViewById(R.id.popup_profile_img);
        ll = findViewById(R.id.ll);
    }

    //디바이스 NavigationUI 숨기기
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
        tag = "프로필PopUP";
        Log.e(tag, "onPause");
    }

    //키보드 내리기위한 OnClickListener
    View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideKeyboard(Activity_9PopUp_Profile.this);
            hideNaviUI();
            String ChangeName = et_ChangeName_Profile.getText().toString();
            ClassUserData.getInstance().setName(ChangeName);
            SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
            SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
            editor.putString("UserName", ChangeName);
            Log.e("프로필 수정 이름", String.valueOf(ClassUserData.getInstance().getName()));

            editor.commit();
            et_ChangeName_Profile.clearFocus();
        }
    };
    //빈 화면 눌렀을때 키보드 내리기
    public static void hideKeyboard(Activity_9PopUp_Profile activity_9PopUp_profile) {
        InputMethodManager imm = (InputMethodManager) activity_9PopUp_profile.getSystemService(Activity_9PopUp_Profile.INPUT_METHOD_SERVICE);
        View view = activity_9PopUp_profile.getCurrentFocus();
        if (view == null) {
            view = new View(activity_9PopUp_profile);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


    }





    @Override
    protected void onResume() {
        super.onResume();
        hideNaviUI();

        tag = "프로필PopUP";
        Log.e(tag, "onResume");

    }

    @Override
    protected void onStop() {
        super.onStop();
        tag = "프로필PopUP";
        Log.e(tag, "onStop");
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        hideNaviUI();
        String ChangeName = et_ChangeName_Profile.getText().toString();
        ClassUserData.getInstance().setName(ChangeName);
        SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
        editor.putString("UserName", ChangeName);
        Log.e("프로필 수정 이름", String.valueOf(ClassUserData.getInstance().getName()));

        editor.commit();
        et_ChangeName_Profile.clearFocus();
        return false;
    }
}



/*
        //이름 변경버튼
        bt_ChangeNameBtn_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ChangeName = et_ChangeName_Profile.getText().toString();
                ClassUserData.getInstance().setName(ChangeName);
                SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
                editor.putString("UserName", ChangeName);
                editor.commit();
                Log.e("프로필 수정 이름", String.valueOf(ClassUserData.getInstance().getName()));
                Toast.makeText(getApplicationContext(), "프로필 이름 변경 완료!", Toast.LENGTH_SHORT).show();
            }
        });*/
/*
        bt_ChangeProfileBtn_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
                editor.putInt("UserProfile", ClassUserData.getInstance().getProfile());
                editor.commit();
                Toast.makeText(getApplicationContext(), "프로필 이미지 변경 완료!", Toast.LENGTH_SHORT).show();

            }
        });*/