package com.rahmatofolio.firebaseauth.modules.start.forgot;


import com.rahmatofolio.firebaseauth.databinding.ActivityForgotBinding;
import com.rahmatofolio.mvp.base.mvp.presenter.BaseFormPresenter;
import com.rahmatofolio.mvp.base.mvp.view.BaseBindingView;
import com.rahmatofolio.mvp.base.mvp.view.BaseView;

public class ForgotContract {

    interface ForgotView extends BaseBindingView<ActivityForgotBinding> {
        void gotoLoginActivity();
    }

    interface ForgotPresenter<T extends BaseView> extends BaseFormPresenter<T> {

        void sendPasswordReset(String email);
    }
}
