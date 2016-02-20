package com.xc0ffee.shouter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "User")
public class User extends Model {

    @Column(name = "Name")
    private String name;

    @Column(name = "RemoteId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long id;

    @Column(name = "ScreenName")
    private String screen_name;

    @Column(name = "ProfileImage")
    private String profile_image_url;

    public User() {
        super();
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screen_name;
    }

    public String getProfileImageUrl() {
        return profile_image_url;
    }
}
