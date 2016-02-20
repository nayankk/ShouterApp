package com.xc0ffee.shouter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.models.Media;
import com.xc0ffee.shouter.models.Tweet;

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
    public void onBindViewHolder(ShouterRecyclerAdapter.ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        ImageView imageView = holder.mProfileImage;
        imageView.setImageResource(android.R.color.transparent);
        Glide.with(mContext).load(tweet.getUser().getProfileImageUrl()).into(imageView);
        holder.mUsername.setText(tweet.getUser().getName());
        holder.mBody.setText(tweet.getText());
        holder.mTimeStamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        List<Media> mediaList = tweet.getEntities().getMedia();
        holder.mImage.setImageResource(android.R.color.transparent);
        holder.mImage.setVisibility(View.GONE);
        if (!mediaList.isEmpty()) {
            String embedUrl = mediaList.get(0).getMediaUrl();
            if (!TextUtils.isEmpty(embedUrl)) {
                Glide.with(mContext).load(embedUrl).into(holder.mImage);
                holder.mImage.setVisibility(View.VISIBLE);
            }
        }
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
}
