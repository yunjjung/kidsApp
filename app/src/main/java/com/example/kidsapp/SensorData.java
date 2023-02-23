package com.example.kidsapp;

import java.time.LocalDate;
import java.time.LocalDateTime;

//4-자이로, 19-심박수 , 21-걸음수
public class SensorData {
    private float sensor;
    private LocalDateTime time;

    public float getSensor() {
        return sensor;
    }

    public void setSensor(float sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }


//    private float heart;
//    private float stepCount;
//    private String gyro;


//    public float getHeart() {
//        return heart;
//    }
//
//    public void setHeart(int heart) {
//        this.heart = heart;
//    }
//
//    public float getStepCount() {
//        return stepCount;
//    }
//
//    public void setStepCount(int stepCount) {
//        this.stepCount = stepCount;
//    }
//
//    public String getGyro() {
//        return gyro;
//    }
//
//    public void setGyro(String gyro) {
//        this.gyro = gyro;
//    }






}
