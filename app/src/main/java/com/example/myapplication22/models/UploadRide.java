package com.example.myapplication22.models;

import java.sql.Time;
import java.util.Date;

public class UploadRide {
    private String userId,docId,from,to,carName;
    private int seats,cost;
    private Date date;
    private  Time time;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setSeat(int seat) {
        this.seats = seat;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getUserId() {
        return userId;
    }

    public String getDocId() {
        return docId;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public String getCarName() {
        return carName;
    }

    public int getSeat() {
        return seats;
    }

    public int getCost() {
        return cost;
    }
}
