package com.gillyweed.android.asklah;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AnnouncementAdapter extends ArrayAdapter<Announcement> {

    public AnnouncementAdapter(Context context, ArrayList<Announcement> announcementsList) {
        super(context, 0, announcementsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the model item for this position
        Announcement announcement = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_announcement, parent, false);
        }

        // Lookup view for achievement announcement
        TextView achievementAnnouncement = (TextView) convertView.findViewById(R.id.achievement_announcement);

        // Set name and achievement
        String username = announcement.getUsername();
        String achievement = announcement.getAchievementName();
        String newAnnouncement = username + " just unlocked the achievement " + achievement + "!";
        achievementAnnouncement.setText(newAnnouncement);

        // Lookup view for relative time
        TextView relativeTime = (TextView) convertView.findViewById(R.id.timestamp);
        relativeTime.setText(announcement.getTime());

        return convertView;
    }
}
