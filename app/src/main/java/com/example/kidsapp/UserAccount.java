package com.example.kidsapp;

/**
 * 사용자 계정 정보 모델 클래스
 */
public class UserAccount
{
    private String idToken; //Firebase Uid(고유 토큰 정보)
    private String nickname; //사용자 이름
    private String emailId;
    private String password;
    private SensorData sensorData;

    public SensorData getSensorData() {
        return sensorData;
    }

    public void setSensorData(SensorData sensorData) {
        this.sensorData = sensorData;
    }



    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmailId() {
        return emailId;
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

    public UserAccount() {} //빈 생성자 없으면 안됨.

    public UserAccount(String nickname, String emailId,String password){
        this.nickname = nickname;
        this.emailId = emailId;
        this.password = password;
    }


}
