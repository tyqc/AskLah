package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 19/7/2017.
 */

public class AddComment {
    @SerializedName("description")
    private String description;

    @SerializedName("reply_to_id")
    private String replyToId;

    @SerializedName("post_id")
    private int postId;

    @SerializedName("image_link")
    private String imageLink;

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setReplyToId(String replyToId)
    {
        this.replyToId = replyToId;
    }

    public void setPostId(int postId)
    {
        this.postId = postId;
    }

    public void setImageLink(String imageLink)
    {
        this.imageLink = imageLink;
    }
}
