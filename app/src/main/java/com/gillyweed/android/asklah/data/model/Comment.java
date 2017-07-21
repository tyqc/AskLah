package com.gillyweed.android.asklah.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Envy 15 on 18/7/2017.
 */

public class Comment implements Parcelable {
    @SerializedName("comment_id")
    private int commentId;

    @SerializedName("description")
    private String description;

    @SerializedName("updated_date")
    private DateObj updatedDate;

    @SerializedName("vote")
    private int vote;

    @SerializedName("best_answer")
    private int bestAnswer;

    @SerializedName("post_id")
    private int postId;

    @SerializedName("image_link")
    private String imageLink;

    @SerializedName("created_date")
    private DateObj createdDate;

    @SerializedName("commenter")
    private CommentUser commenter;

    @SerializedName("commentTo")
    private CommentUser commentTo;

    protected Comment(Parcel in) {
        commentId = in.readInt();
        description = in.readString();
        updatedDate = in.readParcelable(DateObj.class.getClassLoader());
        vote = in.readInt();
        bestAnswer = in.readInt();
        postId = in.readInt();
        imageLink = in.readString();
        createdDate = in.readParcelable(DateObj.class.getClassLoader());
        commenter = in.readParcelable(CommentUser.class.getClassLoader());
        commentTo = in.readParcelable(CommentUser.class.getClassLoader());
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(commentId);
        dest.writeString(description);
        dest.writeParcelable(updatedDate, flags);
        dest.writeInt(vote);
        dest.writeInt(bestAnswer);
        dest.writeInt(postId);
        dest.writeString(imageLink);
        dest.writeParcelable(createdDate, flags);
        dest.writeParcelable(commenter, flags);
        dest.writeParcelable(commentTo, flags);
    }

    public int getCommentId()
    {
        return commentId;
    }

    public String getDescription()
    {
        return description;
    }

    public DateObj getUpdatedDate()
    {
        return updatedDate;
    }

    public int getVote()
    {
        return vote;
    }

    public int getBestAnswer()
    {
        return bestAnswer;
    }

    public int getPostId()
    {
        return postId;
    }

    public String getImageLink()
    {
        return imageLink;
    }

    public DateObj getCreatedDate()
    {
        return createdDate;
    }

    public CommentUser getCommenter()
    {
        return commenter;
    }

    public CommentUser getCommentTo()
    {
        return commentTo;
    }
}
