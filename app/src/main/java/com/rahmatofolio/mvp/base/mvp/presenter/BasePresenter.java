package com.rahmatofolio.mvp.base.mvp.presenter;


import com.rahmatofolio.mvp.base.mvp.view.BaseView;

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void onDestroy();
}