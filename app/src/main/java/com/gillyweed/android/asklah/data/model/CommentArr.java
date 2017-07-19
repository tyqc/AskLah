package com.gillyweed.android.asklah.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Envy 15 on 18/7/2017.
 */

public class CommentArr implements Parcelable {

    @SerializedName("comments")
    private ArrayList<Comment> commentArrayList;

    protected CommentArr(Parcel in) {
    }

    public static final Creator<CommentArr> CREATOR = new Creator<CommentArr>() {
        @Override
        public CommentArr createFromParcel(Parcel in) {
            return new CommentArr(in);
        }

        @Override
        public CommentArr[] newArray(int size) {
            return new CommentArr[size];
        }
    };

    public ArrayList<Comment> getCommentArrayList()
    {
        return commentArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
