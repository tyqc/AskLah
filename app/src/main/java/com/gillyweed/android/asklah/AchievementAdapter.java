package com.gillyweed.android.asklah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AchievementAdapter extends ArrayAdapter<Achievement> {

    public AchievementAdapter(Context context, ArrayList<Achievement> achievementsList) {
        super(context, 0, achievementsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the model item for this position
        Achievement achievement = getItem(position);

        // Check if an existing view is being reused, else inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_achievement, parent, false);
        }

        // Lookup view for Achievement Name
        TextView achievementName = (TextView) convertView.findViewById(R.id.achievement_name);

        // Set this text on the Achievement TextView
        achievementName.setText(achievement.getAchievementName());

        // Lookup view for whether achievement has been achieved
        ImageView achievementAttained = (ImageView) convertView.findViewById(R.id.checkmark);

        // Set this image on the Achievement ImageView
        if (achievement.getAchieved()) {
            achievementAttained.setImageResource(R.drawable.ic_done_black_24dp);
        }

        // Return the completed view to render on the screen
        return convertView;
    }
}
