package com.xc0ffee.shouter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.activities.ShouterApplication;
import com.xc0ffee.shouter.activities.ShouterTimelineActivity;
import com.xc0ffee.shouter.network.TwitterClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReplyFragment extends android.support.v4.app.DialogFragment {

    @Bind(R.id.et_tweet) EditText mTweetText;
    @Bind(R.id.btn_tweet) Button mTweetBtn;
    @Bind(R.id.tv_char_count) TextView mCharCount;
    @Bind(R.id.iv_profile) ImageView mProfileImage;
    @Bind(R.id.iv_close) ImageView mCloseBtn;
    @Bind(R.id.tv_myname) TextView mMyName;
    @Bind(R.id.tv_myhandle) TextView mMyHandle;

    private String mHandle;

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int length = s.length();
            if (length <= 140) {
                mCharCount.setTextColor(getResources().getColor(android.R.color.darker_gray));
                mCharCount.setText(String.valueOf(140-s.length()));
            } else {
                mCharCount.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                mCharCount.setText(String.valueOf(140-s.length()));
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    public static ReplyFragment newInstance(String handle, long id) {
        ReplyFragment fragment = new ReplyFragment();
        Bundle args = new Bundle();
        args.putString("handle", handle);
        args.putString("id", String.valueOf(id));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reply_layout, container);
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();

        ShouterApplication.getRestClient().getProfile(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String profileImgUrl = null;
                try {
                    profileImgUrl = response.getString("profile_image_url");
                    Glide.with(getContext()).load(profileImgUrl).into(mProfileImage);
                    mMyName.setText(response.getString("name"));
                    mMyHandle.setText("@" + response.getString("screen_name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        final String id = bundle.getString("id");
        mHandle = bundle.getString("handle");
        mTweetText.setHint("Reply to @" + mHandle);
        mTweetText.addTextChangedListener(mTextEditorWatcher);

        mTweetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyToId(id);
            }
        });

        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void replyToId(String id) {
        TwitterClient client = ShouterApplication.getRestClient();
        String status = "@" + mHandle + " " + mTweetText.getText().toString();
        client.postToTwitter(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getContext(), "Reply sent", Toast.LENGTH_SHORT).show();
                ShouterTimelineActivity activity = (ShouterTimelineActivity) getContext();
                activity.populateTimeline(true, -1);
                dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getContext(), "Couldn't refresh", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        }, status, id);
    }
}
