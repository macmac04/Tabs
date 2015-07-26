package com.app.macky.droidgency;

/**
 * Created by Macsanity on 7/25/2015.
 */


public class TwitterTweet {


    private String createdAt;


    private String id;


    private String text;


    private String inReplyToStatusId;


    private String inReplyToUserId;


    private String inReplyToScreenName;

    private TwitterUser twitterUser;

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public String getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public String getInReplyToUserId() {
        return inReplyToUserId;
    }

    public String getText() {
        return text;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    public void setInReplyToStatusId(String inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    public void setInReplyToUserId(String inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTwitterUser(TwitterUser twitterUser) {
        this.twitterUser = twitterUser;
    }

    public TwitterUser getTwitterUser() {
        return twitterUser;
    }

    @Override
    public String  toString(){
        return getText();
    }
}