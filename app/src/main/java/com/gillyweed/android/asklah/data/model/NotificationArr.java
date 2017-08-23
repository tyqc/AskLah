package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Envy 15 on 23/8/2017.
 */

public class NotificationArr {
    @SerializedName("notification")
    private ArrayList<Notification> notificationArrayList;

    public ArrayList<Notification> getNotificationArrayList()
    {
        return this.notificationArrayList;
    }
}
