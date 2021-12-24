package com.example.firebase;

class User {
    private String fullName;
    private String Email;
    private String phoneNumber;
    private String password;

    public User(String fullName, String email, String phoneNumber, String password) {
        this.fullName = fullName;
        this.Email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    public User(){}

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
