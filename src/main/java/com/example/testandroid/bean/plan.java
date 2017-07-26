package com.example.testandroid.bean;

import java.io.Serializable;

/**
 * Created by ASUS on 2017/7/20.
 */

public class plan implements Serializable {
    private String tittle;
    private String place;
    private String event;
    private int year;
    private int month;
    private int day;
    private int id;
    private int hour;
    private int minute;

    public plan(int id,String tittle,String place,String event,int year,int month,int day,int hour,int minute){
        this.tittle = tittle;
        this.place = place;
        this.event = event;
        this.year = year;
        this.month= month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public String getPlace() {
        return place;
    }

    public int getId() {
        return id;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getEvent() {
        return event;
    }

    public String getTittle() {
        return tittle;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
