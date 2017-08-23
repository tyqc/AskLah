package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 23/8/2017.
 */

public class NotificationUpdate {
    @SerializedName("new_notification")
    private int notificationNo;

    public int getNotificationNo()
    {
        return this.notificationNo;
    }
}
