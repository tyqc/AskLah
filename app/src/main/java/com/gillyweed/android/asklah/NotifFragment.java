package com.gillyweed.android.asklah;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.Notification;
import com.gillyweed.android.asklah.data.model.NotificationArr;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;
import com.gillyweed.android.asklah.rest.NotificationAdapter;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;


public class NotifFragment extends Fragment {

    public static final String MyPref = "MyPrefs";

    User currentUser = null;

    AccessToken currentUserToken = null;

    ApiClient apiClient = null;

    Retrofit retrofit = null;

    ApiInterface apiService = null;

    ArrayList<Notification> notificationArrayList;

    ListView notificationListView;

    TextView noNotificationText;

    public NotifFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notif, container, false);

        apiClient = new ApiClient();

        retrofit = apiClient.getClient();

        apiService = retrofit.create(ApiInterface.class);

        currentUser = getActivity().getIntent().getParcelableExtra("user");

        currentUserToken = getActivity().getIntent().getParcelableExtra("accessToken");

        notificationListView = (ListView) rootView.findViewById(R.id.notification_list_view);

        noNotificationText = (TextView) rootView.findViewById(R.id.no_notification_message);

        Call<NotificationArr> call = apiService.getNotification(currentUserToken.getToken());

        call.enqueue(new Callback<NotificationArr>() {
            @Override
            public void onResponse(Call<NotificationArr> call, Response<NotificationArr> response) {
                int responseCode = response.code();

                if(response.isSuccessful())
                {
                    notificationArrayList = response.body().getNotificationArrayList();

                    if(notificationArrayList.size() > 0)
                    {
                        noNotificationText.setVisibility(View.GONE);
                    }

                    final NotificationAdapter notificationAdapter = new NotificationAdapter(getActivity(), notificationArrayList);

                    notificationListView.setAdapter(notificationAdapter);

                    notificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            if(notificationArrayList.get(position).getRead() == 0)
                            {
                                updateNotificationStatus(notificationArrayList.get(position).getId());

                                notificationArrayList.get(position).setRead(1);

                                notificationAdapter.notifyDataSetChanged();
                            }

                            Intent postIntent = new Intent(getActivity(), QuestionThreadActivity.class);

                            postIntent.putExtra("user", currentUser);
                            postIntent.putExtra("accessToken", currentUserToken);
                            postIntent.putExtra("postId", notificationArrayList.get(position).getPostId());
                            postIntent.putExtra("postOwnerNusId",  notificationArrayList.get(position).getPostOwnerNusId());
                            startActivityForResult(postIntent, 2000);
                        }
                    });
                }
                else
                {
                    switch (responseCode)
                    {
                        default:
                            Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationArr> call, Throwable t) {
                if(call.isCanceled())
                {
                    Toast.makeText(getActivity(), "Request has been canceled", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 2000)
        {
            if(resultCode == RESULT_OK)
            {
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        }
    }

    public void updateNotificationStatus(int notificationId)
    {
        Call<ResponseBody> call = apiService.updateNotificationStatus(currentUserToken.getToken(), notificationId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int responseCode = response.code();

                if(response.isSuccessful())
                {

                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(call.isCanceled())
                {
                    Toast.makeText(getActivity(), "Request has been canceled", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
