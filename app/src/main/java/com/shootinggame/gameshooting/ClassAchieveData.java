package com.shootinggame.gameshooting;

public class ClassAchieveData {
    String title;
    int progress;
    int progressmax;
    int achievedia;
    int achievegold;

    public ClassAchieveData(String title, int progress, int progressmax, int achievedia, int achievegold) {
        this.title = title;
        this.progress = progress;
        this.progressmax = progressmax;
        this.achievedia = achievedia;
        this.achievegold = achievegold;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgressmax() {
        return progressmax;
    }

    public void setProgressmax(int progressmax) {
        this.progressmax = progressmax;
    }

    public int getAchievedia() {
        return achievedia;
    }

    public void setAchievedia(int achievedia) {
        this.achievedia = achievedia;
    }

    public int getAchievegold() {
        return achievegold;
    }

    public void setAchievegold(int achievegold) {
        this.achievegold = achievegold;
    }
}
