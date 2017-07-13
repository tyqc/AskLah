package com.gillyweed.android.asklah.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 13/7/2017.
 */

public class PostTags implements Parcelable{

    @SerializedName("tag_id")
    private int tagId;

    @SerializedName("tag_name")
    private String tagName;

    protected PostTags(Parcel in) {
        tagId = in.readInt();
        tagName = in.readString();
    }

    public static final Creator<PostTags> CREATOR = new Creator<PostTags>() {
        @Override
        public PostTags createFromParcel(Parcel in) {
            return new PostTags(in);
        }

        @Override
        public PostTags[] newArray(int size) {
            return new PostTags[size];
        }
    };

    public int getTagId()
    {
        return tagId;
    }

    public String getTagName()
    {
        return tagName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tagId);
        dest.writeString(tagName);
    }
}
