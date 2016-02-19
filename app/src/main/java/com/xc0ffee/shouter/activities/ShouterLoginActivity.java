package com.xc0ffee.shouter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.network.TwitterClient;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShouterLoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

    @Bind(R.id.btn_login) Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shouter_login);

        ButterKnife.bind(this);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToRest();
            }
        });
    }

    public void loginToRest() {
        getClient().connect();
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, ShouterTimelineActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailure(Exception e) {

    }
}
