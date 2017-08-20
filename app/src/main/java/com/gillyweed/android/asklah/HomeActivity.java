package com.gillyweed.android.asklah;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.Tag;
import com.gillyweed.android.asklah.data.model.TagArray;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private User currentUser;
    private AccessToken currentToken;
    private com.github.clans.fab.FloatingActionButton addPostBtn;

    ApiClient apiClient = null;
    Retrofit retrofit = null;
    ApiInterface apiService = null;
    ArrayList<Tag> tagArrayList;
    ArrayList<String> tagNameArrayList;
    SimpleCursorAdapter tagAdapter;
    SearchView searchView;
    public static final String MyPref = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.mytabs);
        tabLayout.setupWithViewPager(viewPager);

        addPostBtn = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.add_new_post);

        currentUser = getIntent().getParcelableExtra("user");

        currentToken = getIntent().getParcelableExtra("accessToken");

        apiClient = new ApiClient();

        retrofit = apiClient.getClient();

        apiService = retrofit.create(ApiInterface.class);

        final String[] from = new String[] {"fishName"};
        final int[] to = new int[] {android.R.id.text1};

        tagAdapter = new SimpleCursorAdapter(HomeActivity.this, android.R.layout.simple_spinner_dropdown_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        addPostBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent addPostIntent = new Intent(HomeActivity.this, AddPostActivity.class);

                addPostIntent.putExtra("user", HomeActivity.this.getIntent().getParcelableExtra("user"));

                addPostIntent.putExtra("accessToken", HomeActivity.this.getIntent().getParcelableExtra("accessToken"));

                startActivityForResult(addPostIntent, 1);

            }
        });

        getAllTags();
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

                currentToken = intent.getParcelableExtra("accessToken");
            }
        }
        else if(requestCode == 1002)
        {
            if(resultCode == RESULT_OK)
            {
                finish();
                startActivity(getIntent());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflates the menu. Adds items to actionbar if present
        getMenuInflater().inflate(R.menu.menu_home, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        ComponentName componentName = new ComponentName(HomeActivity.this, TagQuestionsActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        searchView.setIconifiedByDefault(false);
        searchView.setSuggestionsAdapter(tagAdapter);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {

                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                CursorAdapter cursorAdapter = searchView.getSuggestionsAdapter();
                Cursor cursor = cursorAdapter.getCursor();
                cursor.moveToPosition(position);
                searchView.setQuery(cursor.getString(cursor.getColumnIndex("fishName")), false);
                SharedPreferences sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("tagId", tagArrayList.get(position).getTagId());
                editor.putString("access_token", currentToken.getToken());
                editor.commit();

                Intent postListIntent = new Intent(HomeActivity.this, TagQuestionsActivity.class);

                postListIntent.putExtra("user", currentUser);
                postListIntent.putExtra("accessToken", currentToken);

                searchView.clearFocus();


                startActivity(postListIntent);
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final MatrixCursor matrixCursor = new MatrixCursor(new String[]{BaseColumns._ID, "fishName"});

                for(int i = 0; i < tagNameArrayList.size(); i++)
                {
                    if(tagNameArrayList.get(i).toLowerCase().startsWith(newText.toLowerCase()))
                    {
                        matrixCursor.addRow(new Object[]{i, tagNameArrayList.get(i)});
                    }
                }

                tagAdapter.changeCursor(matrixCursor);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handles actions on action bar items
        switch (item.getItemId()) {

            case R.id.action_search:
                getAllTags();
                return true;

            case R.id.action_setting:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        adapter.addFragment(new HomeFragment(), "Home");

        adapter.addFragment(new NotifFragment(), "Notifications");
        adapter.addFragment(new ProfileFragment(), "Profile");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position)
                {
                    case 0:
                        addPostBtn.show(true);
                        break;
                    case 1:
                        addPostBtn.hide(true);
                        break;
                    case 2:
                        addPostBtn.hide(true);
                        break;
                    default:
                        addPostBtn.hide(true);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);

            if(searchView != null)
            {
                searchView.clearFocus();
            }
        }
    }

    public void getAllTags()
    {
        Call< TagArray > call = apiService.getTags(currentToken.getToken());

        call.enqueue(new Callback<TagArray>() {
            @Override
            public void onResponse(Call<TagArray> call, Response<TagArray> response) {
                final int responseCode = response.code();

                if(response.isSuccessful()) {
                    tagArrayList = response.body().getTagArrayList();
                    tagNameArrayList = new ArrayList<String>();
                    convertToTagNameList();
                }
                else
                {
                    switch (responseCode)
                    {
                        default:
                            Toast.makeText(HomeActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<TagArray> call, Throwable t) {
                if(call.isCanceled())
                {
                    Toast.makeText(HomeActivity.this, "Request has been canceled", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(HomeActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void convertToTagNameList()
    {
        for(int i = 0; i < tagArrayList.size(); i++)
        {
            tagNameArrayList.add(tagArrayList.get(i).getTagName());
        }
    }
}