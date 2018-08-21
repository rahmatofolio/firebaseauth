package com.rahmatofolio.firebaseauth.modules.start.signup;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.rahmatofolio.firebaseauth.utils.Slug;
import com.rahmatofolio.mvp.base.mvp.callback.ICallbackListener;
import com.rahmatofolio.mvp.base.mvp.validator.IFormValidator;
import com.rahmatofolio.mvp.data.SignupModel;
import com.rahmatofolio.mvp.data.UserModel;
import com.rahmatofolio.mvp.impl.auth.AppFirebaseAuthHelper;
import com.rahmatofolio.mvp.impl.auth.FirebaseAuthHelper;
import com.rahmatofolio.mvp.impl.db.AppFirebaseDatabaseHelper;
import com.rahmatofolio.mvp.impl.db.FirebaseDatabaseHelper;
import com.rahmatofolio.mvp.impl.pref.AppPrefHelper;
import com.rahmatofolio.mvp.impl.pref.PrefHelper;

import java.util.Objects;

public class SignUpPresenterImpl implements SignUpContract.SignUpPresenter<SignUpContract.SignUpView>{
    private SignUpContract.SignUpView view;
    private IFormValidator formValidator;

    private final PrefHelper prefHelper;
    private final FirebaseDatabaseHelper firebaseDatabaseHelper;
    private final FirebaseAuthHelper firebaseAuthHelper;

    public SignUpPresenterImpl() {
        this.prefHelper = new AppPrefHelper();
        this.firebaseAuthHelper = new AppFirebaseAuthHelper();
        this.firebaseDatabaseHelper = new AppFirebaseDatabaseHelper();
    }

    @Override
    public void addValidator(IFormValidator validator) {
        formValidator = validator;
    }

    @Override
    public void attachView(SignUpContract.SignUpView view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void perFormSignUpTask(SignupModel signupModel) {
        if (formValidator != null && formValidator.validate()) {
            firebaseAuthHelper.signOutFirebase();
            prefHelper.saveUserModel(signupModel);
            checkEmailOnServer(signupModel.getEmail());
        }
    }

    @Override
    public void checkEmailOnServer(String email) {

        view.showProgress("Checking Email...");
        firebaseDatabaseHelper.checkEmailOnServer(email, new ICallbackListener<UserModel>() {
            @Override
            public void onSuccess(UserModel data) {
                view.hideProgress();
                if (data != null) {
                    view.showMessage("Email anda telah terdaftar");
                    view.getBinding().edtEmail.requestFocus();
                } else {
                    view.goToCheckUsernameOnServer();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgress();
                formValidator.onValidationError(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void checkUsernameOnServer(String username) {

        view.showProgress("Checking Existing User...");
        firebaseDatabaseHelper.checkUsernameOnServer(username, new ICallbackListener<UserModel>() {
            @Override
            public void onSuccess(UserModel data) {
                view.hideProgress();
                if (data != null) {
                    view.showMessage("Username telah terpakai");
                    view.getBinding().edtUsername.requestFocus();
                } else {
                    view.goToCreateAccount();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgress();
                formValidator.onValidationError(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void createAccount(String email,String pass){

        view.showProgress("Creating Account...");
        firebaseAuthHelper.createAccount(email,pass, new ICallbackListener<Task<AuthResult>>() {
            @Override
            public void onSuccess(Task<AuthResult> data) {
                view.hideProgress();
                if (data.isSuccessful()) {
                    FirebaseUser user = data.getResult().getUser();
                    prefHelper.saveUserModel(user);
                    prefHelper.setName("");

                    view.goToUpdateUserOnServer();

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
    public void updateUserOnServer(){
        UserModel userModel = prefHelper.getUserModel();
        view.showProgress("Updating User...");
        firebaseDatabaseHelper.updateUserOnServer(userModel, new ICallbackListener<UserModel>() {
            @Override
            public void onSuccess(UserModel data) {
                view.hideProgress();
                view.showMessage("Signup successfully");
                view.gotoMainActivity();
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgress();
                view.showMessage(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void signInWithCredential(AuthCredential authCredential){
        view.showProgress("Checking credential...");
        firebaseAuthHelper.signOutFirebase();
        firebaseAuthHelper.signInWithCredential(authCredential, new ICallbackListener<Task<AuthResult>>() {
            @Override
            public void onSuccess(Task<AuthResult> data) {
                view.hideProgress();
                if (data.isSuccessful()) {
                    FirebaseUser user = data.getResult().getUser();
                    prefHelper.setUsername(Slug.makeUsername(Objects.requireNonNull(user.getEmail())));
                    prefHelper.saveUserModel(user);

                    view.goToUpdateUserOnServer();

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

}
