package com.gillyweed.android.asklah.rest;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gillyweed.android.asklah.ConvertDateTime;
import com.gillyweed.android.asklah.R;
import com.gillyweed.android.asklah.data.model.Comment;
import com.gillyweed.android.asklah.data.model.Notification;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Envy 15 on 23/8/2017.
 */

public class NotificationAdapter extends ArrayAdapter<Notification> {

    private Context mContext;

    public NotificationAdapter(Context context, ArrayList<Notification> notificationArrayListList) {
        super(context, 0, notificationArrayListList);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_notification, parent, false);
        }

        Notification notification = getItem(position);

        TextView notificationDescriptionText = (TextView) convertView.findViewById(R.id.notification_desrip);

        TextView notificationDateText = (TextView) convertView.findViewById(R.id.notification_date);

        LinearLayout listLayout = (LinearLayout) convertView.findViewById(R.id.list_layout);

        String notificationDescrip = "";

        switch(notification.getNotificationType())
        {
            case 1:
                notificationDescrip = "There is an update in one of your subscription";
                break;
            case 2:
                notificationDescrip = "Someone has replied your post!";
                break;
            case 3:
                notificationDescrip = "Someone has mentioned you in his/her comment";
                break;
            case 4:
                notificationDescrip = "Your comment has been pin as the best answer!";
                break;
            default:
                break;
        }

        notificationDescriptionText.setText(notificationDescrip);

        notificationDateText.setText(ConvertDateTime.convertTimeNotification(notification.getCreatedDate()));

        if(notification.getRead() == 0)
        {
            listLayout.setBackgroundColor(Color.parseColor("#FFE7C7"));
        }

        return convertView;
    }
}
