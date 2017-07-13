package com.gillyweed.android.asklah.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 7/7/2017.
 */

public class TagPostOwner implements Parcelable {

    @SerializedName("nus_id")
    private String nusId;

    @SerializedName("username")
    private String username;

    @SerializedName("name")
    private String name;

    protected TagPostOwner(Parcel in) {
        nusId = in.readString();
        username = in.readString();
        name = in.readString();
    }

    public static final Creator<TagPostOwner> CREATOR = new Creator<TagPostOwner>() {
        @Override
        public TagPostOwner createFromParcel(Parcel in) {
            return new TagPostOwner(in);
        }

        @Override
        public TagPostOwner[] newArray(int size) {
            return new TagPostOwner[size];
        }
    };

    public String getNusId()
    {
        return nusId;
    }

    public String getUsername()
    {
        return username;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nusId);
        dest.writeString(username);
        dest.writeString(name);
    }
}
