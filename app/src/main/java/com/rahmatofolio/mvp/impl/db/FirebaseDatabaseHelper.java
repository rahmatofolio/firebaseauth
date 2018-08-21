package com.rahmatofolio.mvp.impl.db;

import com.google.firebase.database.DatabaseReference;
import com.rahmatofolio.mvp.base.mvp.callback.ICallbackListener;
import com.rahmatofolio.mvp.data.UserModel;

public interface FirebaseDatabaseHelper {
    DatabaseReference getTableUser();
    DatabaseReference getTablePost();
    void checkEmailOnServer(String email, ICallbackListener<UserModel> callbackListener);
    void checkUsernameOnServer(String username, ICallbackListener<UserModel> callbackListener);
    void updateUserOnServer(UserModel userModel, ICallbackListener<UserModel> callbackListener);
}
