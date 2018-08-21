package com.rahmatofolio.mvp.impl.db;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahmatofolio.mvp.base.mvp.callback.ICallbackListener;
import com.rahmatofolio.mvp.data.UserModel;
import com.rahmatofolio.mvp.impl.DBFields;

import java.util.Objects;

public class AppFirebaseDatabaseHelper implements FirebaseDatabaseHelper {
    private final String TAG = AppFirebaseDatabaseHelper.class.getSimpleName();
    private final DatabaseReference mDatabase;

    public AppFirebaseDatabaseHelper() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public DatabaseReference getTableUser() {
        return mDatabase.child(DBFields.TBL_USER);
    }

    @Override
    public DatabaseReference getTablePost() {
        return mDatabase.child(DBFields.TBL_POST);
    }

    @Override
    public void checkEmailOnServer(String email, ICallbackListener<UserModel> callbackListener) {
        getTableUser().orderByChild(UserModel.KEY_EMAIL).equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    callbackListener.onSuccess(null);
                    return;
                }
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    Log.d(TAG, "checkEmailOnServer:success" + Objects.requireNonNull(userModel).getEmail());
                    callbackListener.onSuccess(userModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "checkEmailOnServer:failed"+databaseError.getMessage());
                callbackListener.onFailure(databaseError.toException());
            }
        });
    }

    @Override
    public void checkUsernameOnServer(String username, ICallbackListener<UserModel> callbackListener) {
        getTableUser().orderByChild(UserModel.KEY_USERNAME).equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    callbackListener.onSuccess(null);
                    return;
                }
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    Log.d(TAG, "checkUsernameOnServer:success" + Objects.requireNonNull(userModel).getUsername());
                    callbackListener.onSuccess(userModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "checkUsernameOnServer:failed"+databaseError.getMessage());
                callbackListener.onFailure(databaseError.toException());
            }
        });
    }

    @Override
    public void updateUserOnServer(UserModel userModel,ICallbackListener<UserModel> callbackListener){
        getTableUser().child(userModel.getUsername()).setValue(userModel).addOnCompleteListener(task -> {
            Log.d(TAG, "updateUserOnServer:success");
            if (task.isSuccessful()) {
                callbackListener.onSuccess(userModel);
            } else {
                callbackListener.onFailure(task.getException());
            }
        });
    }
}
