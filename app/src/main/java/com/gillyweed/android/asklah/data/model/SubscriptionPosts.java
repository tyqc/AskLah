package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Envy 15 on 22/7/2017.
 */

public class SubscriptionPosts {
    @SerializedName("subscription_post")
    private ArrayList<PostList> subscriptionPosts;

    public ArrayList<PostList> getSubscriptionPosts()
    {
        return subscriptionPosts;
    }
}
