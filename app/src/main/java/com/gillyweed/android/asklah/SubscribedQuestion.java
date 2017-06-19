package com.gillyweed.android.asklah;

import java.util.ArrayList;

public class SubscribedQuestion {
    private String title;
    private String description;
    private ArrayList<String> tags;

    public SubscribedQuestion(String title, String description, ArrayList<String> tags) {
        this.title = title;
        this.description = description;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
}
