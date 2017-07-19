package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 18/7/2017.
 */

public class EditPost {
    @SerializedName("post_id")
    private int postId;

    @SerializedName("title")
    private String postTitle;

    @SerializedName("description")
    private String postDescription;

    public void setPostId(int postId)
    {
        this.postId = postId;
    }

    public void setPostTitle(String postTitle)
    {
        this.postTitle = postTitle;
    }

    public void setPostDescription(String postDescription)
    {
        this.postDescription = postDescription;
    }
}
