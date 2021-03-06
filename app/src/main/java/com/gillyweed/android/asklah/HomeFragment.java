package com.gillyweed.android.asklah;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import com.github.clans.fab.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AlertDialogLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.SubscriptionTag;
import com.gillyweed.android.asklah.data.model.SubscriptionTags;
import com.gillyweed.android.asklah.data.model.Tag;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    public static final String MyPref = "MyPrefs";
    User currentUser = null;
    AccessToken currentUserToken = null;
    ApiClient apiClient = null;
    Retrofit retrofit = null;
    ApiInterface apiService = null;
    ArrayAdapter<String> modulesOrMajorsAdapter = null;
    EditText tagNameText;
    EditText tagDescriptionText;
    Switch statusToggle;
    String userRole;
    private ArrayList<String> moduleNameList = new ArrayList<String>();
    private ArrayList<SubscriptionTag> subscribedTagList = new ArrayList<SubscriptionTag>();
    private String TAG = "subscription tag";
    TextView noSubText;
    GridView tagGridView;

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

        userRole = getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE).getString("user_role", "");

        noSubText = (TextView) rootView.findViewById(R.id.no_subscription_text);

        noSubText.setVisibility(View.GONE);

        tagGridView = (GridView) rootView.findViewById(R.id.home_grid_view);

        Call<SubscriptionTags> call = apiService.getAllSubscribedTag(currentUserToken.getToken(), currentUser.getNusId());

        call.enqueue(new Callback<SubscriptionTags>() {
            @Override
            public void onResponse(Call<SubscriptionTags> call, Response<SubscriptionTags> response) {
                int responseCode = response.code();

                if(response.isSuccessful())
                {
                    if(responseCode == 200)
                    {
                        final SubscriptionTags subscriptionTags = response.body();
                        subscribedTagList = subscriptionTags.getSubscriptionTags();

                        if(subscribedTagList.size() > 0)
                        {
                            noSubText.setVisibility(View.GONE);
                        }
                        else
                        {
                            noSubText.setVisibility(View.VISIBLE);
                        }

                        moduleNameList = new ArrayList<String>();
                        getSubscribedTagName();

                        moduleNameList.add("Subscribed Posts");

                        if(!userRole.equalsIgnoreCase("student"))
                        {
                            moduleNameList.add("+ Add New Module Tag");
                            getActivity().getIntent().putStringArrayListExtra("moduleListName", moduleNameList);
                            getActivity().getIntent().putParcelableArrayListExtra("subscribedTagList", subscribedTagList);
                        }


                        modulesOrMajorsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_module, moduleNameList);
                        homeGridView.setAdapter(modulesOrMajorsAdapter);

                        homeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Create a new intent to open Modules List Activity
                                if(moduleNameList.get(position).equalsIgnoreCase("+ Add New Module Tag"))
                                {
                                    showAddTagDialog();

                                }
                                else if(moduleNameList.get(position).equalsIgnoreCase("Subscribed Posts"))
                                {
                                    directToSubscribedPostList();
                                }
                                else
                                {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt("tagId", subscribedTagList.get(position).getTagId());
                                    editor.putString("access_token", currentUserToken.getToken());
                                    editor.putBoolean("tag_subscribed", subscribedTagList.get(position).getTag().getSubscribed());
                                    editor.putString("module_tag", subscribedTagList.get(position).getTag().getTagName());
                                    if(currentUser.getNusId().equals(subscribedTagList.get(position).getTag().getTagPostOwner().getNusId()))
                                    {
                                        editor.putBoolean("owner", true);
                                    }
                                    editor.commit();

                                    Intent postListIntent = new Intent(getActivity(), TagQuestionsActivity.class);

                                    postListIntent.putExtra("user", currentUser);
                                    postListIntent.putExtra("accessToken", currentUserToken);

                                    startActivityForResult(postListIntent, 2);
                                }

                            }
                        });

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
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                currentUser = intent.getParcelableExtra("user");

                currentUserToken = intent.getParcelableExtra("accessToken");
            }
        }
        else if(requestCode == 2)
        {
            if(resultCode == RESULT_OK)
            {
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, view, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        if(info.position < subscribedTagList.size())
        {
            if(!subscribedTagList.get(info.position).getTag().getTagPostOwner().getNusId().equalsIgnoreCase(currentUser.getNusId()))
            {
                getActivity().getMenuInflater().inflate(R.menu.module_tag_actions, menu);
            }
            else
            {
                getActivity().getMenuInflater().inflate(R.menu.module_tag_actions_owner, menu);
            }
        }

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
                showEditTagDialog(info.position);
                break;
            case R.id.deleteBtn:
                deleteTag(info.position);
                break;
            case R.id.unsubscribeBtn:
                unsubscribeTag(info.position);
                break;
        }

        return true;
    }

    public void getSubscribedTagName()
    {
        for(int i = 0; i < subscribedTagList.size(); i++)
        {
            moduleNameList.add(subscribedTagList.get(i).getTag().getTagName());
        }
    }

    public void showTagInfoDialog(int tagPosition)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.MyAlertDialog));
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
            tagOwnerText.setText("Created By: " + currentTag.getTagPostOwner().getName());
        }
        else
        {
            tagOwnerText.setText("Created By: " + currentTag.getTagPostOwner().getUsername());
        }

        lastUpdateText.setText("Last Updated: " + currentTag.getLastUpdate());

        subscribeNoText.setText("Total Subscription: " + currentTag.getSubscribeNo());

        String status = (currentTag.getTagStatus() == 0) ? "Closed" : "Open";

        tagStatusText.setText("Status: " + status);

        builder.setView(view)
                .setTitle(moduleNameList.get(tagPosition))
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.MyAlertDialog));
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_edit_tag, null);
        tagNameText = (EditText) view.findViewById(R.id.tag_nameText);
        tagDescriptionText = (EditText) view.findViewById(R.id.tag_descriptionText);
        statusToggle = (Switch) view.findViewById(R.id.statusToggle);
        statusToggle.setVisibility(View.GONE);

        builder.setView(view)
                .setTitle("Add New Tag")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Tag newTag = new Tag();

                        newTag.setTagName(tagNameText.getText().toString().toUpperCase());
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
                    Toast.makeText(getActivity(), "Request has been canceled", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void addNewTagToArray(SubscriptionTag newTag)
    {
        subscribedTagList.add(newTag);

        moduleNameList.remove(moduleNameList.size() - 1);

        if(!userRole.equalsIgnoreCase("student"))
        {
            moduleNameList.remove(moduleNameList.size() - 1);
        }

        moduleNameList.add(newTag.getTag().getTagName());

        moduleNameList.add("Subscribed Posts");

        if(!userRole.equalsIgnoreCase("student"))
        {
            moduleNameList.add("+ Add New Module Tag");
        }

        modulesOrMajorsAdapter.notifyDataSetChanged();

        if(subscribedTagList.size() > 0)
        {
            noSubText.setVisibility(View.GONE);
        }
    }

    public void showEditTagDialog(final int tagPosition)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.MyAlertDialog));
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_edit_tag, null);
        tagNameText = (EditText) view.findViewById(R.id.tag_nameText);
        tagDescriptionText = (EditText) view.findViewById(R.id.tag_descriptionText);
        statusToggle = (Switch) view.findViewById(R.id.statusToggle);

        final SubscriptionTag getTag = subscribedTagList.get(tagPosition);

        tagNameText.setText(getTag.getTag().getTagName());

        tagDescriptionText.setText(getTag.getTag().getDescription());

        if(getTag.getTag().getTagStatus() == 1)
        {
            statusToggle.setChecked(true);
        }
        else
        {
            statusToggle.setChecked(false);
        }

        builder.setView(view)
                .setTitle("Edit New Tag")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        getTag.getTag().setTagName(tagNameText.getText().toString());

                        getTag.getTag().setDescription(tagDescriptionText.getText().toString());

                        if(statusToggle.isChecked() == true)
                        {
                            getTag.getTag().setTagStatus(1);
                        }
                        else
                        {
                            getTag.getTag().setTagStatus(0);
                        }

                        Call<ResponseBody> call = apiService.editTag(currentUserToken.getToken(), getTag.getTag(), getTag.getTagId());

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                int responseCode = response.code();

                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getActivity(), "Tag info updated!", Toast.LENGTH_LONG).show();

                                    updateModuleNameList(tagPosition);
                                }
                                else
                                {
                                    switch (responseCode)
                                    {
                                        case 400:
                                            Toast.makeText(getActivity(), "Tag name field cannot be empty :(", Toast.LENGTH_LONG).show();
                                            break;
                                        case 401:
                                            Toast.makeText(getActivity(), "Only tag owner can edit the tag", Toast.LENGTH_LONG).show();
                                            break;
                                        case 404:
                                            Toast.makeText(getActivity(), "Tag cannot be found from the database", Toast.LENGTH_LONG).show();
                                            break;
                                        case 409:
                                            Toast.makeText(getActivity(), "Update failed! Tag name must be unique", Toast.LENGTH_LONG).show();
                                            break;
                                        default:
                                            Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                                            break;
                                    }
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

    public void updateModuleNameList(int tagPosition)
    {
        moduleNameList.set(tagPosition, subscribedTagList.get(tagPosition).getTag().getTagName());

        modulesOrMajorsAdapter.notifyDataSetChanged();
    }

    public void deleteTag(final int tagPosition)
    {
        new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.MyAlertDialog))
                .setTitle("Delete Tag")
                .setMessage("Are you sure you want to delete this tag?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Call<ResponseBody> call = apiService.deleteTag(currentUserToken.getToken(), subscribedTagList.get(tagPosition).getTagId());

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                int responseCode = response.code();

                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getActivity(), "The tag is deleted!", Toast.LENGTH_LONG).show();

                                    removeTagFromList(tagPosition);
                                }
                                else
                                {
                                    switch (responseCode)
                                    {
                                        case 400:
                                            Toast.makeText(getActivity(), "Please provide tag id", Toast.LENGTH_LONG).show();
                                            break;
                                        case 403:
                                            Toast.makeText(getActivity(), "This tag cannot be deleted, subscriptions for this exist", Toast.LENGTH_LONG).show();
                                            break;
                                        case 404:
                                            Toast.makeText(getActivity(), "Tag cannot be found from the database", Toast.LENGTH_LONG).show();
                                            break;
                                        default:
                                            Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                                            break;
                                    }
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
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void removeTagFromList(int tagPosition)
    {
        moduleNameList.remove(tagPosition);

        subscribedTagList.remove(tagPosition);

        modulesOrMajorsAdapter.notifyDataSetChanged();

        if(subscribedTagList.size() <= 0)
        {
            noSubText.setVisibility(View.VISIBLE);
        }
    }

    public void directToSubscribedPostList()
    {
        Intent postListIntent = new Intent(getActivity(), SubscribedQuestionsActivity.class);

        postListIntent.putExtra("user", currentUser);
        postListIntent.putExtra("accessToken", currentUserToken);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_token", currentUserToken.getToken());
        editor.commit();
        startActivity(postListIntent);
    }

    public void unsubscribeTag(final int tagPosition)
    {
        new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.MyAlertDialog))
                .setTitle("Unsubscribe Tag")
                .setMessage("Are you sure you want to unsubscribe this tag?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Call<ResponseBody> call = apiService.unsubscribeTag(currentUserToken.getToken(), subscribedTagList.get(tagPosition).getTagId());

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                int responseCode = response.code();

                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getActivity(), "The tag is deleted!", Toast.LENGTH_LONG).show();

                                    removeTagFromList(tagPosition);
                                }
                                else
                                {
                                    switch (responseCode)
                                    {
                                        case 400:
                                            Toast.makeText(getActivity(), "Please provide tag id", Toast.LENGTH_LONG).show();
                                            break;
                                        case 404:
                                            Toast.makeText(getActivity(), "Tag cannot be found from the database", Toast.LENGTH_LONG).show();
                                            break;
                                        default:
                                            Toast.makeText(getActivity(), "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                                            break;
                                    }
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
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

}
