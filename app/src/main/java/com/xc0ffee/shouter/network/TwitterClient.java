package com.xc0ffee.shouter.network;

import android.content.Context;
import android.text.TextUtils;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 *
 * This is the object responsible for communicating with a REST API.
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes:
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 *
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 *
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = "3tJGdmlxd7Xp6FlTx6c7zSEXB";
    public static final String REST_CONSUMER_SECRET = "FDtmOFjufMp53DtE0ao080AcrF36QZNFZAohzN1DZyzumkkkPT";
    public static final String REST_CALLBACK_URL = "oauth://cpshouter";

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
    public void getHomeTimeline(AsyncHttpResponseHandler handler, long maxId) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("since_id", 1);
        if (maxId != -1) {
            params.put("max_id", maxId);
        }
        getClient().get(apiUrl, params, handler);
    }

    public void getMentions(AsyncHttpResponseHandler handler, long maxId) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("since_id", 1);
        if (maxId != -1) {
            params.put("max_id", maxId);
        }
        getClient().get(apiUrl, params, handler);
    }


    public void postToTwitter(AsyncHttpResponseHandler handler, String text, String inReplyTo) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", text);
        if (!TextUtils.isEmpty(inReplyTo))
            params.put("in_reply_to_status_id", inReplyTo);
        getClient().post(apiUrl, params, handler);
    }

    public void getProfile(AsyncHttpResponseHandler handler, String screename) {
        String apiUrl = getApiUrl("users/lookup.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screename);
        getClient().get(apiUrl, params, handler);
    }

    public void getUserInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, handler);
    }

    public void retweet(AsyncHttpResponseHandler handler, long id) {
        String apiUrl = getApiUrl("statuses/retweet/" + id + ".json");
        getClient().post(apiUrl, handler);
    }

    public void favTweet(AsyncHttpResponseHandler handler, long id) {
        String apiUrl = getApiUrl("favorites/create.json");
        RequestParams params = new RequestParams();
        params.put("id", id);
        getClient().post(apiUrl, params, handler);
    }

    public void getUserTimeline(String screename, AsyncHttpResponseHandler handler, long maxId) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("since_id", 1);
        if (maxId != -1) {
            params.put("max_id", maxId);
        }
        params.put("screen_name", screename);
        getClient().get(apiUrl, params, handler);
    }
}