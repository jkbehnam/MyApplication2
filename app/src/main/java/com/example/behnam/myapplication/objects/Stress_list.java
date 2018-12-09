package com.example.behnam.myapplication.objects;

import java.util.ArrayList;

public class Stress_list {
    private String title;
    private int img;
    private String content_message;
    private ArrayList<Stress_list> s_list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getContent_message() {
        return content_message;
    }

    public void setContent_message(String content_message) {
        this.content_message = content_message;
    }

    public ArrayList<Stress_list> getS_list() {
        return s_list;
    }

    public void setS_list(ArrayList<Stress_list> s_list) {
        this.s_list = s_list;
    }
}
