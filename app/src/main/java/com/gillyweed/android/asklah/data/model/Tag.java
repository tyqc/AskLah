package com.gillyweed.android.asklah.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 7/7/2017.
 */

public class Tag implements Parcelable {

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

    public Tag()
    {

    }

    protected Tag(Parcel in) {
        tagId = in.readInt();
        tagName = in.readString();
        description = in.readString();
        lastUpdate = in.readString();
        subscribeNo = in.readInt();
        tagStatus = in.readInt();
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    public void setTagName(String tagName)
    {
        this.tagName = tagName;
    }

    public String getTagName()
    {
        return tagName;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    public void setTagStatus(int tagStatus)
    {
        this.tagStatus = tagStatus;
    }

    public TagPostOwner getTagPostOwner()
    {
        return tagPostOwner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tagId);
        dest.writeString(tagName);
        dest.writeString(description);
        dest.writeString(lastUpdate);
        dest.writeInt(subscribeNo);
        dest.writeInt(tagStatus);
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
