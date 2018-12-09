package com.example.behnam.myapplication.objects;

public class MiBandData {
    private int timestamp;
    private long deviceId;
    private long userId;
    private int rawIntensity;
    private int steps;
    private int rawKind;
    private int heartRate;
    private int sport;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getRawIntensity() {
        return rawIntensity;
    }

    public void setRawIntensity(int rawIntensity) {
        this.rawIntensity = rawIntensity;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getRawKind() {
        return rawKind;
    }

    public void setRawKind(int rawKind) {
        this.rawKind = rawKind;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getSport() {
        return sport;
    }

    public void setSport(int sport) {
        this.sport = sport;
    }
}
