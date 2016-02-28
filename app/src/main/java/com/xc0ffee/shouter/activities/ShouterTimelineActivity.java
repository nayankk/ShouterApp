package com.xc0ffee.shouter.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.fragments.HomeTimlineFragment;
import com.xc0ffee.shouter.fragments.MentionsTimelineFragment;
import com.xc0ffee.shouter.network.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShouterTimelineActivity extends AppCompatActivity {

    @Bind(R.id.viewpager) ViewPager mPager;
    @Bind(R.id.tabs) PagerSlidingTabStrip mTabStrip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shouter_timeline);

        ButterKnife.bind(this);

        mPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        mTabStrip.setViewPager(mPager);
    }

    // Return the order of fragment
    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String[] titles = new String[]{"Timeline", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimlineFragment();
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_profile).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                launchProfile();
                return true;
            }
        });
        return true;
    }

    public void launchProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("screenname", "xc0ffee");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Utils.isNetworkAvailable(this) || !Utils.isOnline()) {
            // Show error message
            new AlertDialog.Builder(this)
                    .setTitle("No network!")
                    .setMessage("You are not connected to interview. Please check your internet connection." +
                            " If issue persists, please don't call me. Fix it yourself and try again." +
                            " Meanwhile locally cached tweets will be shown")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create().show();
        }
    }
}
