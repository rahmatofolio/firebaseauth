package com.rahmatofolio.firebaseauth.modules.start.signup;

import android.content.Intent;
import android.view.View;
import android.widget.RadioGroup;

import com.google.firebase.auth.AuthCredential;
import com.rahmatofolio.firebaseauth.R;
import com.rahmatofolio.firebaseauth.databinding.ActivitySignupBinding;
import com.rahmatofolio.firebaseauth.helper.GoogleClientHelper;
import com.rahmatofolio.firebaseauth.modules.main.MainActivity;
import com.rahmatofolio.mvp.base.BaseBindingActivity;
import com.rahmatofolio.mvp.base.mvp.validator.FormValidator;
import com.rahmatofolio.mvp.data.SignupModel;
import com.rahmatofolio.mvp.utils.DialogUtils;
import com.rahmatofolio.mvp.utils.Utils;

public class SignUpActivity extends BaseBindingActivity<ActivitySignupBinding> implements SignUpContract.SignUpView,
        View.OnClickListener, RadioGroup.OnCheckedChangeListener, GoogleClientHelper.CredentialListener<AuthCredential> {
    private SignupModel signupModel;
    private SignUpContract.SignUpPresenter<SignUpContract.SignUpView> signUpPresenter;
    private GoogleClientHelper googleClientHelper;

    @Override
    protected int attachView() {
        return R.layout.activity_signup;
    }

    @Override
    protected void initView() {
        initPresenter();

        signupModel = new SignupModel();

        googleClientHelper = GoogleClientHelper.newInstance(this);
        googleClientHelper.addGoogleHelper();

        getBinding().setListener(this);
        getBinding().setHandler(this);
        getBinding().setSignupModel(signupModel);
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
    public void onClick(View v) {
        if (v == getBinding().btnSignup) {
            Utils.hideSoftKeyBoard(this);
            signUpPresenter.perFormSignUpTask(signupModel);
        } else if (v == getBinding().tvGoogle) {
            googleClientHelper.signInGoogle();
        }
    }

    @Override
    public void initPresenter() {
        signUpPresenter = new SignUpPresenterImpl();
        signUpPresenter.attachView(this);
        signUpPresenter.addValidator(new FormValidator() {

            @Override
            public boolean validate() {
                if (!isValidName(signupModel.getName(), getString(R.string.val_enter_name))) {
                    getBinding().edtName.requestFocus();
                    return false;
                } else if (!isValidEmailId(signupModel.getEmail(), getString(R.string.val_enter_email))) {
                    getBinding().edtEmail.requestFocus();
                    return false;
                } else if (isEmptyField(signupModel.getGender(), getString(R.string.val_enter_gender))) {
                    return false;
                } else if (!isValidUsername(signupModel.getUsername(), getString(R.string.val_enter_username))) {
                    getBinding().edtUsername.requestFocus();
                    return false;
                } else if (!isValidPass(signupModel.getPassword(),getString(R.string.val_enter_password))) {
                    getBinding().edtPassword.requestFocus();
                    return false;
                }
                return true;
            }

            @Override
            public void onValidationSuccess() {

            }

            @Override
            public void onValidationError(String errorMessage) {
                DialogUtils.displayToast(SignUpActivity.this,errorMessage);
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
    public void onCredentialGet(AuthCredential credential) {
        signUpPresenter.signInWithCredential(credential);
    }

    @Override
    public void goToCheckUsernameOnServer() {
        signUpPresenter.checkUsernameOnServer(signupModel.getUsername());
    }

    @Override
    public void goToCreateAccount() {
        signUpPresenter.createAccount(signupModel.getEmail(),signupModel.getPassword());
    }

    @Override
    public void goToUpdateUserOnServer() {
        signUpPresenter.updateUserOnServer();
    }

    @Override
    public void gotoMainActivity() {
        //goto main activity
        Intent m = new Intent(this, MainActivity.class);
        m.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(m);
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rdMan) {
            signupModel.setGender("man");
        } else if (checkedId == R.id.rdGirl) {
            signupModel.setGender("girl");
        }
    }
}
