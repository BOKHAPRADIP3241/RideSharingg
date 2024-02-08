package com.example.myapplication22.models;

public class item_ridelist {

    String from,to,date,time,seats,cost,car,ride_id,userId,booked;


    public item_ridelist(String from, String to, String date, String time, String seats, String cost, String car, String ride_id, String userId,String booked) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.seats = seats;
        this.cost = cost;
        this.car = car;
        this.ride_id = ride_id;
        this.userId = userId;
        this.booked = booked;
    }
    item_ridelist(){}
    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getSeats() {
        return seats;
    }

    public String getCost() {
        return cost;
    }

    public String getRide_id() {
        return ride_id;
    }

    public String getCar() { return car; }

    public String getUserId() { return userId; }

    public String getBooked() { return booked; }
}
