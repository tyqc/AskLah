package com.gillyweed.android.asklah;

public class Announcement {
    private String username;
    private String time;
    private String achievementName;

    public Announcement(String username, String time, String achievementName) {
        this.username = username;
        this.time = time;
        this.achievementName = achievementName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getTime() {
        return this.time;
    }

    public String getAchievementName() {
        return this.achievementName;
    }
}
