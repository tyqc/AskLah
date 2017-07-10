package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Envy 15 on 7/7/2017.
 */

public class SubscriptionTags {

    @SerializedName("subscription_tag")
    private ArrayList<SubscriptionTag> subscriptionTags;

    public ArrayList<SubscriptionTag> getSubscriptionTags()
    {
        return subscriptionTags;
    }
}
