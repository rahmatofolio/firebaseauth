package com.rahmatofolio.mvp.base.mvp.view;

import android.databinding.ViewDataBinding;

public interface BaseBindingView<T extends ViewDataBinding> extends BaseView {
    T getBinding();
}
