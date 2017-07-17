package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Envy 15 on 16/7/2017.
 */

public class PostList {
    @SerializedName("posts")
    PostOverview post;

    public PostOverview getPost()
    {
        return post;
    }
}
