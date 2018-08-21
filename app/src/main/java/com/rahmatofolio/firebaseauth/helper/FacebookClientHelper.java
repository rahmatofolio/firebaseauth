package com.rahmatofolio.firebaseauth.helper;

import android.content.Intent;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;

public class FacebookClientHelper {
    private static FacebookClientHelper sInstance = null;
    private static final String TAG = FacebookClientHelper.class.getSimpleName();
    private static final int RC_SIGN_IN_FACEBOOK = 9011;
    private CallbackManager mCallbackManager;
    private LoginButton mLoginButton;

    public FacebookClientHelper(LoginButton loginButton) {
        this.mLoginButton = loginButton;
    }

    public static FacebookClientHelper newInstance(LoginButton loginButton) {
        if (sInstance == null) {
            sInstance = new FacebookClientHelper(loginButton);
        }
        return sInstance;
    }

    public void addFacebookHelper(){
        this.mCallbackManager = CallbackManager.Factory.create();
        this.mLoginButton.setReadPermissions("email", "public_profile");
    }

    public void addFacebookListener(CredentialListener<AuthCredential> credentialListener){
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                credentialListener.onCredentialGet(credential);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }

    public void signInFacebook(){
        mLoginButton.performClick();
    }

    public void signOutFacebook(){
        LoginManager.getInstance().logOut();
    }

    public void getCredentialFacebook(int requestCode, int resultCode, Intent data){
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public interface CredentialListener<T>{
        void onCredentialGet(T credential);
    }
}
