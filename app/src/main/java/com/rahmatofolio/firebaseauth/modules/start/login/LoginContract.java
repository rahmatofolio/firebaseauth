package com.rahmatofolio.firebaseauth.modules.start.login;

import com.google.firebase.auth.AuthCredential;
import com.rahmatofolio.firebaseauth.databinding.ActivityLoginBinding;
import com.rahmatofolio.mvp.base.mvp.presenter.BaseFormPresenter;
import com.rahmatofolio.mvp.base.mvp.view.BaseBindingView;
import com.rahmatofolio.mvp.base.mvp.view.BaseView;
import com.rahmatofolio.mvp.data.LoginModel;

public class LoginContract {
    interface LoginView extends BaseBindingView<ActivityLoginBinding>{
        void goToSignupActivity();

        void goToForgotActivity();

        void gotoMainActivity();
    }

    interface LoginPresenter<T extends BaseView> extends BaseFormPresenter<T> {

        void performLoginTask(LoginModel loginModel);

        void signWithEmailAndPassword(String email, String pass);

        void signInWithCredential(AuthCredential authCredential);

        void checkEmailOnServer(String email);
    }
}
