package com.gillyweed.android.asklah;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gillyweed.android.asklah.data.model.PostList;
import com.gillyweed.android.asklah.data.model.PostTags;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

public class SubscribedQnAdapter extends ArrayAdapter<PostList> {

    public SubscribedQnAdapter(Context context, ArrayList<PostList> postLists) {
        super(context, 0, postLists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the model item for this position
        PostList post = getItem(position);

        // Check if an existing view is being reused, otheriwse inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_subscribed_question, parent, false);
        }

        // Look up view for question title
        TextView title = (TextView) convertView.findViewById(R.id.question_title);

        // Set question title
        title.setText(post.getTitle());

        // Look up view for question description
        TextView description = (TextView) convertView.findViewById(R.id.question_description);

        // Set question description
        description.setText(post.getDescription());

        // Look up view for tags
        TextView tags = (TextView) convertView.findViewById(R.id.question_tags);

        // String of question tags
        ArrayList<PostTags> questionTagsList = post.getTags().getTags();

        // Concatenate into a string
        String questionTagsString = "";
        for (int i = 0; i < questionTagsList.size(); i++) {
            questionTagsString = questionTagsString + " " + questionTagsList.get(i);
        }

        // Set tags
        tags.setText(questionTagsString);

        // return the completed view to render on the screen
        return convertView;
    }
}
