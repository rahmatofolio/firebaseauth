package com.rahmatofolio.mvp.base.mvp.callback;

public interface ICallbackListener<T> {
    void onSuccess(T data);

    void onFailure(Throwable t);

}
