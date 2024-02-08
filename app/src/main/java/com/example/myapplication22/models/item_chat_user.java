package com.example.myapplication22.models;

public class item_chat_user {
    String profile,name,id;
    Boolean online;

    public item_chat_user(String profile, String name, Boolean online, String id) {
        this.profile = profile;
        this.name = name;
        this.online = online;
        this.id = id;
    }
    public item_chat_user(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}
