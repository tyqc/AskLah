package com.gillyweed.android.asklah;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

// Specify custom ViewHolder which gives access to our views
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context mContext;
    private List<Comment> mCommentsList;

    public CommentAdapter(Context context, List<Comment> commentsList) {
        mContext = context;
        mCommentsList = commentsList;
    }

    // Easy access to the context object in Recycler View
    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View commentView = inflater.inflate(R.layout.recycler_item_thread, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(commentView);
        return viewHolder;
    }

    // Involves populating model into the item through holder
    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        // Get the model model based on position
        Comment comment = mCommentsList.get(position);

        // Set item views based on your views and model model
        TextView messageTextView = holder.commentTextView;
        messageTextView.setText(comment.getMessage());
        TextView userTextView = holder.commentOpTextView;
        userTextView.setText(comment.getUser());

    }

    // Returns total count of items in the list
    @Override
    public int getItemCount() {
        return mCommentsList.size();
    }

    // Provide a direct reference to each of the views within a model item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView commentTextView;
        public TextView commentOpTextView;

        // Constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be
            // used to access the context from any ViewHolder instance
            super(itemView);

            commentTextView = (TextView) itemView.findViewById(R.id.comment);
            commentOpTextView = (TextView) itemView.findViewById(R.id.user);
        }

    }
}
