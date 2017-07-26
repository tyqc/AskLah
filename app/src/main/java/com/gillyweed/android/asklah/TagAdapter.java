package com.gillyweed.android.asklah;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gillyweed.android.asklah.data.model.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Envy 15 on 24/7/2017.
 */

public class TagAdapter extends ArrayAdapter<Tag> {

    private String TAG = "tag adapter";

    TextView tagNameText;

    public TagAdapter(Context context, ArrayList<Tag> tagArrayList) {
        super(context, 0, tagArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Tag tag = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_tags, parent, false);

        }

        tagNameText = (TextView) convertView.findViewById(R.id.tag_nameTextView);

        tagNameText.setText(tag.getTagName());

        return convertView;
    }
}