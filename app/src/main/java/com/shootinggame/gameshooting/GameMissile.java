package com.shootinggame.gameshooting;

import android.graphics.Point;

public class GameMissile {
    Point pos; //미사일 좌표 변수
    int X;
    int Y;
    int Who;
    int Speed;
    GameMissile(int x, int y, int who, int speed) { //미사일 좌표를 입력 받는 메소드
        pos = new Point(x, y); //미사일 좌표를 체크
        //X=x;
        //Y=y;
        Who = who;
        Speed = speed;
    }

    public void move() { //미사일 이동을 위한 메소드
        pos.x += 100+Speed; //x 좌표에 10만큼 미사일 이동
    }

    public void emove() { //미사일 이동을 위한 메소드
        pos.x -= 100; //x 좌표에 10만큼 미사일 이동
    }
}
