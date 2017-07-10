package com.gillyweed.android.asklah;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.SubscriptionTag;
import com.gillyweed.android.asklah.data.model.SubscriptionTags;
import com.gillyweed.android.asklah.data.model.Tag;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private ArrayList<String> moduleNameList = new ArrayList<String>();

    private ArrayList<SubscriptionTag> subscribedTagList = new ArrayList<SubscriptionTag>();

    private String TAG = "subscription tag";

    User currentUser = null;

    AccessToken currentUserToken = null;

    ApiClient apiClient = null;

    Retrofit retrofit = null;

    ApiInterface apiService = null;

    ArrayAdapter<String> modulesOrMajorsAdapter = null;


    public HomeFragment() {

        apiClient = new ApiClient();

        retrofit = apiClient.getClient();

        apiService = retrofit.create(ApiInterface.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        final GridView homeGridView = (GridView) rootView.findViewById(R.id.home_grid_view);

        registerForContextMenu(homeGridView);

        currentUser = getActivity().getIntent().getParcelableExtra("user");

        currentUserToken = getActivity().getIntent().getParcelableExtra("accessToken");

        moduleNameList = getActivity().getIntent().getStringArrayListExtra("moduleListName");

        subscribedTagList = getActivity().getIntent().getParcelableArrayListExtra("subscribedTagList");

        if(subscribedTagList == null || moduleNameList == null)
        {
            Call<SubscriptionTags> call = apiService.getAllSubscribedTag(currentUserToken.getToken(), currentUser.getNusId());

            call.enqueue(new Callback<SubscriptionTags>() {
                @Override
                public void onResponse(Call<SubscriptionTags> call, Response<SubscriptionTags> response) {
                    int responseCode = response.code();

                    if(response.isSuccessful())
                    {
                        if(responseCode == 200)
                        {
//                        goHomeActivity(response);
                            SubscriptionTags subscriptionTags = response.body();
                            subscribedTagList = subscriptionTags.getSubscriptionTags();
//                            Log.i(TAG, "check if null: " + subscribedTagList);
                            moduleNameList = new ArrayList<String>();
                            getSubscribedTagName();
                            moduleNameList.add("+ Add New Module Tag");
                            getActivity().getIntent().putStringArrayListExtra("moduleListName", moduleNameList);
                            getActivity().getIntent().putParcelableArrayListExtra("subscribedTagList", subscribedTagList);

                            modulesOrMajorsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, moduleNameList);
                            homeGridView.setAdapter(modulesOrMajorsAdapter);
//
//
                            homeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    // Create a new intent to open Modules List Activity
                                    if(position == moduleNameList.size() - 1)
                                    {
                                        showAddTagDialog();
                                    }
                                    else
                                    {
                                        Intent modulesListIntent = new Intent(getActivity(), SubscribedQuestionsActivity.class);
                                        startActivity(modulesListIntent);
                                    }

                                }
                            });
//

                        }

                    }
                    else
                    {
                        switch (responseCode)
                        {
                            case 404:
                                Toast.makeText(getActivity(), "Your data cannot be found in database", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<SubscriptionTags> call, Throwable t) {
                    if(call.isCanceled())
                    {
                        Log.e(TAG, "request was aborted");
                    }
                    else
                    {
                        Log.e(TAG, t.getMessage());
                    }
                }
            });
        }
        else
        {
            modulesOrMajorsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, moduleNameList);
            homeGridView.setAdapter(modulesOrMajorsAdapter);


            homeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Create a new intent to open Modules List Activity
                    if(position == moduleNameList.size() - 1)
                    {
                        showAddTagDialog();
                    }
                    else
                    {
                        Intent modulesListIntent = new Intent(getActivity(), SubscribedQuestionsActivity.class);
                        startActivity(modulesListIntent);
                    }

                }
            });
        }


        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, view, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.module_tag_actions, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId())
        {
            case R.id.infoBtn:
                showTagInfoDialog(info.position);
                break;
            case R.id.editBtn:
                Toast.makeText(getActivity(), "edit " + info.position, Toast.LENGTH_LONG).show();
                break;
            case R.id.deleteBtn:
                Toast.makeText(getActivity(), "delete " + info.position, Toast.LENGTH_LONG).show();
                break;
            case R.id.unsubscribeBtn:
                Toast.makeText(getActivity(), "unsubscribe " + info.position, Toast.LENGTH_LONG).show();
                break;
        }

        return true;
    }
//
//    public void getSubscribedTag(ApiClient apiClient, Retrofit retrofit, ApiInterface apiService, User currentUser, AccessToken currentAccessToken)
//    {
//
//    }

    public void getSubscribedTagName()
    {
        for(int i = 0; i < subscribedTagList.size(); i++)
        {
            Log.i(TAG, subscribedTagList.get(i).getTag().getTagName());
            moduleNameList.add(subscribedTagList.get(i).getTag().getTagName());
        }
    }

    public void showTagInfoDialog(int tagPosition)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_tag_info, null);
        TextView descriptionText = (TextView) view.findViewById(R.id.tag_descriptionTextView);
        TextView tagOwnerText = (TextView) view.findViewById(R.id.created_byText);
        TextView lastUpdateText = (TextView) view.findViewById(R.id.last_updateText);
        TextView subscribeNoText = (TextView) view.findViewById(R.id.subscription_noText);
        TextView tagStatusText = (TextView) view.findViewById(R.id.tag_statusText);

        Tag currentTag = subscribedTagList.get(tagPosition).getTag();

        descriptionText.setText("Description: " + currentTag.getDescription());

        if(currentUser.getRole() != "student")
        {
            tagOwnerText.setText("Created By: " + currentTag.getTagOwner().getName());
        }
        else
        {
            tagOwnerText.setText("Created By: " + currentTag.getTagOwner().getUsername());
        }

        lastUpdateText.setText("Last Updated: " + currentTag.getLastUpdate());

        subscribeNoText.setText("Total Subscription: " + currentTag.getSubscribeNo());

        String status = (currentTag.getTagStatus() == 0) ? "Closed" : "Open";

        tagStatusText.setText("Status: " + status);

        builder.setView(view)
                .setTitle(moduleNameList.get(tagPosition))
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showAddTagDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_tag, null);
        final EditText tagNameText = (EditText) view.findViewById(R.id.tag_nameText);
        final EditText tagDescriptionText = (EditText) view.findViewById(R.id.tag_descriptionText);

        builder.setView(view)
                .setTitle("Add New Tag")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Tag newTag = new Tag();

                        newTag.setTagName(tagNameText.getText().toString());
                        newTag.setDescription(tagDescriptionText.getText().toString());
                        addNewTagApi(newTag);
                        modulesOrMajorsAdapter.notifyDataSetChanged();
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

    public void addNewTagApi(Tag newTag)
    {
        Call<SubscriptionTag> call = apiService.addNewTag(currentUserToken.getToken(), newTag);

        call.enqueue(new Callback<SubscriptionTag>() {
            @Override
            public void onResponse(Call<SubscriptionTag> call, Response<SubscriptionTag> response) {
                int responseCode = response.code();

                if(response.isSuccessful())
                {
                    Toast.makeText(getActivity(), "New tag created!", Toast.LENGTH_LONG).show();

                    addNewTagToArray(response.body());
                }
                else
                {
                    switch (responseCode)
                    {
                        case 400:
                            Toast.makeText(getActivity(), "Tag name field cannot be empty :(", Toast.LENGTH_LONG).show();
                            break;
                        case 409:
                            Toast.makeText(getActivity(), "Tag name must be unique", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<SubscriptionTag> call, Throwable t) {
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
    }

    public void addNewTagToArray(SubscriptionTag newTag)
    {
        subscribedTagList.add(newTag);

        String lastContent = moduleNameList.get(moduleNameList.size() - 1);
        moduleNameList.remove(moduleNameList.size() - 1);
        moduleNameList.add(newTag.getTag().getTagName());
        moduleNameList.add(lastContent);

        modulesOrMajorsAdapter.notifyDataSetChanged();
    }
}
