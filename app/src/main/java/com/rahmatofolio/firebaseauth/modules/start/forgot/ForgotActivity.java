package com.rahmatofolio.firebaseauth.modules.start.forgot;

import android.content.Intent;
import android.view.View;

import com.rahmatofolio.firebaseauth.R;
import com.rahmatofolio.firebaseauth.databinding.ActivityForgotBinding;
import com.rahmatofolio.firebaseauth.modules.start.login.LoginActivity;
import com.rahmatofolio.mvp.base.BaseBindingActivity;
import com.rahmatofolio.mvp.base.mvp.validator.FormValidator;
import com.rahmatofolio.mvp.data.ForgotModel;
import com.rahmatofolio.mvp.utils.DialogUtils;
import com.rahmatofolio.mvp.utils.Utils;

public class ForgotActivity extends BaseBindingActivity<ActivityForgotBinding> implements ForgotContract.ForgotView, View.OnClickListener {

    private ForgotModel forgotModel;
    private ForgotContract.ForgotPresenter<ForgotContract.ForgotView> forgotPresenter;

    @Override
    protected int attachView() {
        return R.layout.activity_forgot;
    }

    @Override
    protected void initView() {
        initPresenter();

        forgotModel = new ForgotModel();

        getBinding().setListener(this);
        getBinding().setForgotModel(forgotModel);
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
        forgotPresenter = new ForgotPresenterImpl();
        forgotPresenter.attachView(this);
        forgotPresenter.addValidator(new FormValidator() {
            @Override
            public boolean validate() {
                if (!isValidEmailId(forgotModel.getEmail(),getString(R.string.val_enter_email))) {
                    getBinding().edtEmail.requestFocus();
                    return false;
                }
                return true;
            }

            @Override
            public void onValidationSuccess() {

            }

            @Override
            public void onValidationError(String errorMessage) {
                DialogUtils.displayToast(ForgotActivity.this, errorMessage);
            }
        });
    }

    @Override
    public void destroy() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void onClick(View v) {
        if (v == getBinding().btnForgot) {
            Utils.hideSoftKeyBoard(this);
            forgotPresenter.sendPasswordReset(forgotModel.getEmail());
        } else if (v == getBinding().tvSignIn) {
            gotoLoginActivity();
        }
    }

    @Override
    public void gotoLoginActivity() {
        Intent l = new Intent(this, LoginActivity.class);
        startActivity(l);
        finish();
    }
}
