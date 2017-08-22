package com.gillyweed.android.asklah;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.Comment;
import com.gillyweed.android.asklah.data.model.PostList;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

// Specify custom ViewHolder which gives access to our views
public class CommentAdapter extends ArrayAdapter<Comment> {
    public static final String MyPref = "MyPrefs";
    TextView voteTextView = null;
    TextView commenterTextView = null;
    TextView commentDescriptionTextView = null;
    TextView commentDateTextView = null;
    String currentUserRole = "";
    String currentNustId = "";
    TextView voteTextBtn;
    ApiClient apiClient = null;
    Retrofit retrofit = null;
    ApiInterface apiService = null;
    Comment comment;
    private String TAG = "comment adapter";
    private Context mContext;
    private ArrayList<Comment> mCommentsList;

    public CommentAdapter(Context context, ArrayList<Comment> commentsList) {
        super(context, 0, commentsList);
        mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //check where get the correct item based on position
        comment = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_comment, parent, false);

        }

        apiClient = new ApiClient();

        retrofit = apiClient.getClient();

        apiService = retrofit.create(ApiInterface.class);

        voteTextView = (TextView) convertView.findViewById(R.id.vote_text_view);

        commenterTextView = (TextView) convertView.findViewById(R.id.commenter_text_view);

        commentDescriptionTextView = (TextView) convertView.findViewById(R.id.comment_description_text_view);

        commentDateTextView = (TextView) convertView.findViewById(R.id.comment_date_text_view);

        voteTextBtn = (TextView)convertView.findViewById(R.id.comment_vote);

        voteTextView.setText(Integer.toString(comment.getVote()));

        currentUserRole = getContext().getSharedPreferences(MyPref, Context.MODE_PRIVATE).getString("userRole", "");

        currentNustId = getContext().getSharedPreferences(MyPref, Context.MODE_PRIVATE).getString("userNusId", "");

        if(comment.getCommenter().getNusId().equalsIgnoreCase(currentNustId))
        {
            commenterTextView.setText("I say...");
        }
        else
        {
            if(currentUserRole.equalsIgnoreCase("student"))
            {
                commenterTextView.setText(comment.getCommenter().getUsername() + " says...");
            }
            else
            {
                commenterTextView.setText(comment.getCommenter().getName() + " says...");
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

        if(comment.getVoted() == 1)
        {
            voteTextBtn.setTextColor(Color.GREEN);
        }

        if(comment.getBestAnswer() == 1)
        {
            voteTextBtn.setText(R.string.pin_button);
        }

//        voteBtn.setOnClickListener(voteClickListener);
//        voteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                Call<ResponseBody> call = apiService.upvoteDownvoteComment(mContext.getSharedPreferences(MyPref, Context.MODE_PRIVATE).getString("access_token", ""), comment.getCommentId());
//
//                call.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                        Log.i(TAG, "item position: " + position);
//
//                        Log.i(TAG, "item id: " + comment.getCommentId());
//
//                        int responseCode = response.code();
//
//                        if(response.isSuccessful())
//                        {
//                            if(comment.getVoted() == 0)
//                            {
//                                comment.setVote(comment.getVote() + 1);
//                                comment.setVoted(1);
//                                voteBtn.setBackgroundResource(R.drawable.ic_star_black_16dp);
//                                Toast.makeText(mContext, "item position: " + position, Toast.LENGTH_LONG).show();
//                                Toast.makeText(mContext, "item id: " + comment.getCommentId(), Toast.LENGTH_LONG).show();
//                            }
//                            else
//                            {
//                                comment.setVote(comment.getVote() - 1);
//                                comment.setVoted(0);
//                                voteBtn.setBackgroundResource(R.drawable.ic_star_white);
//                                Toast.makeText(mContext, "item position: " + position, Toast.LENGTH_LONG).show();
//                                Toast.makeText(mContext, "item id: " + comment.getCommentId(), Toast.LENGTH_LONG).show();
//                            }

//                            voteTextView.setText(comment.getVote() + "");
//                        }
//                        else
//                        {
//                            switch (responseCode)
//                            {
//                                case 404:
//                                    Toast.makeText(mContext, "Comment cannot be found from the database", Toast.LENGTH_LONG).show();
//                                    break;
//                                default:
//                                    Toast.makeText(mContext, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
//                                    break;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        if(call.isCanceled())
//                        {
//                            Log.e(TAG, "request was aborted");
//                        }
//                        else
//                        {
//                            Log.e(TAG, t.getMessage());
//                        }
//                        Log.i(TAG, "response code 3: " + t.getMessage());
//                    }
//                });
//            }
//        });

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

//    private View.OnClickListener voteClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            View parentRow = (View) v.getParent();
//
//            ListView listView = (ListView) parentRow.getParent();
//
//            int position = listView.getPositionForView(parentRow);
//
//            if(comment.getVoted() == 0)
//            {
//                comment.setVote(comment.getVote() + 1);
//                comment.setVoted(1);
//                voteBtn.setBackgroundResource(R.drawable.ic_star_black_16dp);
//                Toast.makeText(mContext, "item position: " + position, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, "item id: " + comment.getCommentId(), Toast.LENGTH_LONG).show();
//            }
//            else
//            {
//                comment.setVote(comment.getVote() - 1);
//                comment.setVoted(0);
//                voteBtn.setBackgroundResource(R.drawable.ic_star_white);
//                Toast.makeText(mContext, "item position: " + position, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, "item id: " + comment.getCommentId(), Toast.LENGTH_LONG).show();
//            }
//        }
//    };

}
