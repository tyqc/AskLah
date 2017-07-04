package com.gillyweed.android.asklah;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Envy 15 on 25/6/2017.
 */

public class ChangeUsernameDialogFragment extends DialogFragment {

    public static final String MyPref = "MyPrefs";
    private static final String TAG = "changeUsername";
    SharedPreferences sharedPreferences;
    EditText new_usernameText;

//    public interface ChangeUsernameDialogListener
//    {
//        void onFinishEditDialog(String inputText);
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_username, null);

        new_usernameText = (EditText) view.findViewById(R.id.new_usernameText);

        sharedPreferences = getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        ApiClient apiClient = new ApiClient();

        Retrofit retrofit = apiClient.getClient();

        final ApiInterface apiService = retrofit.create(ApiInterface.class);

        final AccessToken currentUserToken = getActivity().getIntent().getParcelableExtra("accessToken");

        final User currentUser = getActivity().getIntent().getParcelableExtra("user");

        builder.setView(inflater.inflate(R.layout.dialog_change_username, null))
                .setPositiveButton("Save", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        String newUsername = new_usernameText.getText().toString();

                        Log.i(TAG, "new username: " + newUsername);
//
//                        currentUser.setUsername(newUsername);
//
//                        Call<User> call = apiService.update(currentUserToken.getToken(),currentUser);
//
//                        call.enqueue(new Callback<User>() {
//                            @Override
//                            public void onResponse(Call<User> call, Response<User> response) {
//                                int responseCode = response.code();
//
//                                if(response.isSuccessful())
//                                {
//                                    if(responseCode == 200)
//                                    {
//                                        changeUsername(response);
//                                        Log.i(TAG, "why not work!!!");
//                                    }
//
//                                }
//                                else
//                                {
//                                    Log.i(TAG, "error code: " + response.errorBody().toString());
//                                    Log.i(TAG, "error code: " + response.code());
//                                    switch (responseCode)
//                                    {
//                                        case 400:
//                                            Toast.makeText(getActivity(), "Username field cannot be empty :(", Toast.LENGTH_LONG).show();
//                                            break;
//                                        case 404:
//                                            Toast.makeText(getActivity(), "Your data cannot be found in database", Toast.LENGTH_LONG).show();
//                                            break;
//                                        case 409:
//                                            Toast.makeText(getActivity(), "Your new unsername has been taken by others :(", Toast.LENGTH_LONG).show();
//                                            break;
//                                        default:
//                                            Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
//                                            break;
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<User> call, Throwable t) {
//                                if(call.isCanceled())
//                                {
//                                    Log.e(TAG, "request was aborted");
//                                }
//                                else
//                                {
//                                    Log.e(TAG, t.getMessage());
//                                }
//                                Log.i(TAG, "response code 3: " + t.getMessage());
//                            }
//                        });


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                   public void onClick(DialogInterface dialog, int id)
                   {
                       ChangeUsernameDialogFragment.this.getDialog().cancel();
                   }
                });

        return builder.create();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanaceState)
    {
        new_usernameText = (EditText) view.findViewById(R.id.new_usernameText);

        new_usernameText.requestFocus();

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

//    public void sendBackResult()
//    {
//        ChangeUsernameDialogListener listener = (ChangeUsernameDialogListener) getTargetFragment();
//
//        listener.onFinishEditDialog(new_usernameText.getText().toString());
//
//        dismiss();
//    }

    public void changeUsername(Response response)
    {
        User user = (User) response.body();

        getActivity().getIntent().putExtra("user", user);
    }


}
