package com.shootinggame.gameshooting;

import java.util.ArrayList;

public class ClassUserPoketmonData extends ClassPoketMon{

    static ArrayList<ClassUserPoketmonData>poketmonDataArrayList = new ArrayList<>();
    String Name;
    int Image;
    int Profile;
    int Pokenum;
    int Dia;
    int Coin;
    int Speed;
    int MaxSpeed;
    int SpeedAtk;
    int MaxSpeedAtk;
    int Hp;
    int MaxHp;
    int Atk;
    int MaxAtk;
    int SpeedMissile;
    int MaxSpeedMissile;
    String Introduce;

    public ClassUserPoketmonData(String name, int image, int profile, int pokenum, int dia, int coin, int speed, int hp, int maxHp, int atk , int maxAtk, String introduce, int speedAtk , int speedMissile, int maxSpeed, int maxSpeedAtk, int maxSpeedMissile) {
        super(name, image, profile, pokenum, dia, coin, speed, hp,maxHp,atk, maxAtk,introduce,speedAtk, speedMissile,maxSpeed,maxSpeedAtk,maxSpeedMissile );
        this.Name = name;
        this.Image = image;
        this.Profile = profile;
        this.Pokenum = pokenum;
        this.Dia = dia;
        this.Coin = coin;
        this.Speed = speed;
        this.SpeedAtk = speedAtk;
        this.Hp = hp;
        this.MaxHp = maxHp;
        this.Atk = atk;
        this.MaxAtk = maxAtk;
        this.Introduce = introduce;
        this.SpeedMissile = speedMissile;
        this.MaxSpeed = maxSpeed;
        this.MaxSpeedAtk = maxSpeedAtk;
        this.MaxSpeedMissile = maxSpeedMissile;

    }


    public String getNameUserPoketMon() {
        return Name;
    }

    public void setNameUserPoketMon(String name) {
        Name = name;
    }

    public int getImageUserPoketMon() {
        return Image;
    }

    public void setImageUserPoketMon(int image) {
        Image = image;
    }

    public int getProfileUserPoketMon() {
        return Profile;
    }

    public void setProfileUserPoketMon(int profile) {
        Profile = profile;
    }

    public int getPokenumUserPoketMon() {
        return Pokenum;
    }

    public void setPokenumUserPoketMon(int pokenum) {
        Pokenum = pokenum;
    }

    public int getSpeedUserPoketMon() {
        return Speed;
    }

    public void setSpeedUserPoketMon(int speed) {
        Speed = speed;
    }

    public int getHpUserPoketMon() {
        return Hp;
    }

    public void setHpUserPoketMon(int hp) {
        Hp = hp;
    }

    public int getAtkUserPoketMon() {
        return Atk;
    }

    public void setAtkUserPoketMon(int atk) {
        Atk = atk;
    }

    /*private static ClassUserPoketmonData instance;
    public static synchronized ClassUserPoketmonData getInstance() {
        if (null == instance) {
            instance = new ClassUserPoketmonData("피츄",R.drawable.pichu, R.drawable.pichuprofile,10, 100, 500,1,1,1);
            poketmonDataArrayList.add(instance);
        }

        return instance;
    }*/
}
