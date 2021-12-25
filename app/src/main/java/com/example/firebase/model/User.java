package com.example.firebase.model;

import com.example.firebase.manager.RolesManager;
import com.google.firebase.auth.FirebaseUser;

public class User {
    private FirebaseUser mFbUser;
    private String mPhoneNumber;
    RolesManager.RoleType mRoleType;

    public User(FirebaseUser fbUser, RolesManager.RoleType roleType) {
        mRoleType = roleType;
        mFbUser = fbUser;
    }

    public String getFullName() {
        return mFbUser.getDisplayName();
    }

    public String getEmail() {
        return mFbUser.getEmail();
    }

    public String getPhoneNumber() {
        return mFbUser.getPhoneNumber();
    }
}
