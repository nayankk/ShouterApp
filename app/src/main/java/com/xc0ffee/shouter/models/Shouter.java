package com.xc0ffee.shouter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Shouter {
    private String mBody;
    private long mId;
    private User mUser;
    private String mCreatedAt;

    public static Shouter fromJson(JSONObject jsonObject) {
        Shouter shouter = new Shouter();
        try {
            shouter.mBody = jsonObject.getString("text");
            shouter.mId = jsonObject.getLong("id");
            shouter.mCreatedAt = jsonObject.getString("created_at");
            shouter.mUser = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return shouter;
    }

    public static List<Shouter> fromJsonArray(JSONArray response) {
        List<Shouter> list = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject tweetJson = response.getJSONObject(i);
                Shouter shouter = Shouter.fromJson(tweetJson);
                if (shouter != null) {
                    list.add(shouter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return list;
    }

    public String getBody() {
        return mBody;
    }

    public long getId() {
        return mId;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public User getUser() {
        return mUser;
    }

}
