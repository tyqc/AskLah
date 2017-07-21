package com.gillyweed.android.asklah;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gillyweed.android.asklah.data.model.Comment;
import com.gillyweed.android.asklah.data.model.PostList;
import com.gillyweed.android.asklah.data.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Specify custom ViewHolder which gives access to our views
public class CommentAdapter extends ArrayAdapter<Comment> {

    private Context mContext;
    private ArrayList<Comment> mCommentsList;
    TextView voteTextView = null;
    TextView commenterTextView = null;
    TextView commentDescriptionTextView = null;
    TextView commentDateTextView = null;
    String currentUserRole = "";
    String currentNustId = "";


    public static final String MyPref = "MyPrefs";

    public CommentAdapter(Context context, ArrayList<Comment> commentsList) {
        super(context, 0, commentsList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Comment comment = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_comment, parent, false);
        }



        voteTextView = (TextView) convertView.findViewById(R.id.vote_text_view);

        commenterTextView = (TextView) convertView.findViewById(R.id.commenter_text_view);

        commentDescriptionTextView = (TextView) convertView.findViewById(R.id.comment_description_text_view);

        commentDateTextView = (TextView) convertView.findViewById(R.id.comment_date_text_view);

        voteTextView.setText(Integer.toString(comment.getVote()));

        currentUserRole = getContext().getSharedPreferences(MyPref, Context.MODE_PRIVATE).getString("userRole", "");

        currentNustId = getContext().getSharedPreferences(MyPref, Context.MODE_PRIVATE).getString("userNusId", "");

        if(comment.getCommenter().getNusId().equalsIgnoreCase(currentNustId))
        {
            commenterTextView.setText("Me :");
        }
        else
        {
            if(currentUserRole.equalsIgnoreCase("student"))
            {
                commenterTextView.setText(comment.getCommenter().getUsername() + " :");
            }
            else
            {
                commenterTextView.setText(comment.getCommenter().getName() + " :");
            }
        }

        commentDescriptionTextView.setText(comment.getDescription());

        Date addedDate = ConvertDateTime.convertToDate(comment.getCreatedDate().getDate());

        Date updatedDate = ConvertDateTime.convertToDate(comment.getUpdatedDate().getDate());

        String dateTextString = "";

        if(updatedDate.compareTo(addedDate) > 0)
        {
            dateTextString = ConvertDateTime.convertTime(comment.getUpdatedDate().getDate()) + " | Edited";
        }
        else
        {
            dateTextString = ConvertDateTime.convertTime(comment.getUpdatedDate().getDate());
        }

        if(comment.getCommentTo() != null)
        {
            if(currentUserRole != "student")
            {
                dateTextString += " | @" + comment.getCommentTo().getName();
            }
            else
            {
                dateTextString += " | @" + comment.getCommentTo().getUsername();
            }
        }

        commentDateTextView.setText(dateTextString);

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).getCommenter().getNusId().equalsIgnoreCase(currentNustId))
        {
            return 0;
        }
        return 1;
    }

    //    // Easy access to the context object in Recycler View
//    private Context getContext() {
//        return mContext;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        // Inflate the custom layout
//        View commentView = inflater.inflate(R.layout.list_item_comment, parent, false);
//
//        // Return a new holder instance
//        ViewHolder viewHolder = new ViewHolder(commentView);
//        return viewHolder;
//    }
//
//    // Involves populating model into the item through holder
//    @Override
//    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
//        // Get the model model based on position
//        Comment comment = mCommentsList.get(position);
//
//        // Set item views based on your views and model model
//        TextView messageTextView = holder.commentTextView;
//        messageTextView.setText(comment.getMessage());
//        TextView userTextView = holder.commentOpTextView;
//        userTextView.setText(comment.getUser());
//
//    }
//
//    // Returns total count of items in the list
//    @Override
//    public int getItemCount() {
//        return mCommentsList.size();
//    }
//
//    // Provide a direct reference to each of the views within a model item
//    // Used to cache the views within the item layout for fast access
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        public TextView commentTextView;
//        public TextView commentOpTextView;
//
//        // Constructor that accepts the entire item row
//        // and does the view lookups to find each subview
//        public ViewHolder(View itemView) {
//            // Stores the itemView in a public final member variable that can be
//            // used to access the context from any ViewHolder instance
//            super(itemView);
//
//            commentTextView = (TextView) itemView.findViewById(R.id.comment);
//            commentOpTextView = (TextView) itemView.findViewById(R.id.user);
//        }
//
//    }
}
