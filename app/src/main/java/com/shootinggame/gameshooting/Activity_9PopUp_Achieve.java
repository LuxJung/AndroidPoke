package com.shootinggame.gameshooting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_9PopUp_Achieve extends AppCompatActivity {
    private ImageButton ib_Close_Achieve;
    private RecyclerView recyclerView;
    private String tag;
    private GridLayoutManager layoutManager;
    private RecyclerVIewAdapter_Achieve adapter;
    private RecyclerVIewAdapter_Achieve.myViewHolder holder;
    public Gson gson = new Gson();
    Activity_2Join join = (Activity_2Join) Activity_2Join.activity;
    ArrayList<ClassAchieveData> achieveData = new ArrayList<ClassAchieveData>();
     ArrayList<ClassAchieveData> getAachieveData() {
        return achieveData;
    }
    @Override
    protected void onStart() {
        super.onStart();
//        adapter.notifyDataSetChanged();
        SharedPreferences pref_achieve = getSharedPreferences("Game", MODE_PRIVATE);//쉐어드 선언 Key : User
        final SharedPreferences.Editor editor_achieve = pref_achieve.edit();//쉐어드 사용할 'editor'선언
        Type type = new TypeToken<ArrayList<ClassAchieveData>>() {
        }.getType();
        ArrayList<ClassAchieveData> achieve = gson.fromJson(pref_achieve.getString("Game_Achieve", ""), type);

        if (join.achieve_this.size() == 0) {
            for (int i = 0; i < achieve.size(); i++) {
                join.achieve_this.add(achieve.get(i));
            }
        }

        for (int i = 0; i <  join.achieve_this.size(); i++) {
            join.achieve_this.get(i);
            Log.v("팝업에서", "제목: "+join.achieve_this.get(i).getTitle()+" / "
                    + "진행: "+join.achieve_this.get(i).getProgress()+" / "
                    + "맥스: "+join.achieve_this.get(i).getProgressmax()+" / ");
        }



        recyclerView = findViewById(R.id.RV);

        adapter = new RecyclerVIewAdapter_Achieve(getApplicationContext(), achieveData);
        layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new ItemAchieveClickListener() {
            @Override
            public void onItemClick(RecyclerVIewAdapter_Achieve.myViewHolder holder, View view, int position) {
                int pos = holder.getAdapterPosition();

                String ttl = holder.title.getText().toString();
                int ing = Integer.parseInt(holder.progress.getText().toString());
                int req = Integer.parseInt(holder.progressmax.getText().toString());
                int olddia = Integer.parseInt(holder.dia.getText().toString());
                int oldgold = Integer.parseInt(holder.gold.getText().toString());
                if (ing == req) {
                    achieveData.remove(pos);
                    int newreq = req * 2;
                    int newdia = olddia * 2;
                    int newgold = oldgold * 2;
                    achieveData.add(pos, new ClassAchieveData(ttl, 0, newreq, newdia, newgold));
                    String ToJson = gson.toJson(achieveData);//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                    editor_achieve.putString("Game_Achieve", ToJson);
                    editor_achieve.commit();
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "조건을 달성하지 못했습니다.", Toast.LENGTH_LONG).show();

                }
            }
        });

        ib_Close_Achieve = findViewById(R.id.popup_achieve_Close);
        ib_Close_Achieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9popup_achieve);
        hideNaviUI();

        SharedPreferences pref_achieve = getSharedPreferences("Game", MODE_PRIVATE);//쉐어드 선언 Key : User
        final SharedPreferences.Editor editor_achieve = pref_achieve.edit();//쉐어드 사용할 'editor'선언
        Type type = new TypeToken<ArrayList<ClassAchieveData>>() {
        }.getType();
        ArrayList<ClassAchieveData> achieve = gson.fromJson(pref_achieve.getString("Game_Achieve", ""), type);

        if (achieveData.size() == 0) {
            for (int i = 0; i < achieve.size(); i++) {
                achieveData.add(achieve.get(i));
            }
        }

        for (int i = 0; i <  achieveData.size(); i++) {
            achieveData.get(i);
            Log.v("팝업에서", "제목: "+achieveData.get(i).getTitle()+" / "
                    + "진행: "+achieveData.get(i).getProgress()+" / "
                    + "맥스: "+achieveData.get(i).getProgressmax()+" / ");
        }






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

    @Override
    protected void onPause() {
        super.onPause();
        hideNaviUI();
        tag = "업적PopUP";
        Log.e(tag, "onPause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNaviUI();
        tag = "업적PopUP";
        Log.e(tag, "onResume");
/*
        SharedPreferences pref_achieve = getSharedPreferences("Game", MODE_PRIVATE);//쉐어드 선언 Key : User
        final SharedPreferences.Editor editor_achieve = pref_achieve.edit();//쉐어드 사용할 'editor'선언
        Type type = new TypeToken<ArrayList<ClassAchieveData>>() {
        }.getType();
        ArrayList<ClassAchieveData> achieve = gson.fromJson(pref_achieve.getString("Game_Achieve", ""), type);




        ib_Close_Achieve = findViewById(R.id.popup_achieve_Close);
        recyclerView = findViewById(R.id.RV);

        ib_Close_Achieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new RecyclerVIewAdapter_Achieve(getApplicationContext(), join.getAchieve_thislist());
        layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new ItemAchieveClickListener() {
            @Override
            public void onItemClick(RecyclerVIewAdapter_Achieve.myViewHolder holder, View view, int position) {
                int pos = holder.getAdapterPosition();

                String ttl = holder.title.getText().toString();
                int ing = Integer.parseInt(holder.progress.getText().toString());
                int req = Integer.parseInt(holder.progressmax.getText().toString());
                int olddia = Integer.parseInt(holder.dia.getText().toString());
                int oldgold = Integer.parseInt(holder.gold.getText().toString());
                if (ing == req) {
                    join.getAchieve_thislist().remove(pos);
                    int newreq = req * 2;
                    int newdia = olddia * 2;
                    int newgold = oldgold * 2;
                    join.getAchieve_thislist().add(pos, new ClassAchieveData(ttl, 0, newreq, newdia, newgold));
                    String ToJson = gson.toJson(join.getAchieve_thislist());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                    editor_achieve.putString("Game_Achieve", ToJson);
                    editor_achieve.commit();
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "조건을 달성하지 못했습니다.", Toast.LENGTH_LONG).show();

                }
            }
        });


        if (join.achieve_this.size() == 0) {
            for (int i = 0; i < achieve.size(); i++) {
                join.achieve_this.add(achieve.get(i));
            }
        }*/
    }

    public void holderSetting() {
        SharedPreferences pref_achieve = getSharedPreferences("Game", MODE_PRIVATE);//쉐어드 선언 Key : User
        SharedPreferences.Editor editor_achieve = pref_achieve.edit();//쉐어드 사용할 'editor'선언
        Type type = new TypeToken<ArrayList<ClassAchieveData>>() {
        }.getType();


        ArrayList<ClassAchieveData> parse = gson.fromJson(pref_achieve.getString("Game_Achieve", ""), type);
        //저장되어있는 어레이리스트에서 피츄 업그레이드 하는 방법
        for (int i = 0; i < parse.size(); i++) {
            if(parse.get(i).title.equals("보스처치")) {
                holder.progressmax.setText(String.valueOf(parse.get(i).progressmax));
                holder.progress.setText(String.valueOf(parse.get(i).progress));
                holder.dia.setText(String.valueOf(parse.get(i).achievedia));
                holder.gold.setText(String.valueOf(parse.get(i).achievegold));
                holder.title.setText(parse.get(i).title);

                holder.progressBar.setMax(parse.get(i).progressmax);
                holder.progressBar.setProgress(parse.get(i).progress);
            }
            if(parse.get(i).title.equals("중보처치")) {
                holder.progressmax.setText(String.valueOf(parse.get(i).progressmax));
                holder.progress.setText(String.valueOf(parse.get(i).progress));
                holder.dia.setText(String.valueOf(parse.get(i).achievedia));
                holder.gold.setText(String.valueOf(parse.get(i).achievegold));
                holder.title.setText(parse.get(i).title);

                holder.progressBar.setMax(parse.get(i).progressmax);
                holder.progressBar.setProgress(parse.get(i).progress);
            }if(parse.get(i).title.equals("일반처치")) {
                holder.progressmax.setText(String.valueOf(parse.get(i).progressmax));
                holder.progress.setText(String.valueOf(parse.get(i).progress));
                holder.dia.setText(String.valueOf(parse.get(i).achievedia));
                holder.gold.setText(String.valueOf(parse.get(i).achievegold));
                holder.title.setText(parse.get(i).title);

                holder.progressBar.setMax(parse.get(i).progressmax);
                holder.progressBar.setProgress(parse.get(i).progress);
            }if(parse.get(i).title.equals("모험횟수")) {
                holder.progressmax.setText(String.valueOf(parse.get(i).progressmax));
                holder.progress.setText(String.valueOf(parse.get(i).progress));
                holder.dia.setText(String.valueOf(parse.get(i).achievedia));
                holder.gold.setText(String.valueOf(parse.get(i).achievegold));
                holder.title.setText(parse.get(i).title);

                holder.progressBar.setMax(parse.get(i).progressmax);
                holder.progressBar.setProgress(parse.get(i).progress);
            }if(parse.get(i).title.equals("사망횟수")) {
                holder.progressmax.setText(String.valueOf(parse.get(i).progressmax));
                holder.progress.setText(String.valueOf(parse.get(i).progress));
                holder.dia.setText(String.valueOf(parse.get(i).achievedia));
                holder.gold.setText(String.valueOf(parse.get(i).achievegold));
                holder.title.setText(parse.get(i).title);

                holder.progressBar.setMax(parse.get(i).progressmax);
                holder.progressBar.setProgress(parse.get(i).progress);
            }

        }
    }

}
