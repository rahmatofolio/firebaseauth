package com.rahmatofolio.mvp.impl.pref;

import com.google.firebase.auth.FirebaseUser;
import com.rahmatofolio.firebaseauth.AppConstants;
import com.rahmatofolio.firebaseauth.helper.PreferencesHelper;
import com.rahmatofolio.mvp.data.SignupModel;
import com.rahmatofolio.mvp.data.UserModel;

import java.util.Objects;

public class AppPrefHelper implements PrefHelper {

    private PreferencesHelper preferencesHelper;

    public AppPrefHelper() {
        this.preferencesHelper = PreferencesHelper.getInstance();
    }

    public PreferencesHelper getPreferencesHelper() {
        return PreferencesHelper.getInstance();
    }

    @Override
    public UserModel getUserModel() {
        UserModel userModel = new UserModel();
        userModel.setUsername(getUsername());
        userModel.setEmail(getEmail());
        userModel.setReferral(getReferral());
        userModel.setName(getName());
        userModel.setImage_url(getImage_url());
        userModel.setUid(getUid());
        userModel.setProvider_id(getProvider_id());
        userModel.setCreation_timestamp(getCreation_timestamp());
        userModel.setLast_login_timestamp(getLast_login_timestamp());
        userModel.setCredit(getCredit());
        return userModel;
    }

    @Override
    public void saveUserModel(UserModel userModel){
        getPreferencesHelper().setValue(UserModel.KEY_USERNAME,userModel.getUsername());
        getPreferencesHelper().setValue(UserModel.KEY_EMAIL,userModel.getEmail());
        getPreferencesHelper().setValue(UserModel.KEY_REFERRAL,userModel.getReferral());
        getPreferencesHelper().setValue(UserModel.KEY_NAME,userModel.getName());
        getPreferencesHelper().setValue(UserModel.KEY_IMAGE_URL,userModel.getImage_url());
        getPreferencesHelper().setValue(UserModel.KEY_UID,userModel.getUid());
        getPreferencesHelper().setValue(UserModel.KEY_PROVIDER_ID,userModel.getProvider_id());
        getPreferencesHelper().setValue(UserModel.KEY_CREATION_TIMESTAMP,userModel.getCreation_timestamp());
        getPreferencesHelper().setValue(UserModel.KEY_LOGIN_TIMESTAMP,userModel.getLast_login_timestamp());
        getPreferencesHelper().setValue(UserModel.KEY_CREDIT,userModel.getCredit());
    }

    @Override
    public void saveUserModel(SignupModel signupModel){
        getPreferencesHelper().setValue(UserModel.KEY_USERNAME,signupModel.getUsername());
        getPreferencesHelper().setValue(UserModel.KEY_EMAIL,signupModel.getEmail());
        getPreferencesHelper().setValue(UserModel.KEY_REFERRAL,signupModel.getReferral());
        getPreferencesHelper().setValue(UserModel.KEY_NAME,signupModel.getName());
        getPreferencesHelper().setValue(UserModel.KEY_CREDIT, AppConstants.CREDIT_DEFAULT);
    }

    @Override
    public void saveUserModel(FirebaseUser user){
        getPreferencesHelper().setValue(UserModel.KEY_NAME, user.getDisplayName());
        getPreferencesHelper().setValue(UserModel.KEY_EMAIL, user.getEmail());
        getPreferencesHelper().setValue(UserModel.KEY_IMAGE_URL, user.getPhotoUrl()!=null ? user.getPhotoUrl().toString() : "");
        getPreferencesHelper().setValue(UserModel.KEY_UID, user.getUid());
        getPreferencesHelper().setValue(UserModel.KEY_PROVIDER_ID, user.getProviderId());
        getPreferencesHelper().setValue(UserModel.KEY_CREATION_TIMESTAMP, Objects.requireNonNull(user.getMetadata()).getCreationTimestamp());
        getPreferencesHelper().setValue(UserModel.KEY_LOGIN_TIMESTAMP, user.getMetadata().getLastSignInTimestamp());
    }

    @Override
    public String getUsername() {
        return getPreferencesHelper().getStringValue(UserModel.KEY_USERNAME,null);
    }

    @Override
    public void setUsername(String username) {
        getPreferencesHelper().setValue(UserModel.KEY_USERNAME,username);
    }

    @Override
    public String getEmail() {
        return getPreferencesHelper().getStringValue(UserModel.KEY_EMAIL,null);
    }

    @Override
    public void setEmail(String email) {
        getPreferencesHelper().setValue(UserModel.KEY_EMAIL,email);
    }

    @Override
    public String getReferral() {
        return getPreferencesHelper().getStringValue(UserModel.KEY_REFERRAL,null);
    }

    @Override
    public void setReferral(String referral) {
        getPreferencesHelper().setValue(UserModel.KEY_REFERRAL,referral);
    }

    @Override
    public String getName() {
        return getPreferencesHelper().getStringValue(UserModel.KEY_NAME,null);
    }

    @Override
    public void setName(String name) {
        getPreferencesHelper().setValue(UserModel.KEY_NAME,name);
    }

    @Override
    public String getImage_url() {
        return getPreferencesHelper().getStringValue(UserModel.KEY_IMAGE_URL,null);
    }

    @Override
    public void setImage_url(String image_url) {
        getPreferencesHelper().setValue(UserModel.KEY_IMAGE_URL,image_url);
    }

    @Override
    public String getUid() {
        return getPreferencesHelper().getStringValue(UserModel.KEY_UID,null);
    }

    @Override
    public void setUid(String uid) {
        getPreferencesHelper().setValue(UserModel.KEY_UID,uid);
    }

    @Override
    public String getProvider_id() {
        return getPreferencesHelper().getStringValue(UserModel.KEY_PROVIDER_ID,null);
    }

    @Override
    public void setProvider_id(String provider_id) {
        getPreferencesHelper().setValue(UserModel.KEY_PROVIDER_ID,provider_id);
    }

    @Override
    public long getCreation_timestamp() {
        return getPreferencesHelper().getLongValue(UserModel.KEY_CREATION_TIMESTAMP,0);
    }

    @Override
    public void setCreation_timestamp(long creation_timestamp) {
        getPreferencesHelper().setValue(UserModel.KEY_CREATION_TIMESTAMP,creation_timestamp);
    }

    @Override
    public long getLast_login_timestamp() {
        return getPreferencesHelper().getLongValue(UserModel.KEY_LOGIN_TIMESTAMP,0);
    }

    @Override
    public void setLast_login_timestamp(long last_login_timestamp) {
        getPreferencesHelper().setValue(UserModel.KEY_LOGIN_TIMESTAMP,last_login_timestamp);
    }

    @Override
    public long getCredit() {
        return getPreferencesHelper().getLongValue(UserModel.KEY_CREDIT,0);
    }

    @Override
    public void setCredit(long credit) {
        getPreferencesHelper().setValue(UserModel.KEY_CREDIT,credit);
    }

}
