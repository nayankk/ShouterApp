package com.xc0ffee.shouter.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.fragments.HomeTimlineFragment;
import com.xc0ffee.shouter.fragments.MentionsTimelineFragment;

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
        private String[] titles = new String[]{"Home", "Timeline"};

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
}
