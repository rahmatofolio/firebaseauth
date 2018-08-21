package com.rahmatofolio.mvp.data;

public class UserModel {
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_REFERRAL = "referral";
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE_URL = "image_url";
    public static final String KEY_UID = "uid";
    public static final String KEY_PROVIDER_ID = "provider_id";
    public static final String KEY_CREATION_TIMESTAMP = "creation_timestamp";
    public static final String KEY_LOGIN_TIMESTAMP = "last_login_timestamp";
    public static final String KEY_CREDIT = "credit";

    private String username;
    private String email;
    private String referral;
    private String name;
    private String image_url;
    private String uid;
    private String provider_id;
    private long creation_timestamp;
    private long last_login_timestamp;
    private long credit;
    private boolean online;

    public UserModel() {
    }

    public UserModel(String username, String email, String referral, String name, String image_url, String uid, String provider_id, long creation_timestamp, long last_login_timestamp, long credit) {
        this.username = username;
        this.email = email;
        this.referral = referral;
        this.name = name;
        this.image_url = image_url;
        this.uid = uid;
        this.provider_id = provider_id;
        this.creation_timestamp = creation_timestamp;
        this.last_login_timestamp = last_login_timestamp;
        this.credit = credit;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public long getCreation_timestamp() {
        return creation_timestamp;
    }

    public void setCreation_timestamp(long creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }

    public long getLast_login_timestamp() {
        return last_login_timestamp;
    }

    public void setLast_login_timestamp(long last_login_timestamp) {
        this.last_login_timestamp = last_login_timestamp;
    }

    public long getCredit() {
        return credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
