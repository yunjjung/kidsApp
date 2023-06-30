package com.example.kidsapp;
//x,y,z 축이 있는 센서 데이터 들을 위한 클래스
public class ThSensorData {
    private float x;
    private float y;
    private float z;

    public ThSensorData(){};

    public ThSensorData(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        x = x;
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
