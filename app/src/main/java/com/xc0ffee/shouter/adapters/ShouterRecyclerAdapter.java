package com.xc0ffee.shouter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.models.Tweet;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShouterRecyclerAdapter extends RecyclerView.Adapter <ShouterRecyclerAdapter.ViewHolder> {

    List<Tweet> mTweets;

    private final Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_profile) ImageView mProfileImage;
        @Bind(R.id.tv_user) TextView mUsername;
        @Bind(R.id.tv_body) TextView mBody;

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
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }
}
