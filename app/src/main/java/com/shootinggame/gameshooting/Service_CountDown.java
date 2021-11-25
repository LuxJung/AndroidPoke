package com.shootinggame.gameshooting;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class Service_CountDown extends Service {
    private IBinder mIBinder = new MyBinder();
    Service_CountDown_Thread thread = new Service_CountDown_Thread();
    int Count = ClassUserData.getInstance().getCount();
    int CountMax = ClassUserData.getInstance().getMaxCount();

    class MyBinder extends Binder {
        Service_CountDown getService() {
            return Service_CountDown.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("LOG", "onBind()");
        return mIBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("LOG", "onStartCommand()");
        if (thread.isRun == true && Count < CountMax) {
                thread.run();
                thread.isRun = false;
                if(thread.isRun){
            Log.e("thread State", String.valueOf(thread.isRun));
                }

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("LOG", "onDestroy()");
        if (Count == CountMax&& thread.isRun == false) {
           // thread.stopForever();
            thread.isRun = true;
        }
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("LOG", "onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        Log.e("LOG", "onCreate()");
        super.onCreate();
    }

}
