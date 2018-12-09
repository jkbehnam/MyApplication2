package com.example.behnam.myapplication.objects;

public class AudioItem {
    private String title;
    private String name;
    private String content;
    private String url;
    private String path;
    private int audioResId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getAudioResId() {
        return audioResId;
    }

    public void setAudioResId(int audioResId) {
        this.audioResId = audioResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
