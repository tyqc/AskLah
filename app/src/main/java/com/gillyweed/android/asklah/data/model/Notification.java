package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 23/8/2017.
 */

public class Notification {
    @SerializedName("id")
    private int id;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("expired_date")
    private String expiredDate;

    @SerializedName("read")
    private int read;

    @SerializedName("notification_type")
    private int notificationType;

    @SerializedName("comment_id")
    private int commentId;

    @SerializedName("post_id")
    private int postId;

    @SerializedName("tag_id")
    private int tagId;

    @SerializedName("post_owner_nus_id")
    private String postOwnerNusId;

    public int getId()
    {
        return this.id;
    }

    public String getCreatedDate()
    {
        return this.createdDate;
    }

    public int getRead()
    {
        return this.read;
    }

    public int getNotificationType()
    {
        return this.notificationType;
    }

    public int getCommentId()
    {
        return this.commentId;
    }

    public int getPostId()
    {
        return this.postId;
    }

    public int getTagId()
    {
        return this.tagId;
    }

    public String getPostOwnerNusId()
    {
        return this.postOwnerNusId;
    }

    public void setRead(int status)
    {
        this.read = status;
    }
}
