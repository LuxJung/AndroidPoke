package com.shootinggame.gameshooting;

import android.os.CountDownTimer;
import android.util.Log;


public class Service_CountDown_Thread extends Thread implements Runnable {

    boolean isRun = true;
    public CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 100; //총 시간 (300초 = 5분)//원래 300*1000
    final int COUNT_DOWN_INTERVAL = 100;//1000
    private Activity_3Main activity_3Main = new Activity_3Main();
    private Activity_4Team activity_4Team = new Activity_4Team();
    private Activity_4Team_Powerup activity_4Team_powerup = new Activity_4Team_Powerup();
    private Activity_5Book activity_5Book = new Activity_5Book();
    private Activity_5Book_Detail activity_5Book_detail = new Activity_5Book_Detail();
    private Activity_6Store activity_6Store = new Activity_6Store();
    private Activity_7GameStart_Linear_Table activity_7GameStart_linear_table = new Activity_7GameStart_Linear_Table();


    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }


    public void run() {
        //반복적으로 수행할 작업을 한다
        countDownTimer();
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //카운트 다운 메소드
    public void countDownTimer() {
        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //(300초에서 1초 마다 계속 줄어듬)
                try {
                    long emailAuthCount = millisUntilFinished / 100;//1000
                    Log.d("Time", emailAuthCount + "");
                    if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                        final String a = (emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60));

                        Activity_3Main.set3MainTimerText(a);
                        Activity_3Main.set3MainTimer();

                        Activity_4Team.set4TeamTimerText(a);
                        Activity_4Team.set4TeamTimer();

                        Activity_4Team_Powerup.set4TeamPowerTimerText(a);
                        Activity_4Team_Powerup.set4TeamPowerTimer();

                        Activity_5Book.set5BookTimerText(a);
                        Activity_5Book.set5BookTimer();

                        Activity_5Book_Detail.set5BookDetailTimerText(a);
                        Activity_5Book_Detail.set5BookDetailTimer();

                        Activity_6Store.set6ShopTimerText(a);
                        Activity_6Store.set6ShopTimer();

                        Activity_7GameStart_Linear_Table.set7GameStartTimerText(a);
                        Activity_7GameStart_Linear_Table.set7GameStartTimer();

                        Activity_3Main.MainViewVisible();
                        Activity_4Team.TeamViewVisible();
                        Activity_4Team_Powerup.TeamPowerViewVisible();
                        Activity_5Book.BookViewVisible();
                        Activity_5Book_Detail.BookDetailViewVisible();
                        Activity_6Store.StoreViewVisible();
                        Activity_7GameStart_Linear_Table.GameStartViewVisible();

                        //tv_TimeCount.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                        //ClassUserData.getInstance().setTime(tv_TimeCount.getText().toString());
                    } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                        final String aa = (emailAuthCount / 60) + " :0" + (emailAuthCount - ((emailAuthCount / 60) * 60));

                        Activity_3Main.set3MainTimerText(aa);
                        Activity_3Main.set3MainTimer();
                        Activity_4Team.set4TeamTimerText(aa);
                        Activity_4Team.set4TeamTimer();
                        Activity_4Team_Powerup.set4TeamPowerTimerText(aa);
                        Activity_4Team_Powerup.set4TeamPowerTimer();
                        Activity_5Book.set5BookTimerText(aa);
                        Activity_5Book.set5BookTimer();
                        Activity_5Book_Detail.set5BookDetailTimerText(aa);
                        Activity_5Book_Detail.set5BookDetailTimer();
                        Activity_6Store.set6ShopTimerText(aa);
                        Activity_6Store.set6ShopTimer();
                        Activity_7GameStart_Linear_Table.set7GameStartTimerText(aa);
                        Activity_7GameStart_Linear_Table.set7GameStartTimer();

                        Activity_3Main.MainViewVisible();
                        Activity_4Team.TeamViewVisible();
                        Activity_4Team_Powerup.TeamPowerViewVisible();
                        Activity_5Book.BookViewVisible();
                        Activity_5Book_Detail.BookDetailViewVisible();
                        Activity_6Store.StoreViewVisible();
                        Activity_7GameStart_Linear_Table.GameStartViewVisible();
                        //tv_TimeCount.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                        //ClassUserData.getInstance().setTime(tv_TimeCount.getText().toString());
                    }

                    //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                    // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                    // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료
                try {
                    int cnt = ClassUserData.getInstance().getCount() + 1;
                    ClassUserData.getInstance().setCount(cnt);

                    Activity_3Main.set3MainUserCount(Integer.toString(ClassUserData.getInstance().getCount()));
                    Activity_4Team.set4TeamUserCount(Integer.toString(ClassUserData.getInstance().getCount()));
                    Activity_4Team_Powerup.set4TeamPowerUserCount(Integer.toString(ClassUserData.getInstance().getCount()));
                    Activity_5Book.set5BookUserCount(Integer.toString(ClassUserData.getInstance().getCount()));
                    Activity_5Book_Detail.set5BookDetailUserCount(Integer.toString(ClassUserData.getInstance().getCount()));
                    Activity_6Store.set6ShopUserCount(Integer.toString(ClassUserData.getInstance().getCount()));
                    Activity_7GameStart_Linear_Table.set7GameStartUserCount(Integer.toString(ClassUserData.getInstance().getCount()));
                    if (ClassUserData.getInstance().getMaxCount() == ClassUserData.getInstance().getCount()) {
                        Activity_3Main.MainViewGone();
                        Activity_4Team.TeamViewGone();
                        Activity_4Team_Powerup.TeamPowerViewGone();
                        Activity_5Book.BookViewGone();
                        Activity_5Book_Detail.BookDetailViewGone();
                        Activity_6Store.StoreViewGone();
                        Activity_7GameStart_Linear_Table.GameStartViewGone();
                        isRun = true;
                    } else if (ClassUserData.getInstance().getMaxCount() > ClassUserData.getInstance().getCount()) {
                        countDownTimer();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
