package com.shootinggame.gameshooting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Activity_2_5FirstSelect extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerVIewAdapter_First adapter;
    private GridLayoutManager layoutManager;
    ArrayList<ClassPoketMon> fslist = new ArrayList<ClassPoketMon>();
    ArrayList<ClassPoketMon> select = new ArrayList<ClassPoketMon>();
    public Gson gson = new Gson();
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_5first_select);
        hideNaviUI();
        final SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
        final SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
        final SharedPreferences game = getSharedPreferences("TeamPage", MODE_PRIVATE);//쉐어드 선언 Key : User

        fslist.add(new ClassPoketMon("피츄",R.drawable.pichu, R.drawable.pichuprofile,10, 100, 500,1,1,5,1,5,"피츄 입니다.",1,1,5,5,5));
        fslist.add(new ClassPoketMon("파이리", R.drawable.pairi, R.drawable.pairiprofile,4, 100, 500,1,1,5,1,5,"파이리 입니다.",1,1,5,5,5));
        fslist. add(new ClassPoketMon("꼬부기", R.drawable.ggobugi, R.drawable.ggobugiprofile,7, 100, 500,1,1,5,1,5,"꼬부기 입니다.",1,1,5,5,5));
        fslist. add(new ClassPoketMon("이상해씨", R.drawable.esangheassi, R.drawable.esangheassiprofile, 1, 100, 500,1,1,5,1,5,"이상해씨 입니다.",1,1,5,5,5));

        recyclerView = (RecyclerView) findViewById(R.id.rv_first_select);
        recyclerView.setItemViewCacheSize(5);
        adapter = new RecyclerVIewAdapter_First(getApplicationContext(), fslist);
        layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //어텝터 클릭리스너 인터페이스
        adapter.setOnItemClickListener(new ItemFirstClickListener() {
            @Override
            public void onItemClick(RecyclerVIewAdapter_First.MyViewHolder holder, View view, int position) {
                //어댑터에서 포지션을 가지고 온다.
                int pos = holder.getAdapterPosition();
                //공유 객체로 사용할 레이아웃의 아이디를 지정해준다.
                View img = view.findViewById(R.id.recylcerview_first_image);
                //페어할 이미지를 만든다.                 위 이미지를 교환할 트렌젝션이름을 통해
                Pair<View, String> pair_IMG = Pair.create(img, img.getTransitionName());
                //Pair<View, String> pair_IMG2 = Pair.create(img, img.getTransitionName());
                //액티비티 옵션컴팻 중 스크린 트랜지션애니메이션을 설정해주는데, 현재액티비티와, 공유객체할 이미지로 설정한다.
                ActivityOptionsCompat optionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(Activity_2_5FirstSelect.this, pair_IMG);

                //인텐트로 이동을한다.
                int click = fslist.get(pos).Image;
                int click2 = fslist.get(pos).Profile;
                String click3 = fslist.get(pos).Name;
                Log.e("문자열",fslist.get(pos).Name);
                switch (pos) {
                    case 0:
                        select.add(new ClassPoketMon("피츄",R.drawable.pichu, R.drawable.pichuprofile,10, 100, 500,1,1,5,1,5,"피츄 입니다.",1,1,5,5,5));
                        Log.e("list main size: ", String.valueOf(select.size()));
                        //gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                        String ToJson = gson.toJson(select);
                        //쉐어드의 editor 를 통해 JSON 스트링 저장 Key : User_PoketMon
                        editor.putString("User_Poketmon", ToJson);
                        Log.e("FIRST_SELECT / Shared저장값", pref.getString("User_Poketmon", ""));
                        Log.e("FIRST_SELECT / ToJson", ToJson);
                        editor.putInt("PokeIMG", click);
                        editor.putInt("UserProfile", click2);
                        editor.putString("PokeName",click3);
                        editor.commit();//쉐어드 에디터 닫아주는데 apply 비동기처리 설명은 맨아래 주석
                        break;
                    case 1:
                        select.add(new ClassPoketMon("파이리", R.drawable.pairi, R.drawable.pairiprofile,4, 100, 500,1,1,5,1,5,"파이리 입니다.",1,1,5,5,5));
                        Log.e("list main size: ", String.valueOf(select.size()));
                        String ToJson1 = gson.toJson(select);//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                        editor.putString("User_Poketmon", ToJson1); //쉐어드의 editor 를 통해 JSON 스트링 저장 Key : User_PoketMon
                        Log.e("FIRST_SELECT / Shared저장값", pref.getString("User_Poketmon", ""));
                        Log.e("FIRST_SELECT / ToJson", ToJson1);
                        editor.putInt("PokeIMG", click);
                        editor.putInt("UserProfile", click2);
                        editor.putString("PokeName",click3);
                        editor.commit();//쉐어드 에디터 닫아주는데 apply 비동기처리 설명은 맨아래 주석
                        break;
                    case 2:
                        select. add(new ClassPoketMon("꼬부기", R.drawable.ggobugi, R.drawable.ggobugiprofile,7, 100, 500,1,1,5,1,5,"꼬부기 입니다.",1,1,5,5,5));
                        Log.e("FIRST_SELECT / list main size: ", String.valueOf(select.size()));
                        String ToJson2 = gson.toJson(select);//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                        editor.putString("User_Poketmon", ToJson2); //쉐어드의 editor 를 통해 JSON 스트링 저장 Key : User_PoketMon
                        editor.putInt("PokeIMG", click);
                        editor.putInt("UserProfile", click2);
                        editor.putString("PokeName",click3);
                        editor.commit();//쉐어드 에디터 닫아주는데 apply 비동기처리 설명은 맨아래 주석
                        Log.e("FIRST_SELECT / Shared저장값", pref.getString("User_Poketmon", ""));
                        Log.e("FIRST_SELECT / ToJson", ToJson2);
                        break;
                    case 3:
                        select. add(new ClassPoketMon("이상해씨", R.drawable.esangheassi, R.drawable.esangheassiprofile, 1, 100, 500,1,1,5,1,5,"이상해씨 입니다.",1,1,5,5,5));
                        Log.e("list main size: ", String.valueOf(select.size()));
                        String ToJson3 = gson.toJson(select);//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                        editor.putString("User_Poketmon", ToJson3); //쉐어드의 editor 를 통해 JSON 스트링 저장 Key : User_PoketMon
                        Log.e("Shared저장값", pref.getString("User_Poketmon", ""));
                        Log.e("ToJson", ToJson3);
                        editor.putInt("PokeIMG", click);
                        editor.putInt("UserProfile", click2);
                        editor.putString("PokeName",click3);
                        editor.commit();//쉐어드 에디터 닫아주는데 apply 비동기처리 설명은 맨아래 주석
                        break;

                }
                Intent intent = new Intent(getApplicationContext(), Activity_3Main.class);

                // 최초 업적을 Shared에 저장한다.
                SharedPreferences pref_achieve = getSharedPreferences("ACHIEVE", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor_achieve = pref_achieve.edit();//쉐어드 사용할 'editor'선언
                int boss = 0;
                int bossMax = 20;
                int bossDia = 50;
                int bossCoin = 100;
                editor_achieve.putInt("ACHIEVE_BOSSKILL", boss);
                editor_achieve.putInt("ACHIEVE_BOSSKILLMAX", bossMax);
                editor_achieve.putInt("ACHIEVE_BOSSKILLDIA", bossDia);
                editor_achieve.putInt("ACHIEVE_BOSSKILLCOIN", bossCoin);
                int midboss = 0;
                int midbossMax = 10;
                int midbossDia = 30;
                int midbossCoin = 60;
                editor_achieve.putInt("ACHIEVE_MIDBOSSKILL", midboss);
                editor_achieve.putInt("ACHIEVE_MIDBOSSKILLMAX", midbossMax);
                editor_achieve.putInt("ACHIEVE_MIDBOSSKILLDIA", midbossDia);
                editor_achieve.putInt("ACHIEVE_MIDBOSSKILLCOIN", midbossCoin);
                int nomal = 0;
                int nomalMax = 50;
                int nomalDia = 10;
                int nomalCoin = 20;
                editor_achieve.putInt("ACHIEVE_NOMALKILL", nomal);
                editor_achieve.putInt("ACHIEVE_NOMALKILLMAX", nomalMax);
                editor_achieve.putInt("ACHIEVE_NOMALKILLDIA", nomalDia);
                editor_achieve.putInt("ACHIEVE_NOMALKILLCOIN", nomalCoin);
                int adVenture = 0;
                int adVentureMax = 20;
                int adVentureDia = 10;
                int adVentureCoin = 20;
                editor_achieve.putInt("ACHIEVE_ADVENTURE", adVenture);
                editor_achieve.putInt("ACHIEVE_ADVENTUREMAX", adVentureMax);
                editor_achieve.putInt("ACHIEVE_ADVENTUREDIA", adVentureDia);
                editor_achieve.putInt("ACHIEVE_ADVENTURECOIN", adVentureCoin);
                int dIe =  0;
                int dIeMax = 20;
                int dIeDia =  5;
                int dIeCoin = 10;
                editor_achieve.putInt("ACHIEVE_DIE", dIe);
                editor_achieve.putInt("ACHIEVE_DIEMAX", dIeMax);
                editor_achieve.putInt("ACHIEVE_DIEDIA", dIeDia);
                editor_achieve.putInt("ACHIEVE_DIECOIN", dIeCoin);

                editor_achieve.commit();

                startActivity(intent, optionsCompat.toBundle());

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
   /* @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();

    }*/

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("선택창","onStop");
        finish();

    }
}
