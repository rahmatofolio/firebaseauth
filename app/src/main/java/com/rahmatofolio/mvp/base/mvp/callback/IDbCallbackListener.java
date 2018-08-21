package com.rahmatofolio.mvp.base.mvp.callback;

import com.google.firebase.database.DatabaseError;

public interface IDbCallbackListener<T> {
    void onSuccess(T data);

    void onFailure(DatabaseError t);

}
