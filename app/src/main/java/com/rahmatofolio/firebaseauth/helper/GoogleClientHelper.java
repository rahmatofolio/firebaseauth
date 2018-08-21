package com.rahmatofolio.firebaseauth.helper;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.rahmatofolio.firebaseauth.R;

import static android.app.Activity.RESULT_OK;

public class GoogleClientHelper {
    private static GoogleClientHelper sInstance = null;
    private static final String TAG = GoogleClientHelper.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;
    private Activity mActivity;
    private AuthCredential mCredential;

    private GoogleClientHelper(Activity activity) {
        this.mActivity = activity;
    }

    public static GoogleClientHelper newInstance(Activity activity) {
        if (sInstance==null) {
            sInstance = new GoogleClientHelper(activity);
        }
        return sInstance;
    }

    public void addGoogleHelper(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mActivity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        this.mGoogleSignInClient = GoogleSignIn.getClient(mActivity, gso);
    }

    public void signOutGoogle() {
        mGoogleSignInClient.signOut();
    }

    public void signInGoogle() {
        mGoogleSignInClient.signOut();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void getCredentialGoogle(int requestCode, int resultCode, Intent data, CredentialListener<AuthCredential> credentialListener) {
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    credentialListener.onCredentialGet(credential);

                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.d(TAG, "Google sign in failed", e);
                }
            } else {
                Log.e(TAG, "Credential Google: NOT OK");
            }

        }
    }

    public interface CredentialListener<T>{
        void onCredentialGet(T credential);
    }
}
