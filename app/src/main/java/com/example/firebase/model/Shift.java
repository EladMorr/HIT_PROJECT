package com.example.firebase.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Shift {

    private String mId;
    private List<User> mShiftEmployees;
    private User mShiftManager;

    public Shift() {
        mShiftEmployees = new ArrayList<>();
        mId = UUID.randomUUID().toString();
    }

    public String getId() {
        return mId;
    }

    public void addEmployee(User employee){
        mShiftEmployees.add(employee);
    }

    public void removeEmployee(User employee){
        mShiftEmployees.remove(employee);
    }

    public User getShiftManager() {
        return mShiftManager;
    }

    public void setShiftManager(User shiftManager) {
        this.mShiftManager = shiftManager;
    }
}
