package com.xc0ffee.shouter.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.fragments.UserTimelineFragment;
import com.xc0ffee.shouter.network.TwitterClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private TwitterClient mClient;

    @Bind(R.id.riv_pp) ImageView mProfileImage;
    @Bind(R.id.tv_username) TextView mMyName;
    @Bind(R.id.tv_handle) TextView mMyHandle;
    @Bind(R.id.iv_profile_header) ImageView mProfileHeader;
    @Bind(R.id.tv_desc) TextView mDesc;
    @Bind(R.id.tv_following) TextView mFollowing;
    @Bind(R.id.tv_followers) TextView mFollowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile);

        ButterKnife.bind(this);

        mClient = ShouterApplication.getRestClient();

        if (savedInstanceState == null) {
            String screename = getIntent().getStringExtra("screenname");

            mClient.getProfile(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    String profileImgUrl = null;
                    try {
                        JSONObject user = response.getJSONObject(0);
                        profileImgUrl = user.getString("profile_image_url");
                        Glide.with(ProfileActivity.this).load(profileImgUrl).into(mProfileImage);
                        mMyName.setText(user.getString("name"));
                        mMyHandle.setText("@" + user.getString("screen_name"));
                        String profileBg = user.getString("profile_background_image_url");
                        Glide.with(ProfileActivity.this).load(profileBg).into(mProfileHeader);
                        mDesc.setText(user.getString("description"));
                        mFollowing.setText(Html.fromHtml("<b>" + user.getString("friends_count") + "</b>" + " Following"));
                        mFollowers.setText(Html.fromHtml("<b>" + user.getString("followers_count") + "</b>" + " Followers"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                }
            }, screename);

            UserTimelineFragment userFragment = UserTimelineFragment.newInstance(screename);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.id_fragment_container, userFragment);
            transaction.commit();
        }

    }
}
