package com.xc0ffee.shouter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.activities.DividerItemDecoration;
import com.xc0ffee.shouter.activities.EndlessScrollListener;
import com.xc0ffee.shouter.adapters.ShouterRecyclerAdapter;
import com.xc0ffee.shouter.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TweetsListFragment extends Fragment {

    protected List<Tweet> mTweets = new ArrayList<>();
    protected ShouterRecyclerAdapter mAdapter;

    @Bind(R.id.rv_tweets) RecyclerView mRecyclerView;
    @Bind(R.id.fab_compose) FloatingActionButton mComposeFab;
    @Bind(R.id.swipe_container) SwipeRefreshLayout mSwipeContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        mAdapter = new ShouterRecyclerAdapter(getContext(), mTweets);

        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layout);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
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
                refresh(true, -1);
            }
        });

        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void customLoadMoreDataFromApi(int offset) {
        // Lowest ID will always be at the last position
        long lowest = mTweets.get(mTweets.size()-1).getTweetId();
        refresh(false, lowest);
    }

    private void showCompose() {/*
        FragmentManager fm = getSupportFragmentManager();
        ComposeDialogFragment fragment = ComposeDialogFragment.newInstance("Compose a shout");
        fragment.show(fm, "compose_fg");*/
    }

    public void showReply(long id, String name) {/*
        FragmentManager fm = getSupportFragmentManager();
        ReplyFragment fragment = ReplyFragment.newInstance(name, id);
        fragment.show(fm, "reply");*/
    }

    protected void refresh(boolean shouldClear, long maxId) {

    }
}
