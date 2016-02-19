package com.xc0ffee.shouter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.adapters.ShouterRecyclerAdapter;
import com.xc0ffee.shouter.models.Shouter;
import com.xc0ffee.shouter.network.TwitterClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShouterTimelineActivity extends AppCompatActivity {

    private TwitterClient mClient;

    private ArrayList<Shouter> mShouts;
    private ShouterRecyclerAdapter mAdapter;

    @Bind(R.id.rv_tweets) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shouter_timeline);
        setTitle(R.string.timeline_title);

        ButterKnife.bind(this);
        mClient = ShouterApplication.getRestClient();
        mShouts = new ArrayList<>();
        mAdapter = new ShouterRecyclerAdapter(this, mShouts);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        populateTimeline();
    }

    private void populateTimeline() {

        mClient.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                mShouts.addAll(Shouter.fromJsonArray(response));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });
    }
}
