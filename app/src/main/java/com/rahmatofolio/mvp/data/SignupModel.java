package com.rahmatofolio.mvp.data;

/**
 * Created in BindingConstraintMVP-Demo on 11/01/17.
 */

public class SignupModel {
    private String name,email,gender,username, password, referral;

    public SignupModel() {
    }

    public SignupModel(String name, String email, String gender, String username, String password, String referral) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.referral = referral;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }
}
