package com.xc0ffee.shouter.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;
import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.activities.ShouterApplication;
import com.xc0ffee.shouter.activities.ShouterTimelineActivity;
import com.xc0ffee.shouter.network.TwitterClient;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ComposeDialogFragment extends android.support.v4.app.DialogFragment {

    @Bind(R.id.et_tweet) EditText mTweetText;
    @Bind(R.id.btn_tweet) Button mTweetBtn;

    public static ComposeDialogFragment newInstance(String title) {
        ComposeDialogFragment fragment = new ComposeDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.compose_layout, container);
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        mTweetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postToTwitter();
            }
        });
    }

    private void postToTwitter() {
        String tweetMsg = mTweetText.getText().toString();
        if (TextUtils.isEmpty(tweetMsg)) {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Shout can't be empty")
                    .setMessage("Please shout something")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create();
            alertDialog.show();
            return;
        }

        TwitterClient client = ShouterApplication.getRestClient();
        client.postToTwitter(new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Toast.makeText(getContext(), "Successfully shouted", Toast.LENGTH_SHORT).show();
                ShouterTimelineActivity activity = (ShouterTimelineActivity) getActivity();
                activity.populateTimeline(true, -1);
                dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getContext(), "Couldn't shout!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        }, tweetMsg);
    }
}
