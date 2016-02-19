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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);

        populateTimeline();
    }

    private void populateTimeline() {
        mClient.getHomeTimeline(new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new GsonBuilder().create();
                Type listType = new TypeToken<List<Tweet>>(){}.getType();
                List<Tweet> tweets = gson.fromJson(responseString, listType);
                mTweets.addAll(tweets);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
