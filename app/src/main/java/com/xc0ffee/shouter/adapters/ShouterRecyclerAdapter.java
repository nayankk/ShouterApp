package com.xc0ffee.shouter.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.activities.ShouterApplication;
import com.xc0ffee.shouter.activities.ShouterTimelineActivity;
import com.xc0ffee.shouter.models.Media;
import com.xc0ffee.shouter.models.Tweet;

import org.apache.http.Header;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShouterRecyclerAdapter extends RecyclerView.Adapter <ShouterRecyclerAdapter.ViewHolder> {

    List<Tweet> mTweets;

    private final Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_profile) ImageView mProfileImage;
        @Bind(R.id.tv_user) TextView mUsername;
        @Bind(R.id.tv_body) TextView mBody;
        @Bind(R.id.tv_timestamp) TextView mTimeStamp;
        @Bind(R.id.iv_embed_image) ImageView mImage;
        @Bind(R.id.tv_retweet_count) TextView mTweetCnt;
        @Bind(R.id.tv_like_count) TextView mLikeCnt;
        @Bind(R.id.retweet) ViewGroup mRetweetView;
        @Bind(R.id.fav) ViewGroup mFavView;
        @Bind(R.id.retweet_img) ImageView mRetweetImg;
        @Bind(R.id.iv_favorited) ImageView mFavImg;
        @Bind(R.id.tv_handle) TextView mHandle;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public ShouterRecyclerAdapter(Context context, List<Tweet> tweets) {
        mContext = context;
        mTweets = tweets;
    }

    @Override
    public ShouterRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.shouter_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ShouterRecyclerAdapter.ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        ImageView imageView = holder.mProfileImage;
        imageView.setImageResource(android.R.color.transparent);
        Glide.with(mContext).load(tweet.getUser().getProfileImageUrl()).into(imageView);
        holder.mUsername.setText(tweet.getUser().getName());
        holder.mBody.setText(tweet.getText());
        holder.mTimeStamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));

        holder.mImage.setImageResource(android.R.color.transparent);
        holder.mImage.setVisibility(View.GONE);
        if (tweet.getEntities() != null) {
            List<Media> mediaList = tweet.getEntities().getMedia();
            if (!mediaList.isEmpty()) {
                String embedUrl = mediaList.get(0).getMediaUrl();
                if (!TextUtils.isEmpty(embedUrl)) {
                    Glide.with(mContext).load(embedUrl).into(holder.mImage);
                    holder.mImage.setVisibility(View.VISIBLE);
                }
            }
        }

        holder.mLikeCnt.setText(tweet.getFavouritesCount());
        holder.mTweetCnt.setText(tweet.getRetweetCount());

        holder.mRetweetImg.setImageResource(android.R.color.transparent);
        if (tweet.isRetweeted()) holder.mRetweetImg.setImageResource(R.drawable.twitter_retweet_selected);
        else holder.mRetweetImg.setImageResource(R.drawable.ic_twitter_retweet_grey600_18dp);

        if (!tweet.isRetweeted()) {
            final long tweetId = tweet.getTweetId();
            holder.mRetweetView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setPositiveButton("Retweet", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    retweetMessage(tweetId);
                                }
                            })
                            .setTitle("Retweet?")
                            .setMessage("Are you sure you want to retweet " + holder.mUsername.getText().toString() + "\'s tweet")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create().show();
                }
            });
        }

        holder.mFavImg.setImageResource(android.R.color.transparent);
        if (tweet.isFavorited()) holder.mFavImg.setImageResource(R.drawable.heart_selected);
        else holder.mFavImg.setImageResource(R.drawable.ic_heart_grey600_18dp);

        if (!tweet.isFavorited()) {
            final long tweetId = tweet.getTweetId();
            holder.mFavView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favoriteTweet(tweetId);
                }
            });
        }

        holder.mHandle.setText("@"+tweet.getUser().getScreenName());
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public void retweetMessage(long tweetId) {
        ShouterApplication.getRestClient().retweet(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ShouterTimelineActivity activity = (ShouterTimelineActivity) mContext;
                activity.populateTimeline(true, -1);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(mContext, "Retweet failed", Toast.LENGTH_SHORT).show();
                Log.d("NAYAN", "Error = " + errorResponse);
            }
        }, tweetId);
    }

    public void favoriteTweet(long tweetId) {
        ShouterApplication.getRestClient().favTweet(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ShouterTimelineActivity activity = (ShouterTimelineActivity) mContext;
                activity.populateTimeline(true, -1);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(mContext, "Favorite failed", Toast.LENGTH_SHORT).show();
                Log.d("NAYAN", "Error = " + errorResponse);
            }
        }, tweetId);
    }
}
