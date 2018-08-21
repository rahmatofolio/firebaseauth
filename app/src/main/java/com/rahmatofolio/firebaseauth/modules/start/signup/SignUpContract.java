package com.rahmatofolio.firebaseauth.modules.start.signup;

import com.google.firebase.auth.AuthCredential;
import com.rahmatofolio.firebaseauth.databinding.ActivitySignupBinding;
import com.rahmatofolio.mvp.base.mvp.presenter.BaseFormPresenter;
import com.rahmatofolio.mvp.base.mvp.view.BaseBindingView;
import com.rahmatofolio.mvp.base.mvp.view.BaseView;
import com.rahmatofolio.mvp.data.SignupModel;

public class SignUpContract {
    interface SignUpView extends BaseBindingView<ActivitySignupBinding> {

        void goToCheckUsernameOnServer();

        void goToCreateAccount();

        void goToUpdateUserOnServer();

        void gotoMainActivity();
    }

    interface SignUpPresenter<T extends BaseView> extends BaseFormPresenter<T> {

        void perFormSignUpTask(SignupModel signupModel);

        void checkEmailOnServer(String email);

        void checkUsernameOnServer(String username);

        void createAccount(String email, String pass);

        void updateUserOnServer();

        void signInWithCredential(AuthCredential authCredential);

    }
}
