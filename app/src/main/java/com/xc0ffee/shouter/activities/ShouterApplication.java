package com.xc0ffee.shouter.activities;

import android.content.Context;

import com.xc0ffee.shouter.network.TwitterClient;

public class ShouterApplication extends com.activeandroid.app.Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static TwitterClient getRestClient() {
        return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, mContext);
    }
}
