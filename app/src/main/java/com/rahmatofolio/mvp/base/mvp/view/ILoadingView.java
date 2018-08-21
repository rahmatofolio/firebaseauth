package com.rahmatofolio.mvp.base.mvp.view;

public interface ILoadingView {
    void showProgress(String message);

    void hideProgress();

    void showNoData();

    void showMessage(String errorMessage);

    void showToast(String message);
}
