package com.xc0ffee.shouter.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.adapters.ShouterRecyclerAdapter;
import com.xc0ffee.shouter.fragments.ComposeDialogFragment;
import com.xc0ffee.shouter.fragments.ReplyFragment;
import com.xc0ffee.shouter.models.Tweet;
import com.xc0ffee.shouter.models.User;
import com.xc0ffee.shouter.network.TwitterClient;

import org.apache.http.Header;

import java.lang.reflect.Modifier;
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
    @Bind(R.id.fab_compose) FloatingActionButton mComposeFab;
    @Bind(R.id.swipe_container) SwipeRefreshLayout mSwipeContainer;

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

        mComposeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCompose();
            }
        });

        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline(true, -1);
            }
        });

        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // First load from Active Android DB
        loadFromAA();

        // Get tweets from network
        populateTimeline(true, -1);
    }

    private void loadFromAA() {
        List<Tweet> tweetList = new Select().from(Tweet.class).orderBy("RemoteId DESC").limit(100).execute();
        mTweets.addAll(tweetList);
        mAdapter.notifyDataSetChanged();
    }

    public void populateTimeline(final boolean shouldClear, final long maxId) {
        mClient.getHomeTimeline(new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Couldn't refresh timeline", Toast.LENGTH_SHORT).show();
                Log.d("NAYAN", "Response = " + responseString);
                mSwipeContainer.setRefreshing(false);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                refreshTimeline(responseString, shouldClear, maxId);
            }
        }, maxId);
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
        // First time is the dup of the maxId
        if (maxId != -1 && !tweets.isEmpty()) tweets.remove(0);
        mTweets.addAll(tweets);
        mAdapter.notifyItemRangeChanged(oldSize, mTweets.size() - 1);
        mSwipeContainer.setRefreshing(false);
    }

    public void customLoadMoreDataFromApi(int offset) {
        // Lowest ID will always be at the last position
        long lowest = mTweets.get(mTweets.size()-1).getId();
        populateTimeline(false, lowest);
    }

    private void showCompose() {
        FragmentManager fm = getSupportFragmentManager();
        ComposeDialogFragment fragment = ComposeDialogFragment.newInstance("Compose a shout");
        fragment.show(fm, "compose_fg");
    }

    public void showReply(long id, String name) {
        FragmentManager fm = getSupportFragmentManager();
        ReplyFragment fragment = ReplyFragment.newInstance(name, id);
        fragment.show(fm, "reply");
    }
}
