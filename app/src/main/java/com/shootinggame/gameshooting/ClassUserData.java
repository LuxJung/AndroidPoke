package com.shootinggame.gameshooting;

public class ClassUserData {
    private String Name;
    private int Diamond, Gold, Level, Exp, Profile, MaxCount, Count ,ExpMax;
    private String Time;
    public int getMaxCount() {
        return MaxCount;
    }

    public void setMaxCount(int maxCount) {
        MaxCount = maxCount;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getExpMax() {
        return ExpMax;
    }

    public void setExpMax(int expMax) {
        ExpMax = expMax;
    }

    public ClassUserData(String name, int diamond, int gold, int level, int exp, int expmax, int profile, int maxcount, int count) {
        Name = name;
        Diamond = diamond;
        Gold = gold;
        Level = level;
        Exp = exp;
        Profile = profile;
        MaxCount = maxcount;
        Count = count;
        ExpMax = expmax;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;

    }

    public int getDiamond() {
        return Diamond;
    }

    public void setDiamond(int diamond) {
        Diamond = diamond;
    }

    public int getGold() {
        return Gold;
    }

    public void setGold(int gold) {
        Gold = gold;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getExp() {
        return Exp;
    }

    public void setExp(int exp) {
        Exp = exp;
    }

    public int getProfile() {
        return Profile;
    }

    public void setProfile(int profile) {
        Profile = profile;
    }

    private static ClassUserData instance;

    public static synchronized ClassUserData getInstance() {
        if (null == instance) {

            instance = new ClassUserData("연습생", 10000, 100000, 1, 0,30, R.drawable.whoareyou, 5, 5);
        }

        return instance;

    }


}
