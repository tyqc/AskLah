package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Envy 15 on 13/7/2017.
 */

public class TagArray {
    @SerializedName("tag")
    private ArrayList<TagDescrip> tagArrayList;

    public ArrayList<TagDescrip> getTagArrayList()
    {
        return tagArrayList;
    }
}
