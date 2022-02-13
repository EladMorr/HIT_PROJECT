package com.example.firebase.model;

import com.example.firebase.manager.RolesManager;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class User {

    private FirebaseUser mFbUser;
    private String mId;
    private String mPassword;
    private final String mMail;
    private String mPhoneNumber;
    RolesManager.RoleType mRoleType = RolesManager.RoleType.Employee;

    public User(FirebaseUser fbUser) {
        mFbUser = fbUser;
        mMail = fbUser.getEmail();
        mId = fbUser.getUid();
    }

    public RolesManager.RoleType getRoleType() {
        return mRoleType;
    }

    public void setRoleType(RolesManager.RoleType roleType) {
        this.mRoleType = roleType;
    }

    public String getFullName() {
        return mFbUser.getDisplayName();
    }

    public String getEmail() {
        return mMail;
    }

    public String getPhoneNumber() {
        return mFbUser.getPhoneNumber();
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getPassword() {
        return mPassword;
    }

    public boolean isUserManager() {
        return mRoleType == RolesManager.RoleType.Manager;
    }

}
