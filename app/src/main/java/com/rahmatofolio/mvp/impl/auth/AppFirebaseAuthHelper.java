package com.rahmatofolio.mvp.impl.auth;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rahmatofolio.mvp.base.mvp.callback.ICallbackListener;
import com.rahmatofolio.mvp.data.LoginModel;

public class AppFirebaseAuthHelper implements FirebaseAuthHelper {
    private final String TAG = AppFirebaseAuthHelper.class.getSimpleName();
    private final FirebaseAuth mAuth;

    public AppFirebaseAuthHelper() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signOutFirebase(){
        mAuth.signOut();
    }

    @Override
    public void doLogin(LoginModel loginModel, ICallbackListener<FirebaseUser> callbackListener) {
        mAuth.signInWithEmailAndPassword(loginModel.getUsername(),loginModel.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "loginUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        callbackListener.onSuccess(user);
                    } else {
                        callbackListener.onFailure(task.getException());
                    }
                });
    }

    @Override
    public void createAccount(String email, String pass, ICallbackListener<Task<AuthResult>> callbackListener) {
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmailAndPassword:success");
                        callbackListener.onSuccess(task);
                    } else {
                        callbackListener.onFailure(task.getException());
                    }
                });
    }

    @Override
    public void signWithEmailAndPassword(String email, String pass, ICallbackListener<Task<AuthResult>> callbackListener){
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "signWithEmailAndPassword:success");
                callbackListener.onSuccess(task);
            } else {
                callbackListener.onFailure(task.getException());
            }
        });
    }

    @Override
    public void signInWithCredential(AuthCredential authCredential, ICallbackListener<Task<AuthResult>> callbackListener) {
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "signInWithCredential:success");
                callbackListener.onSuccess(task);
            } else {
                callbackListener.onFailure(task.getException());
            }
        });
    }

    @Override
    public void sendPasswordReset(String email, ICallbackListener<Task<Void>> callbackListener){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "sendPasswordReset:success");
                callbackListener.onSuccess(task);
            } else {
                callbackListener.onFailure(task.getException());
            }
        });
    }
}
