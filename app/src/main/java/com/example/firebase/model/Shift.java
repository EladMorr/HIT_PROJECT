package com.example.firebase.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Shift {

    private String mId;
    private List<User> mShiftEmployees;
    private User mShiftManager;
    private long mStartTime;
    private long mEndTime;
    private int mNumOFEmployees;

    public Shift() {
        mShiftEmployees = new ArrayList<>();
        mId = UUID.randomUUID().toString();
    }

    public String getId() {
        return mId;
    }

    public void setEndTime(long endTime) {
        this.mEndTime = endTime;
    }

    public void setStartTime(long startTime) {
        this.mStartTime = startTime;
    }

    public void setNumOFEmployees(int numOFEmployees) {
        this.mNumOFEmployees = numOFEmployees;
    }

    public int getNumOFEmployees() {
        return mNumOFEmployees;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public long getEndTime() {
        return mEndTime;
    }


    public void addEmployee(User employee) {
        mShiftEmployees.add(employee);
    }

    public void removeEmployee(User employee) {
        mShiftEmployees.remove(employee);
    }

    public User getShiftManager() {
        return mShiftManager;
    }

    public void setShiftManager(User shiftManager) {
        this.mShiftManager = shiftManager;
    }

    public List<User> getShiftEmployees() {
        return mShiftEmployees;
    }

}
