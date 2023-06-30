package com.example.kidsapp;
//x,y,z 축이 있는 센서 데이터 들을 위한 클래스
public class ThSensorData {
    private float X;
    private float y;
    private float z;

    public ThSensorData(){};

    public ThSensorData(float x, float y, float z){

    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }




}
