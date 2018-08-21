package com.rahmatofolio.mvp.impl.pref;

import com.google.firebase.auth.FirebaseUser;
import com.rahmatofolio.mvp.data.SignupModel;
import com.rahmatofolio.mvp.data.UserModel;

public interface PrefHelper {
    UserModel getUserModel();

    void saveUserModel(UserModel userModel);

    void saveUserModel(SignupModel signupModel);

    void saveUserModel(FirebaseUser user);

    String getUsername();

    void setUsername(String username);

    String getEmail();

    void setEmail(String email);

    String getReferral();

    void setReferral(String referral);

    String getName();

    void setName(String name);

    String getImage_url();

    void setImage_url(String image_url);

    String getUid();

    void setUid(String uid);

    String getProvider_id();

    void setProvider_id(String provider_id);

    long getCreation_timestamp();

    void setCreation_timestamp(long creation_timestamp);

    long getLast_login_timestamp();

    void setLast_login_timestamp(long last_login_timestamp);

    long getCredit();

    void setCredit(long credit);
}
