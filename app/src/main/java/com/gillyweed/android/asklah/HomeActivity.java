package com.gillyweed.android.asklah;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private User currentUser;
    private AccessToken currentToken;

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

        currentUser = getIntent().getParcelableExtra("user");

        currentToken = getIntent().getParcelableExtra("accessToken");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                Log.i("addpost", "back button clicked");

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handles actions on action bar items
        switch (item.getItemId()) {
//            case R.id.action_announcement:
//                startActivity(new Intent(this, AnnouncementsActivity.class));
//                return true;

            case R.id.action_setting:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

//        if(currentUser == null && currentToken == null)
//        {
//            Log.i("before add post", "object is null!!!");

            adapter.addFragment(new HomeFragment(), "Home");
//        }
//        else
//        {
//            Log.i("after add post", "object is " + getIntent().getParcelableExtra("user"));
//
//            HomeFragment homeFragment = new HomeFragment();
//
//            Bundle bundle = new Bundle();
//
//            bundle.putParcelable("user", getIntent().getParcelableExtra("user"));
//            bundle.putParcelable("accessToken", getIntent().getParcelableExtra("accessToken"));
//            homeFragment.setArguments(bundle);
//            adapter.addFragment(homeFragment, "Home");
//        }

//        adapter.addFragment(new HomeFragmentNewUser(), "Home");
        adapter.addFragment(new NotifFragment(), "Notifications");
        adapter.addFragment(new ProfileFragment(), "Profile");
        viewPager.setAdapter(adapter);
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
}