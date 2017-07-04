package com.gillyweed.android.asklah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import org.w3c.dom.Text;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileFragment extends Fragment {

    public static final String MyPref = "MyPrefs";
    private static final String TAG = "profile";
    SharedPreferences sharedPreferences;
    TextView usernameTextView;
    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        usernameTextView = (TextView) rootView.findViewById(R.id.username_text_view);
        sharedPreferences = getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        ListView profileList = (ListView) rootView.findViewById(R.id.profile_list_view);

        String[] listItemArray = getResources().getStringArray(R.array.profileArray);

        ArrayAdapter<String> listItemAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItemArray);

        profileList.setAdapter(listItemAdapter);

        ApiClient apiClient = new ApiClient();

        Retrofit retrofit = apiClient.getClient();

        final ApiInterface apiService = retrofit.create(ApiInterface.class);

        final AccessToken currentUserToken = getActivity().getIntent().getParcelableExtra("accessToken");

        updateUsername();

        profileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        DialogFragment newFragment = new ChangeUsernameDialogFragment();
                        newFragment.show(getFragmentManager(), "ChangeUsernameDialogFragment");
                        updateUsername();
                        break;
                    case 1:
                        //Create a new intent to open the {@link AchievementsAcitivty}
                        Intent achievementsIntent = new Intent(getActivity(), AchievementsActivity.class);

                        // Start the new activity
                        startActivity(achievementsIntent);
                        break;
                    case 2:
                        Call<ResponseBody> call = apiService.logout(currentUserToken.getToken());
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                int responseCode = response.code();

                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getActivity(), "See you again!", Toast.LENGTH_LONG).show();

                                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);

                                    loginIntent.removeExtra("user");

                                    loginIntent.removeExtra("accessToken");

                                    startActivity(loginIntent);
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                if(call.isCanceled())
                                {
                                    Log.e(TAG, "request was aborted");
                                }
                                else
                                {
                                    Log.e(TAG, t.getMessage());
                                }
                                Log.i(TAG, "response code 3: " + t.getMessage());
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        });

        return rootView;
    }



//    @Override
//    public void onActivityCreated(Bundle savedInstanceState)
//    {
//        super.onActivityCreated(savedInstanceState);
//
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.profileArray, android.R.layout.simple_list_item_1);
//
//        setListAdapter(adapter);
//    }



    public void updateUsername()
    {
        User currentUser = getActivity().getIntent().getParcelableExtra("user");

        usernameTextView.setText(currentUser.getUsername());
    }
}
