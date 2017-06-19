package com.gillyweed.android.asklah;

public class Comment {
    private String message;
    private String user;
    private int upvotesNum;

    public Comment(String message, String user) {
        this.message = message;
        this.user = user;
        this.upvotesNum = 0; // Number of upvotes is zero when question is first published
    }

    public String getMessage() {
        return this.message;
    }

    public String getUser() {
        return this.user;
    }

    public int getUpvotesNum() {
        return this.upvotesNum;
    }
}
