package com.gillyweed.android.asklah;

public class Achievement {

    private String achievementName;
    private boolean achieved;

    public Achievement(String achievementName, boolean achieved) {
        this.achievementName = achievementName;
        this.achieved = achieved;
    }

    public String getAchievementName() {
        return this.achievementName;
    }

    public boolean getAchieved() {
        return this.achieved;
    }
}
