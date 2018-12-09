package com.example.behnam.myapplication.objects;

public class Sign {
    private Boolean title_chbox=false;
    private String title="";
    private int in_exercise=0;
    private Double duration =0.0;
    private String reaction=" ";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIn_exercise() {
        return in_exercise;
    }

    public void setIn_exercise(int in_exercise) {
        this.in_exercise = in_exercise;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public Boolean getTitle_chbox() {
        return title_chbox;
    }

    public void setTitle_chbox(Boolean title_chbox) {
        this.title_chbox = title_chbox;
    }
}
