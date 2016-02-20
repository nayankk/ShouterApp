package com.xc0ffee.shouter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tweets")
public class Tweet extends Model {

    @Column(name = "Text")
    private String text;

    @Column(name = "RemoteId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long id;

    @Column(name = "User", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private User user;

    @Column(name = "CreatedAt")
    private String created_at;

    private Entities entities;

    @Column(name = "FavoriteCount")
    private String favorite_count;

    @Column(name = "RetweetCount")
    private String retweet_count;

    public Tweet() {
        super();
    }

    public String getText() {
        return text;
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
