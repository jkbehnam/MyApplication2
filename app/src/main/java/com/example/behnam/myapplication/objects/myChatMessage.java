package com.example.behnam.myapplication.objects;

public class myChatMessage {
    private int id;
    private String message;
    private String timestamp;
    private String who_send;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getWho_send() {
        return who_send;
    }

    public void setWho_send(String who_send) {
        this.who_send = who_send;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
