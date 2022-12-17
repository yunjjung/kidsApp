package com.example.kidsapp;

/**
 * 사용자 계정 정보 모델 클래스
 */
public class UserAccount
{
    private String idToken; //Firebase Uid(고유 토큰 정보)
    private String emailId;
    private String password;

    public String getEmailId() {
        return emailId;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserAccount() {}


}
