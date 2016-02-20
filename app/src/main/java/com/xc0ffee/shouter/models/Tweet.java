package com.xc0ffee.shouter.models;

public class Tweet {
    private String text;
    private long id;
    private User user;
    private String created_at;
    private Entities entities;
    private String favorite_count;
    private String retweet_count;

    public String getText() {
        return text;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public Entities getEntities() {
        return entities;
    }

    public String getFavouritesCount() {
        return favorite_count;
    }

    public String getRetweetCount() {
        return retweet_count;
    }
}
