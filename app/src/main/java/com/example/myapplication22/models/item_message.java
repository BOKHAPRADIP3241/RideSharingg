package com.example.myapplication22.models;

public class item_message {
    String msg,sendBy;

    public item_message(String msg, String sendBy) {
        this.msg = msg;
        this.sendBy = sendBy;
    }
    public item_message(){}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }
}
