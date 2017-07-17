package com.gillyweed.android.asklah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gillyweed.android.asklah.data.model.PostList;
import com.gillyweed.android.asklah.data.model.PostOverview;
import com.gillyweed.android.asklah.data.model.PostTags;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.media.CamcorderProfile.get;

public class SubscribedQnAdapter extends ArrayAdapter<PostList> {

    public SubscribedQnAdapter(Context context, ArrayList<PostList> postList) {
        super(context, 0, postList);
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
        title.setText(post.getPost().getTitle());

        // Look up view for question description
        TextView description = (TextView) convertView.findViewById(R.id.question_description);

        // Set question description
        description.setText(post.getPost().getDescription());

        // Look up view for tags
        TextView tags = (TextView) convertView.findViewById(R.id.question_tags);

        // String of question tags
        ArrayList<PostTags> questionTagsList = post.getPost().getTags().getTags();

        // Concatenate into a string
        String questionTagsString = "";
        for (int i = 0; i < questionTagsList.size(); i++) {
            questionTagsString += questionTagsList.get(i).getTagName() + " ";
        }

        // Set tags
        tags.setText(questionTagsString);

        TextView dateText = (TextView) convertView.findViewById(R.id.post_date);

//        Date date = null;
//        try {
//            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(post.getPost().getDateUpdated().getDate());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        DateTime updatedDate = new DateTime(date);
//
//        dateText.setText(updatedDate.toString("dd/MM/yyyy HH:mm aa"));

        dateText.setText(ConvertDateTime.convertTime(post.getPost().getDateUpdated().getDate()));

        // return the completed view to render on the screen
        return convertView;
    }
}
