package com.gillyweed.android.asklah.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 28/6/2017.
 */

public class SubscriptionTag implements Parcelable {

    @SerializedName("tag_id")
    private int tagId;

    @SerializedName("last_visit")
    private String lastVisit;

    @SerializedName("tag")
    private Tag tag;

    protected SubscriptionTag(Parcel in) {
        tagId = in.readInt();
        lastVisit = in.readString();
    }

    public static final Creator<SubscriptionTag> CREATOR = new Creator<SubscriptionTag>() {
        @Override
        public SubscriptionTag createFromParcel(Parcel in) {
            return new SubscriptionTag(in);
        }

        @Override
        public SubscriptionTag[] newArray(int size) {
            return new SubscriptionTag[size];
        }
    };

    public int getTagId()
    {
        return this.tagId;
    }

    public String getLastVisit()
    {
        return lastVisit;
    }

    public Tag getTag()
    {
        return tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tagId);
        dest.writeString(lastVisit);
    }
}
