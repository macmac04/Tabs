package com.app.macky.droidgency;

/**
 * Created by Macsanity on 7/25/2015.
 */


public class TwitterUser {


    private String screenName;


    private String name;


    private String profileImageUrl;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}