package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 20/8/2017.
 */

public class TagDescrip {
    @SerializedName("tag_name")
    private String tagName;

    @SerializedName("description")
    private String description;

    @SerializedName("last_update")
    private String lastUpdate;

    @SerializedName("subscribe_no")
    private int subscribeNo;

    @SerializedName("tag_status")
    private int tagStatus;

    @SerializedName("created_by")
    private TagPostOwner tagPostOwner;

    @SerializedName("tag_id")
    private int tagId;

    @SerializedName("subscribed")
    private Boolean subscribed;

    public String getTagName()
    {
        return tagName;
    }

    public String getDescription()
    {
        return description;
    }

    public String getLastUpdate()
    {
        return lastUpdate;
    }

    public int getSubscribeNo()
    {
        return subscribeNo;
    }

    public int getTagStatus()
    {
        return tagStatus;
    }

    public TagPostOwner getTagPostOwner()
    {
        return tagPostOwner;
    }

    public int getTagId()
    {
        return tagId;
    }

    public Boolean getSubscribed()
    {
        return subscribed;
    }
}
