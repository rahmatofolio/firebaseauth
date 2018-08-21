package com.rahmatofolio.firebaseauth.modules.start.forgot;

import com.google.android.gms.tasks.Task;
import com.rahmatofolio.mvp.base.mvp.callback.ICallbackListener;
import com.rahmatofolio.mvp.base.mvp.validator.IFormValidator;
import com.rahmatofolio.mvp.impl.auth.AppFirebaseAuthHelper;
import com.rahmatofolio.mvp.impl.auth.FirebaseAuthHelper;

import java.util.Objects;

public class ForgotPresenterImpl implements ForgotContract.ForgotPresenter<ForgotContract.ForgotView> {

    private IFormValidator formValidator;
    private ForgotContract.ForgotView view;

    private final FirebaseAuthHelper firebaseAuthHelper;

    public ForgotPresenterImpl() {
        this.firebaseAuthHelper = new AppFirebaseAuthHelper();
    }

    @Override
    public void addValidator(IFormValidator validator) {
        formValidator = validator;
    }

    @Override
    public void attachView(ForgotContract.ForgotView view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void sendPasswordReset(String email){
        if (formValidator != null && formValidator.validate()) {
            view.showProgress("Please wait...");
            firebaseAuthHelper.sendPasswordReset(email, new ICallbackListener<Task<Void>>() {
                @Override
                public void onSuccess(Task<Void> data) {
                    view.hideProgress();
                    if (data.isSuccessful()) {
                        view.showMessage("Check your inbox email");
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
}
