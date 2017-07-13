package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Envy 15 on 13/7/2017.
 */

public class AddPost {
    @SerializedName("post_id")
    private int postId;

    @SerializedName("title")
    private String postTitle;

    @SerializedName("description")
    private String postDescription;

    @SerializedName("image_link")
    private String imgLink;

    @SerializedName("tags")
    private ArrayList<PostTags> postTagsArrayList;

    @SerializedName("tag_id")
    private ArrayList<Integer> tagIds;

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

    public ArrayList<PostTags> getPostTagsArrayList()
    {
        return postTagsArrayList;
    }

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

    public void setTagIds(ArrayList<Integer> tagIds)
    {
        this.tagIds = tagIds;
    }

}
