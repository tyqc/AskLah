package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Envy 15 on 13/7/2017.
 */

public class PostOverview {

    @SerializedName("post_id")
    private int postId;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("date_updated")
    private DateObj dateUpdated;

    @SerializedName("nus_id")
    private String nusId;

    @SerializedName("tags")
    private PostTagArray tags;

    @SerializedName("voted")
    private int voted;

    public int getPostId()
    {
        return postId;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public DateObj getDateUpdated()
    {
        return dateUpdated;
    }

    public PostTagArray getTags()
    {
        return tags;
    }

    public String getNusId()
    {
        return nusId;
    }

    public int getVoted()
    {
        return voted;
    }
}
