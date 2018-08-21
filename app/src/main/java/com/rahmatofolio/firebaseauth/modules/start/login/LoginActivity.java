package com.rahmatofolio.firebaseauth.modules.start.login;

import android.content.Intent;
import android.view.View;

import com.google.firebase.auth.AuthCredential;
import com.rahmatofolio.firebaseauth.R;
import com.rahmatofolio.firebaseauth.databinding.ActivityLoginBinding;
import com.rahmatofolio.firebaseauth.helper.GoogleClientHelper;
import com.rahmatofolio.firebaseauth.modules.main.MainActivity;
import com.rahmatofolio.firebaseauth.modules.start.forgot.ForgotActivity;
import com.rahmatofolio.firebaseauth.modules.start.signup.SignUpActivity;
import com.rahmatofolio.mvp.base.BaseBindingActivity;
import com.rahmatofolio.mvp.base.mvp.validator.FormValidator;
import com.rahmatofolio.mvp.data.LoginModel;
import com.rahmatofolio.mvp.utils.DialogUtils;
import com.rahmatofolio.mvp.utils.Utils;

public class LoginActivity extends BaseBindingActivity<ActivityLoginBinding> implements LoginContract.LoginView , View.OnClickListener, GoogleClientHelper.CredentialListener<AuthCredential>{

    private LoginModel loginModel;
    private LoginContract.LoginPresenter<LoginContract.LoginView> loginPresetner;
    private GoogleClientHelper googleClientHelper;

    @Override
    protected int attachView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        initPresenter();

        loginModel = new LoginModel();

        googleClientHelper = GoogleClientHelper.newInstance(this);
        googleClientHelper.addGoogleHelper();

        getBinding().setListener(this);
        getBinding().setLoginModel(loginModel);
    }

    @Override
    protected void initToolbar() {
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public void initPresenter() {
        loginPresetner = new LoginPresenterImpl();
        loginPresetner.attachView(this);
        loginPresetner.addValidator(new FormValidator() {

            @Override
            public boolean validate() {
                if (!isValidEmailId(loginModel.getUsername(), getString(R.string.val_enter_email))) {
                    getBinding().edtUsername.requestFocus();
                    return false;
                } else if (!isValidPass(loginModel.getPassword(),getString(R.string.val_enter_password))) {
                    getBinding().edtPassword.requestFocus();
                    return false;
                }
                return true;
            }

            @Override
            public void onValidationSuccess() {
                // if want to perform something on success
            }

            @Override
            public void onValidationError(String errorMessage) {
                DialogUtils.displayToast(LoginActivity.this, errorMessage);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleClientHelper.getCredentialGoogle(requestCode, resultCode, data, this);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void onClick(View v) {
        if (v == getBinding().btnLogin) {
            Utils.hideSoftKeyBoard(this);
            loginPresetner.performLoginTask(loginModel);
        } else if (v == getBinding().tvSignup) {
            goToSignupActivity();
        } else if (v == getBinding().tvForgot) {
            goToForgotActivity();
        } else if (v == getBinding().tvFacebook) {
            showToast("tvFacebook");
        } else if (v == getBinding().tvGoogle) {
            googleClientHelper.signInGoogle();
        }
    }

    @Override
    public void onCredentialGet(AuthCredential credential) {
        loginPresetner.signInWithCredential(credential);
    }

    @Override
    public void goToSignupActivity() {
        Intent s = new Intent(this, SignUpActivity.class);
        startActivity(s);
    }

    @Override
    public void goToForgotActivity() {
        Intent f = new Intent(this, ForgotActivity.class);
        startActivity(f);
    }

    @Override
    public void gotoMainActivity() {
        Intent m = new Intent(this, MainActivity.class);
        m.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(m);
        finish();
    }
}
