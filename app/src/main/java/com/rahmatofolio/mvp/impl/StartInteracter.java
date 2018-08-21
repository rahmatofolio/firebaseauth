package com.rahmatofolio.mvp.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahmatofolio.mvp.base.mvp.callback.ICallbackListener;
import com.rahmatofolio.mvp.base.mvp.callback.IDbCallbackListener;
import com.rahmatofolio.mvp.data.LoginModel;
import com.rahmatofolio.mvp.data.SignupModel;
import com.rahmatofolio.mvp.data.UserModel;

import static com.rahmatofolio.mvp.impl.DBFields.TBL_USER;

public class StartInteracter{
    private final String TAG = StartInteracter.class.getSimpleName();
    private final FirebaseAuth mAuth;
    private final DatabaseReference mDatabase;

    public StartInteracter() {
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void doLogin(LoginModel loginModel, ICallbackListener<FirebaseUser> callbackListener){
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

    public void createAccount(SignupModel signupModel, ICallbackListener<SignupModel> callbackListener){
        mAuth.createUserWithEmailAndPassword(signupModel.getEmail(),signupModel.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmailAndPassword:success");
                        callbackListener.onSuccess(signupModel);
                    } else {
                        callbackListener.onFailure(task.getException());
                    }
        });
    }

    public void checkUserExisting(FirebaseUser user, IDbCallbackListener<UserModel> callbackListener){
        String userId = user.getUid();
        mDatabase.child(TBL_USER).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Log.d(TAG, "checkUserExisting:success");
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    callbackListener.onSuccess(userModel);
                } else {
                    Log.d(TAG, "checkUserExisting:null");
                    callbackListener.onSuccess(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callbackListener.onFailure(databaseError);
            }
        });
    }

    public void checkUsername(String username, IDbCallbackListener<String> callbackListener){
        mDatabase.child(TBL_USER).child(UserModel.KEY_USERNAME).child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Log.d(TAG, "checkUsername:exist");
                    callbackListener.onSuccess(dataSnapshot.getValue(String.class));
                } else {
                    Log.d(TAG, "checkUsername:not exist");
                    callbackListener.onSuccess(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callbackListener.onFailure(databaseError);
            }
        });
    }

}
