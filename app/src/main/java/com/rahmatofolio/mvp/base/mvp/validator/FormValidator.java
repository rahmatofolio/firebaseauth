package com.rahmatofolio.mvp.base.mvp.validator;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

public abstract class FormValidator implements IFormValidator {
    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
    /**
     * Validates the field
     *
     * @return true if not empty text
     */
    public boolean isEmptyField(final String text, String errorMessage) {
        boolean isEmpty = TextUtils.isEmpty(text);
        if (isEmpty && !TextUtils.isEmpty(errorMessage))
            onValidationError(errorMessage);
        return isEmpty;
    }

    /**
     * Validates the Email Id
     *
     * @return true valid email id, false invalid emailid
     */
    public boolean isValidEmailId(final String emailId, String errorMessage) {
        boolean isValid = !isEmptyField(emailId, null) && Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
        if (!isValid && !TextUtils.isEmpty(errorMessage))
            onValidationError(errorMessage);
        return isValid;
    }

    public boolean isValidPass(final String pass, String errorMessage){
        boolean isValid = !isEmptyField(pass,null) && pass.length() > 5;
        if(!isValid && !TextUtils.isEmpty(errorMessage))
            onValidationError(errorMessage);
        return isValid;
    }

    public boolean isValidName(final String name, String errorMessage){
        boolean isValid = !isEmptyField(name, null) && name.length() > 5;
        if (!isValid && !TextUtils.isEmpty(errorMessage))
            onValidationError(errorMessage);
        return isValid;
    }

    public boolean isValidUsername(final String username, String errorMessage){
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        boolean isValid = !isEmptyField(username,null) && username.length() > 5 && pattern.matcher(username).matches();
        if (!isValid && !TextUtils.isEmpty(errorMessage))
            onValidationError(errorMessage);
        return isValid;
    }

    /**
     * Validates the Url
     *
     * @return true valid email id, false invalid emailid
     */
    public boolean isValidUrl(final String url, String errorMessage) {
        boolean isValid = !isEmptyField(url, null) && Patterns.WEB_URL.matcher(url).matches();
        if (!isValid && !TextUtils.isEmpty(errorMessage))
            onValidationError(errorMessage);
        return isValid;
    }

    /**
     * Validates the password
     *
     * @return true valid email id, false invalid emailid
     */
    public boolean isConfirmPasswordSame(final String password, final String confirmPassword, String errorMessage) {
        boolean isSame = password.equals(confirmPassword);
        if (!isSame && !TextUtils.isEmpty(errorMessage))
            onValidationError(errorMessage);
        return isSame;
    }

    /**
     * Validates length
     *
     * @return true if length matches condition
     */
    public boolean isValidLength(final String text, final int maxLength, String errorMessage) {
        boolean hasLength = !TextUtils.isEmpty(text) && text.length() <= maxLength;
        if (!hasLength && !TextUtils.isEmpty(errorMessage))
            onValidationError(errorMessage);
        return hasLength;
    }

}
