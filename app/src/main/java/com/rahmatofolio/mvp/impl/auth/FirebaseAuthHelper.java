package com.rahmatofolio.mvp.impl.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.rahmatofolio.mvp.base.mvp.callback.ICallbackListener;
import com.rahmatofolio.mvp.data.LoginModel;

public interface FirebaseAuthHelper {
    void signOutFirebase();

    void doLogin(LoginModel loginModel, ICallbackListener<FirebaseUser> callbackListener);

    void createAccount(String email, String pass, ICallbackListener<Task<AuthResult>> callbackListener);

    void signWithEmailAndPassword(String email, String pass, ICallbackListener<Task<AuthResult>> callbackListener);

    void signInWithCredential(AuthCredential authCredential, ICallbackListener<Task<AuthResult>> callbackListener);

    void sendPasswordReset(String email, ICallbackListener<Task<Void>> callbackListener);
}
