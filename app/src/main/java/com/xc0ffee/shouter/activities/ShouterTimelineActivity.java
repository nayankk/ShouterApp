package com.xc0ffee.shouter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.adapters.ShouterRecyclerAdapter;
import com.xc0ffee.shouter.models.Tweet;
import com.xc0ffee.shouter.network.TwitterClient;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShouterTimelineActivity extends AppCompatActivity {

    private TwitterClient mClient;

    private List<Tweet> mTweets = new ArrayList<>();
    private ShouterRecyclerAdapter mAdapter;

    @Bind(R.id.rv_tweets) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shouter_timeline);
        setTitle(R.string.timeline_title);

        ButterKnife.bind(this);
        mClient = ShouterApplication.getRestClient();
        mAdapter = new ShouterRecyclerAdapter(this, mTweets);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layout);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.addOnScrollListener(new EndlessScrollListener(layout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
        });

        populateTimeline(-1);
    }

    private void populateTimeline(final long maxId) {
        mClient.getHomeTimeline(new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new GsonBuilder().create();
                Type listType = new TypeToken<List<Tweet>>(){}.getType();
                List<Tweet> tweets = gson.fromJson(responseString, listType);
                int oldSize = tweets.size();
                // First time is the dup of the maxId
                tweets.remove(0);
                mTweets.addAll(tweets);
                mAdapter.notifyItemRangeChanged(oldSize, mTweets.size()-1);
            }
        }, maxId);
    }

    public void customLoadMoreDataFromApi(int offset) {
        // Lowest ID will always be at the last position
        long lowest = mTweets.get(mTweets.size()-1).getId();
        populateTimeline(lowest);
    }
}
