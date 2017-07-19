package com.gillyweed.android.asklah.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 18/7/2017.
 */

public class CommentUser implements Parcelable {
    @SerializedName("nus_id")
    private String nusId;

    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private String username;

    @SerializedName("role")
    private String role;

    protected CommentUser(Parcel in) {
        nusId = in.readString();
        name = in.readString();
        username = in.readString();
        role = in.readString();
    }

    public static final Creator<CommentUser> CREATOR = new Creator<CommentUser>() {
        @Override
        public CommentUser createFromParcel(Parcel in) {
            return new CommentUser(in);
        }

        @Override
        public CommentUser[] newArray(int size) {
            return new CommentUser[size];
        }
    };

    public String getNusId()
    {
        return nusId;
    }

    public String getName()
    {
        return name;
    }

    public String getUsername()
    {
        return username;
    }

    public String getRole()
    {
        return role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nusId);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(role);
    }
}
