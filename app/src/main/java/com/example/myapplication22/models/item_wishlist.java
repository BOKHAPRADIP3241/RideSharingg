package com.example.myapplication22.models;

public class item_wishlist {
    String from,to,date,docId;

    public  item_wishlist(){}

    public item_wishlist(String from, String to, String date,String docId) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
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
}
