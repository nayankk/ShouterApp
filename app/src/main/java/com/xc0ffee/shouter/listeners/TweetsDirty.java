package com.xc0ffee.shouter.listeners;

public interface TweetsDirty {

    public void OnNewTweetSent();
    public void OnReplySent();
    public void OnTweetFavorited();
    public void OnTweetRetweeted();
    public void OnShowReplyScreen(long tweetId, String name);
}
