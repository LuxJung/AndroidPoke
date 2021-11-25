package com.shootinggame.gameshooting;

public class ClassPoketMon {
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



    public ClassPoketMon(String name, int image, int profile, int pokenum, int dia, int coin, int speed, int hp, int maxHp, int atk , int maxAtk, String introduce, int speedAtk , int speedMissile, int maxSpeed, int maxSpeedAtk, int maxSpeedMissile) {
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

    public int getMaxSpeed() {
        return MaxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        MaxSpeed = maxSpeed;
    }

    public int getMaxSpeedAtk() {
        return MaxSpeedAtk;
    }

    public void setMaxSpeedAtk(int maxSpeedAtk) {
        MaxSpeedAtk = maxSpeedAtk;
    }

    public int getMaxSpeedMissile() {
        return MaxSpeedMissile;
    }

    public void setMaxSpeedMissile(int maxSpeedMissile) {
        MaxSpeedMissile = maxSpeedMissile;
    }
    public int getSpeedAtk() {
        return SpeedAtk;
    }

    public void setSpeedAtk(int speedAtk) {
        SpeedAtk = speedAtk;
    }

    public int getSpeedMissile() {
        return SpeedMissile;
    }

    public void setSpeedMissile(int speedMissile) {
        SpeedMissile = speedMissile;
    }
    public int getMaxHp() {
        return MaxHp;
    }

    public void setMaxHp(int maxHp) {
        MaxHp = maxHp;
    }

    public int getMaxAtk() {
        return MaxAtk;
    }

    public void setMaxAtk(int maxAtk) {
        MaxAtk = maxAtk;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public String getPoketMonName() {
        return Name;
    }

    public void setPoketMonName(String name) {
        Name = name;
    }

    public int getPoketMonImage() {
        return Image;
    }

    public void setPoketMonImage(int image) {
        Image = image;
    }

    public int getPoketMonProfile() {
        return Profile;
    }

    public void setPoketMonProfile(int profile) {
        Profile = profile;
    }

    public int getPoketMonPokenum() {
        return Pokenum;
    }

    public void setPoketMonPokenum(int pokenum) {
        Pokenum = pokenum;
    }

    public int getPoketMonDia() {
        return Dia;
    }

    public void setPoketMonDia(int dia) {
        Dia = dia;
    }

    public int getPoketMonCoin() {
        return Coin;
    }

    public void setPoketMonCoin(int coin) {
        Coin = coin;
    }

    public int getPoketMonSpeed() {
        return Speed;
    }

    public void setPoketMonSpeed(int speed) {
        Speed = speed;
    }

    public int getPoketMonHp() {
        return Hp;
    }

    public void setPoketMonHp(int hp) {
        Hp = hp;
    }

    public int getPoketMonAtk() {
        return Atk;
    }

    public void setPoketMonAtk(int atk) {
        Atk = atk;
    }
}