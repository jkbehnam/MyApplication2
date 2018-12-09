package com.example.behnam.myapplication.objects;

/**
 * Created by behnam on 4/17/2018.
 */

public class HomeRecycleView {
    private String MainText;
    private String SubText;
    private int MianImage;
    public HomeRecycleView(){}
public HomeRecycleView(String maintext, String subtext, int mainimage){
    setMainText(maintext);
    setSubText(subtext);
    setMianImage(mainimage);
}

    public String getMainText() {
        return MainText;
    }

    public void setMainText(String mainText) {
        MainText = mainText;
    }

    public String getSubText() {
        return SubText;
    }

    public void setSubText(String subText) {
        SubText = subText;
    }

    public int getMianImage() {
        return MianImage;
    }

    public void setMianImage(int mianImage) {
        MianImage = mianImage;
    }
}
