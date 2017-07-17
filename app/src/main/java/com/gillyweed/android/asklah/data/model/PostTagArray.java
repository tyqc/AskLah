package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Envy 15 on 13/7/2017.
 */

public class PostTagArray {

    @SerializedName("tag")
    private ArrayList<PostTags> tags;

    public ArrayList<PostTags> getTags()
    {
        return tags;
    }

    public void setTags(ArrayList<PostTags> tags)
    {
        this.setTags(tags);
    }
}
