package com.gillyweed.android.asklah;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.*;
import com.gillyweed.android.asklah.data.model.Achievement;
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
    EditText newUsernameText;
    TextView questionNoText;
    TextView commentNoText;
    TextView bestAnswerNoText;
    TextView pointText;
    AccessToken currentUserToken;
    User currentUser;
    ApiInterface apiService;
    ApiClient apiClient;
    Retrofit retrofit;

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

        questionNoText = (TextView)rootView.findViewById(R.id.question_no_text);

        commentNoText = (TextView)rootView.findViewById(R.id.comment_no_text);

        bestAnswerNoText = (TextView)rootView.findViewById(R.id.best_answer_no_text);

        pointText = (TextView)rootView.findViewById(R.id.point_text);

        GridView profileList = (GridView) rootView.findViewById(R.id.profile_grid_view);

        String[] listItemArray = getResources().getStringArray(R.array.profileArray);

        ArrayAdapter<String> listItemAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_profile, listItemArray);

        profileList.setAdapter(listItemAdapter);

        apiClient = new ApiClient();

        retrofit = apiClient.getClient();

        apiService = retrofit.create(ApiInterface.class);

        currentUserToken = getActivity().getIntent().getParcelableExtra("accessToken");

        currentUser = getActivity().getIntent().getParcelableExtra("user");

        getAchievement();

        usernameTextView.setText(currentUser.getUsername());

        profileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:

                        showChangeUsernameDialog();
                        break;

                    case 1:
                        Call<ResponseBody> call = apiService.logout(currentUserToken.getToken());
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                int responseCode = response.code();

                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getActivity(), "See you soon!", Toast.LENGTH_LONG).show();

                                    SharedPreferences preferences = getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.clear();
                                    editor.commit();

                                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);

                                    loginIntent.removeExtra("user");

                                    loginIntent.removeExtra("accessToken");

                                    startActivity(loginIntent);

                                    getActivity().finish();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "An error has occurred. Please try again later.", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                if(call.isCanceled())
                                {
                                    Toast.makeText(getActivity(), "Login failed, request has been canceled", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                                }
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


    public void showChangeUsernameDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_change_username, null);
        newUsernameText = (EditText)view.findViewById(R.id.new_usernameText);
        builder.setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String newUsername = newUsernameText.getText().toString();
                        if(newUsername != "")
                        {
                            updateUsernameAPI(newUsername);
                            updateUsername(newUsername);
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Please type a new username", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateUsernameAPI(String newUsername)
    {
        User currentUser = getActivity().getIntent().getParcelableExtra("user");
        currentUser.setUsername(newUsername);

        Call<User> call = apiService.update(currentUserToken.getToken(),currentUser);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int responseCode = response.code();

                if(response.isSuccessful())
                {
                    if(responseCode == 200)
                    {
                        Toast.makeText(getActivity(), "Username has been updated!", Toast.LENGTH_LONG).show();

                        User updatedUser = response.body();

                        getActivity().getIntent().putExtra("user", updatedUser);
                    }

                }
                else
                {
                    switch (responseCode)
                    {
                        case 400:
                            Toast.makeText(getActivity(), "Username field cannot be empty.", Toast.LENGTH_LONG).show();
                            break;
                        case 404:
                            Toast.makeText(getActivity(), "Your data cannot be found.", Toast.LENGTH_LONG).show();
                            break;
                        case 409:
                            Toast.makeText(getActivity(), "This username is unavailable.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(getActivity(), "An error has occurred. Please try again later.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if(call.isCanceled())
                {
                    Toast.makeText(getActivity(), "Login failed, request has been canceled", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void updateUsername(String newUsername)
    {
        usernameTextView.setText(newUsername);
    }

    public void getAchievement()
    {
        Call<com.gillyweed.android.asklah.data.model.Achievement> call = apiService.getAchievement(currentUserToken.getToken());

        call.enqueue(new Callback<Achievement>() {
            @Override
            public void onResponse(Call<Achievement> call, Response<Achievement> response) {
                int responseCode = response.code();

                if(response.isSuccessful())
                {
                    if(responseCode == 200)
                    {
                        int question = response.body().getQuestionNo();

                        int comment = response.body().getCommentNo();

                        int answer = response.body().getAnswerNo();

                        int point = response.body().getPoints();

                        questionNoText.setText(String.valueOf(question));

                        commentNoText.setText(String.valueOf(comment));

                        bestAnswerNoText.setText(String.valueOf(answer));

                        pointText.setText(String.valueOf(point));
                    }

                }
                else
                {
                    switch (responseCode)
                    {
                        case 400:
                            Toast.makeText(getActivity(), "Username field cannot be empty.", Toast.LENGTH_LONG).show();
                            break;
                        case 404:
                            Toast.makeText(getActivity(), "Your data cannot be found.", Toast.LENGTH_LONG).show();
                            break;
                        case 409:
                            Toast.makeText(getActivity(), "This username is unavailable.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(getActivity(), "An error has occurred. Please try again later.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Achievement> call, Throwable t) {
                if(call.isCanceled())
                {
                    Toast.makeText(getActivity(), "Login failed, request has been canceled", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
