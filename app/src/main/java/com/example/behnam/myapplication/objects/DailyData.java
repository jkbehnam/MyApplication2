package com.example.behnam.myapplication.objects;

public class DailyData {
    private String time;
    private String answer_time;
    private double up_pb;
    private double down_pb;
    private double gluose;
    private double et_weight;
    private double cigarette;
    private double sleep;
    private double steps;
    private double last_heart_rate;
    private double heart_rate_max;
    private double heart_rate_min;

    public double getUp_pb() {
        return up_pb;
    }

    public void setUp_pb(double up_pb) {
        this.up_pb = up_pb;
    }

    public double getDown_pb() {
        return down_pb;
    }

    public void setDown_pb(double down_pb) {
        this.down_pb = down_pb;
    }

    public double getGluose() {
        return gluose;
    }

    public void setGluose(double gluose) {
        this.gluose = gluose;
    }

    public double getEt_weight() {
        return et_weight;
    }

    public void setEt_weight(double et_weight) {
        this.et_weight = et_weight;
    }

    public double getCigarette() {
        return cigarette;
    }

    public void setCigarette(double cigarette) {
        this.cigarette = cigarette;
    }

    public double getSleep() {
        return sleep;
    }

    public void setSleep(double sleep) {
        this.sleep = sleep;
    }

    public double getSteps() {
        return steps;
    }

    public void setSteps(double steps) {
        this.steps = steps;
    }

    public double getHeart_rate_max() {
        return heart_rate_max;
    }

    public void setHeart_rate_max(double heart_rate) {
        this.heart_rate_max = heart_rate;
    }

    public double getHeart_rate_min() {
        return heart_rate_min;
    }

    public void setHeart_rate_min(double heart_rate_min) {
        this.heart_rate_min = heart_rate_min;
    }

    public double getLast_heart_rate() {
        return last_heart_rate;
    }

    public void setLast_heart_rate(double last_heart_rate) {
        this.last_heart_rate = last_heart_rate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAnswer_time() {
        return answer_time;
    }

    public void setAnswer_time(String answer_time) {
        this.answer_time = answer_time;
    }
}
