package com.xc0ffee.shouter.models;

public class Tweet {
    private String text;
    private long id;
    private User user;
    private String created_at;

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
}
