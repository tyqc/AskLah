package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Envy 15 on 13/7/2017.
 */

public class TagPostList {

    @SerializedName("tag_post")
    ArrayList<PostList> postList;

    public ArrayList<PostList> getPostList()
    {
        return postList;
    }
}
