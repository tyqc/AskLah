package com.gillyweed.android.asklah.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 13/7/2017.
 */

public class GetPost implements Parcelable{
    @SerializedName("post_id")
    private int postId;

    @SerializedName("title")
    private String postTitle;

    @SerializedName("description")
    private String postDescription;

    @SerializedName("date_updated")
    private DateObj dateUpdated;

    @SerializedName("date_added")
    private DateObj dateAdded;

    @SerializedName("image_link")
    private String imgLink;

    @SerializedName("vote")
    private int vote;

    @SerializedName("subscription_no")
    private int subscribeNo;

    @SerializedName("subscribed")
    private boolean subscribed;

    @SerializedName("tags")
    private PostTagArray postTagArray;

    @SerializedName("created_by")
    private TagPostOwner owner;

    @SerializedName("comments")
    private CommentArr commentArr;

    @SerializedName("voted")
    private int voted;

    @SerializedName("best_answer_exist")
    private boolean best_answer_exist;

    protected GetPost(Parcel in) {
        postId = in.readInt();
        postTitle = in.readString();
        postDescription = in.readString();
        imgLink = in.readString();
        vote = in.readInt();
        subscribeNo = in.readInt();
        subscribed = in.readByte() != 0;
        owner = in.readParcelable(TagPostOwner.class.getClassLoader());
        commentArr = in.readParcelable(CommentArr.class.getClassLoader());
        voted = in.readInt();
        best_answer_exist = in.readByte() != 0;
    }

    public static final Creator<GetPost> CREATOR = new Creator<GetPost>() {
        @Override
        public GetPost createFromParcel(Parcel in) {
            return new GetPost(in);
        }

        @Override
        public GetPost[] newArray(int size) {
            return new GetPost[size];
        }
    };

    public void setPostTitle(String postTitle)
    {
        this.postTitle = postTitle;
    }

    public void setPostDescription(String postDescription)
    {
        this.postDescription = postDescription;
    }

    public void setImgLink(String imgLink)
    {
        this.imgLink = imgLink;
    }

    public void setPostTagArray(PostTagArray postTagArray)
    {
        this.postTagArray = postTagArray;
    }

    public int getPostId()
    {
        return postId;
    }

    public String getPostTitle()
    {
        return postTitle;
    }

    public String getPostDescription()
    {
        return postDescription;
    }

    public String getImgLink()
    {
        return imgLink;
    }

    public DateObj getDateUpdated()
    {
        return dateUpdated;
    }

    public DateObj getDateAdded()
    {
        return dateAdded;
    }

    public int getVote()
    {
        return vote;
    }

    public int getSubscribeNo()
    {
        return subscribeNo;
    }

    public boolean getSubscribed()
    {
        return subscribed;
    }

    public PostTagArray getPostTagArray()
    {
        return postTagArray;
    }

    public TagPostOwner getOwner()
    {
        return owner;
    }

    public CommentArr getCommentArr()
    {
        return commentArr;
    }

    public int getVoted()
    {
        return voted;
    }

    public void setVoted(int voted)
    {
        this.voted = voted;
    }

    public void setVote(int vote)
    {
        this.vote = vote;
    }

    public void setBest_answer_exist(boolean best_answer_exist)
    {
        this.best_answer_exist = best_answer_exist;
    }

    public boolean getBest_answer_exist()
    {
        return best_answer_exist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(postId);
        dest.writeString(postTitle);
        dest.writeString(postDescription);
        dest.writeString(imgLink);
        dest.writeInt(vote);
        dest.writeInt(subscribeNo);
        dest.writeByte((byte) (subscribed ? 1 : 0));
        dest.writeParcelable(owner, flags);
        dest.writeParcelable(commentArr, flags);
        dest.writeInt(voted);
        dest.writeByte((byte) (best_answer_exist ? 1 : 0));
    }
}
