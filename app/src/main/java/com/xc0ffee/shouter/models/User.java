package com.xc0ffee.shouter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private String mName;
    private long mUid;
    private String mScreenName;
    private String mProfileImageUrl;

    public static User fromJson(JSONObject jsonObject) {
        User user = new User();
        try {
            user.mName = jsonObject.getString("name");
            user.mUid = jsonObject.getLong("id");
            user.mScreenName = jsonObject.getString("screen_name");
            user.mProfileImageUrl = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getName() {
        return mName;
    }

    public long getUid() {
        return mUid;
    }

    public String getScreenName() {
        return mScreenName;
    }

    public String getProfileImageUrl() {
        return mProfileImageUrl;
    }
}
