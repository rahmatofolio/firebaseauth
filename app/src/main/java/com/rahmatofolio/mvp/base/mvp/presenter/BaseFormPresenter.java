package com.rahmatofolio.mvp.base.mvp.presenter;


import com.rahmatofolio.mvp.base.mvp.validator.IFormValidator;
import com.rahmatofolio.mvp.base.mvp.view.BaseView;

public interface BaseFormPresenter<T extends BaseView> extends BasePresenter<T> {
    void addValidator(IFormValidator formValidator);
}