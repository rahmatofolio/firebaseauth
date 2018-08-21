package com.rahmatofolio.mvp.base.mvp.view;

public interface BaseView extends ILoadingView {

    void initPresenter();

    void destroy();
}
