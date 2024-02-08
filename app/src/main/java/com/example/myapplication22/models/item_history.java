package com.example.myapplication22.models;

public class item_history {

    String from,to,date,time,bookedseat,totalcost,seats,ride_id,status;

    public item_history(String from, String to, String date, String time, String bookedseat, String totalcost,String seats,String ride_id,String status) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.bookedseat = bookedseat;
        this.totalcost = totalcost;
        this.seats=seats;
        this.ride_id=ride_id;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public item_history(){}

    public String getRide_id() {
        return ride_id;
    }

    public void setRide_id(String ride_id) {
        this.ride_id = ride_id;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBookedseat() {
        return bookedseat;
    }

    public void setBookedseat(String bookedseat) {
        this.bookedseat = bookedseat;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }
}
