package com.gillyweed.android.asklah.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 13/7/2017.
 */

public class DateObj implements Parcelable{

    @SerializedName("date")
    private String date;

    @SerializedName("timezone_type")
    private int timezoneType;

    @SerializedName("timezone")
    private String timezone;

    protected DateObj(Parcel in) {
        date = in.readString();
        timezoneType = in.readInt();
        timezone = in.readString();
    }

    public static final Creator<DateObj> CREATOR = new Creator<DateObj>() {
        @Override
        public DateObj createFromParcel(Parcel in) {
            return new DateObj(in);
        }

        @Override
        public DateObj[] newArray(int size) {
            return new DateObj[size];
        }
    };

    public String getDate()
    {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeInt(timezoneType);
        dest.writeString(timezone);
    }
}
