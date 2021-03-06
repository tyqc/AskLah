package com.gillyweed.android.asklah;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.AddPost;
import com.gillyweed.android.asklah.data.model.Tag;
import com.gillyweed.android.asklah.data.model.TagArray;
import com.gillyweed.android.asklah.data.model.TagDescrip;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPostActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    EditText postTitleText;
    EditText postTagText;
    EditText postDescripText;
    ApiClient apiClient = null;
    Retrofit retrofit = null;
    ApiInterface apiService = null;
    User currentUser = null;
    AccessToken currentUserToken = null;
    CharSequence[] tagNameArray;
    ArrayList<TagDescrip> tagArray;
    ArrayList<Integer> selectedTags;
    boolean checkSelected = false;
    boolean[] checked;
    Button takePhotoText;
    Bitmap imageBitmap;
    ImageView attachmentImageView;
    String imageDir;
    private String TAG = "add post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        getSupportActionBar().setTitle("Add New Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiClient = new ApiClient();

        retrofit = apiClient.getClient();

        apiService = retrofit.create(ApiInterface.class);

        currentUser = getIntent().getParcelableExtra("user");

        currentUserToken = getIntent().getParcelableExtra("accessToken");

        postTitleText = (EditText) findViewById(R.id.newPostTitleText);

        postDescripText = (EditText) findViewById(R.id.newPostDescriptText);

        postTagText = (EditText) findViewById(R.id.tagsText);

        takePhotoText = (Button) findViewById(R.id.addPhotoText);

        //  attachmentImageView = (ImageView)findViewById(R.id.image_attach_image_view);

        getTagArray();

        postTagText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                selectedTags = new ArrayList<Integer>();

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setTitle("Select Tags");

                builder.setMultiChoiceItems(tagNameArray, checked,
                        new DialogInterface.OnMultiChoiceClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkSelected = true;
                            }

                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(checkSelected)
                        {
                            String tagSelected = "";

                            for (int i = 0; i < checked.length; i++)
                            {
                                if(checked[i])
                                {
                                    tagSelected += tagArray.get(i).getTagName();

                                    tagSelected += ", ";

                                    selectedTags.add(tagArray.get(i).getTagId());

                                }
                            }

                            // Setting the string for question tag edit text
                            postTagText.setText(tagSelected);

                            checkSelected = false;
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                AlertDialog dialog = builder.create();

                dialog.show();
            }
        });

        takePhotoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.add_post_action, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:

                Intent homeIntent = getParentActivityIntent();
                homeIntent.putExtra("user", currentUser);
                homeIntent.putExtra("accessToken", currentUserToken);
                NavUtils.navigateUpTo(this, homeIntent);
                return true;

            case R.id.action_post:
                postNewQuestion();
                homeIntent = getParentActivityIntent();
                homeIntent.putExtra("user", currentUser);
                homeIntent.putExtra("accessToken", currentUserToken);
                NavUtils.navigateUpTo(this, homeIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getTagArray()
    {
        Call<TagArray> call = apiService.getTags(currentUserToken.getToken());

        call.enqueue(new Callback<TagArray>() {
            @Override
            public void onResponse(Call<TagArray> call, Response<TagArray> response) {

                int responseCode = response.code();

                if(response.isSuccessful())
                {
                    tagArray = response.body().getTagArrayList();

                    copyToTagNameArray(tagArray);
                }
                else
                {
                    switch (responseCode)
                    {
                        default:
                            Toast.makeText(AddPostActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<TagArray> call, Throwable t) {
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

    public void copyToTagNameArray(ArrayList<TagDescrip> tagArr)
    {
        tagNameArray = new CharSequence[tagArr.size()];

        for (int i = 0; i < tagArr.size(); i++)
        {
            tagNameArray[i] = tagArr.get(i).getTagName();
        }

        checked = new boolean[tagArray.size()];
    }

    public void postNewQuestion()
    {
        AddPost newPost = new AddPost();

        newPost.setPostTitle(postTitleText.getText().toString());

        newPost.setPostDescription(postDescripText.getText().toString());

//        int[] selectedArr = new int[selectedTags.size()];
//
//         selectedArr = convertToPrimArray(selectedArr);

        if (imageDir != null)
        {
            newPost.setImgLink(imageDir);
        }

        newPost.setTagIds(selectedTags);

        Call<ResponseBody> call = apiService.addNewPost(currentUserToken.getToken(), currentUser.getNusId(), newPost);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int responseCode = response.code();

                if(response.isSuccessful())
                {
                    Toast.makeText(AddPostActivity.this, "New Post Added", Toast.LENGTH_LONG).show();
                }
                else
                {
                    switch (responseCode)
                    {
                        default:
                            Toast.makeText(AddPostActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                            break;
                    }
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
    }

    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            File photoFile = null;

            try {
                photoFile = createImageFile();
            }catch (IOException ex)
            {
                Log.e(TAG, "camera error: " + ex.getMessage());
            }

            if(photoFile != null)
            {
                Uri photoUri = FileProvider.getUriForFile(this, "com.gillyweed.android.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
//            Bundle extras = data.getExtras();


//            imageBitmap = (Bitmap)extras.get("data");

            //    attachmentImageView.setVisibility(View.VISIBLE);

            takePhotoText.setText("Photo attached");
        }
    }

    private File createImageFile() throws IOException
    {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "JPEG_" + timestamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        imageDir = image.getAbsolutePath();

        return image;
    }

    //    public int[] convertToPrimArray(int[] selectedTagArr)
//    {
//        for(int i = 0; i < selectedTagArr.length; i++)
//        {
//            selectedTagArr[i] = selectedTags.get(i);
//        }
//
//        return selectedTagArr;
//    }
}
