package com.xc0ffee.shouter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.xc0ffee.shouter.activities.ShouterApplication;
import com.xc0ffee.shouter.models.Tweet;
import com.xc0ffee.shouter.models.User;
import com.xc0ffee.shouter.network.TwitterClient;

import org.apache.http.Header;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;

public class MentionsTimelineFragment extends TweetsListFragment {

    private TwitterClient mClient;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mClient = ShouterApplication.getRestClient();

        populateTimeline(true, -1);
    }

    public void populateTimeline(final boolean shouldClear, final long maxId) {
        mClient.getMentions(new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getContext(), "Couldn't refresh timeline", Toast.LENGTH_SHORT).show();
                Log.d("NAYAN", "Response = " + responseString);
                mSwipeContainer.setRefreshing(false);
                if (mNetListener != null) {
                    mNetListener.NetworkActivityEnd();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                refreshTimeline(responseString, shouldClear, maxId);
                if (mNetListener != null) {
                    mNetListener.NetworkActivityEnd();
                }
            }
        }, maxId);
        if (mNetListener != null) {
            mNetListener.NetworkActivityStart();
        }
    }

    private void refreshTimeline(String response, boolean shouldClear, final long maxId) {
        if (shouldClear) mTweets.clear();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .create();
        Type listType = new TypeToken<List<Tweet>>(){}.getType();
        List<Tweet> tweets = gson.fromJson(response, listType);

        // Save the tweets in ActiveAndroid
        for (Tweet tweet : tweets) {
            User user = tweet.getUser();
            user.save();
            tweet.save();
        }

        int oldSize = mTweets.size();
        // First tweet is the dup of the maxId
        if (maxId != -1 && !tweets.isEmpty()) tweets.remove(0);
        mTweets.addAll(tweets);
        mAdapter.notifyItemRangeChanged(oldSize, mTweets.size() - 1);
        mSwipeContainer.setRefreshing(false);
    }

    @Override
    protected void refresh(boolean shouldClear, long maxId) {
        populateTimeline(shouldClear, maxId);
    }
}
