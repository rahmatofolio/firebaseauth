package com.rahmatofolio.firebaseauth.modules.start.login;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.rahmatofolio.mvp.base.mvp.callback.ICallbackListener;
import com.rahmatofolio.mvp.base.mvp.validator.IFormValidator;
import com.rahmatofolio.mvp.data.LoginModel;
import com.rahmatofolio.mvp.data.UserModel;
import com.rahmatofolio.mvp.impl.auth.AppFirebaseAuthHelper;
import com.rahmatofolio.mvp.impl.auth.FirebaseAuthHelper;
import com.rahmatofolio.mvp.impl.db.AppFirebaseDatabaseHelper;
import com.rahmatofolio.mvp.impl.db.FirebaseDatabaseHelper;
import com.rahmatofolio.mvp.impl.pref.AppPrefHelper;
import com.rahmatofolio.mvp.impl.pref.PrefHelper;

import java.util.Objects;

public class LoginPresenterImpl implements LoginContract.LoginPresenter<LoginContract.LoginView>{

    private IFormValidator formValidator;
    private LoginContract.LoginView view;

    private final PrefHelper prefHelper;
    private final FirebaseDatabaseHelper firebaseDatabaseHelper;
    private final FirebaseAuthHelper firebaseAuthHelper;

    public LoginPresenterImpl() {
        this.prefHelper = new AppPrefHelper();
        this.firebaseAuthHelper = new AppFirebaseAuthHelper();
        this.firebaseDatabaseHelper = new AppFirebaseDatabaseHelper();
    }

    @Override
    public void addValidator(IFormValidator formValidator) {
        this.formValidator = formValidator;
    }

    @Override
    public void attachView(LoginContract.LoginView view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void performLoginTask(LoginModel loginModel) {
        if (formValidator != null && formValidator.validate()) {
            firebaseAuthHelper.signOutFirebase();
            signWithEmailAndPassword(loginModel.getUsername(),loginModel.getPassword());
        }
    }

    @Override
    public void signWithEmailAndPassword(String email, String pass){
        view.showProgress("Signing...");
        firebaseAuthHelper.signWithEmailAndPassword(email, pass, new ICallbackListener<Task<AuthResult>>() {
            @Override
            public void onSuccess(Task<AuthResult> data) {
                view.hideProgress();
                if (data.isSuccessful()) {
                    view.showMessage("Login Successfully");
                } else {
                    view.showMessage(Objects.requireNonNull(data.getException()).getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgress();
                view.showMessage(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void signInWithCredential(AuthCredential authCredential) {
        view.showProgress("Checking credential...");
        firebaseAuthHelper.signOutFirebase();
        firebaseAuthHelper.signInWithCredential(authCredential, new ICallbackListener<Task<AuthResult>>() {
            @Override
            public void onSuccess(Task<AuthResult> data) {
                view.hideProgress();
                if (data.isSuccessful()) {
                    FirebaseUser user = data.getResult().getUser();
                    checkEmailOnServer(user.getEmail());
                } else {
                    view.showMessage(Objects.requireNonNull(data.getException()).getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgress();
                view.showMessage(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void checkEmailOnServer(String email){
        view.showProgress("Checking Email...");
        firebaseDatabaseHelper.checkEmailOnServer(email, new ICallbackListener<UserModel>() {
            @Override
            public void onSuccess(UserModel data) {
                view.hideProgress();
                if (data != null) {
                    prefHelper.saveUserModel(data);
                    view.showMessage("Login successfully");
                    view.gotoMainActivity();
                } else {
                    view.showMessage("Anda belum terdaftar");
                    view.goToSignupActivity();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgress();
                formValidator.onValidationError(t.getLocalizedMessage());
            }
        });
    }
}
