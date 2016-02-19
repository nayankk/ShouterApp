package com.xc0ffee.shouter.activities;

import android.app.Application;
import android.content.Context;

import com.xc0ffee.shouter.network.TwitterClient;

public class ShouterApplication extends Application {

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
