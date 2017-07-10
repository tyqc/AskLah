package com.gillyweed.android.asklah.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Envy 15 on 28/6/2017.
 */

public class User implements Parcelable{
    @SerializedName("nus_id")
    private  String nusId;

    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("role")
    private String role;

    @SerializedName("access_token")
    private AccessToken accessToken;

//    @SerializedName("subscription_tag")
//    private ArrayList<SubscriptionTag> subscriptionTag;

    public User()
    {

    }

    protected User(Parcel in) {
        nusId = in.readString();
        name = in.readString();
        username = in.readString();
        password = in.readString();
        role = in.readString();
        accessToken = in.readParcelable(AccessToken.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
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

    public void setUsername(String newUsername)
    {
        this.username = newUsername;
    }

    public String getRole()
    {
        return role;
    }

    public void setNusId(String nusId)
    {
        this.nusId = nusId;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public AccessToken getAccessToken()
    {
        return accessToken;
    }

//    public ArrayList<SubscriptionTag> getSubscriptionTag()
//    {
//        return subscriptionTag;
//    }

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

//        accessToken.writeToParcel(dest, flags);
    }
}
