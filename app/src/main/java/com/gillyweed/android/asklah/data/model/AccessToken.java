package com.gillyweed.android.asklah.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

/**
 * Created by Envy 15 on 28/6/2017.
 */

public class AccessToken implements Parcelable{
    @SerializedName("token")
    private String token;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("expired_date")
    private String expiredDate;

    public AccessToken()
    {

    }

    protected AccessToken(Parcel in) {
        token = in.readString();
        createdDate = in.readString();
        expiredDate = in.readString();
    }

    public static final Creator<AccessToken> CREATOR = new Creator<AccessToken>() {
        @Override
        public AccessToken createFromParcel(Parcel in) {
            return new AccessToken(in);
        }

        @Override
        public AccessToken[] newArray(int size) {
            return new AccessToken[size];
        }
    };

    public String getToken()
    {
        return token;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(createdDate);
        dest.writeString(expiredDate);
    }
}
