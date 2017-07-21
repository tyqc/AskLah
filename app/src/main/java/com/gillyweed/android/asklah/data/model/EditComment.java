package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 21/7/2017.
 */

public class EditComment {

    @SerializedName("comment_id")
    private int commentId;

    @SerializedName("description")
    private String description;

    @SerializedName("image_link")
    private String imageLink;

    public void setCommentId(int commentId)
    {
        this.commentId = commentId;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setImageLink(String imageLink)
    {
        this.imageLink = imageLink;
    }
}
