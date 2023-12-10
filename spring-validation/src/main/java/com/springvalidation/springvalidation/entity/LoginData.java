package com.springvalidation.springvalidation.entity;

import jakarta.validation.constraints.*;

public class LoginData {

    @NotBlank(message = "User Name can not be empty !!")
    @Size(min=3,max=12,message = "User name must be between 3 - 12 characters !!")
    private String userName;



    //@Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$", message = "Invalid Email !!" )
    @Email(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$")
    private String email;

    @AssertTrue(message = "Must agree terms and conditions")
    private boolean agreed;

    public boolean isAgreed() {
        return agreed;
    }

    public void setAgreed(boolean agreed) {
        this.agreed = agreed;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LoginData(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public LoginData() {
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
