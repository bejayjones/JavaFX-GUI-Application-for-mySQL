package com.example.schedulingapp.model;

public class Reports {
    private int month;
    private int year;
    private int count;
    private String type;


    public Reports(int month, int year, String type, int count){
        this.month = month;
        this.year = year;
        this.type = type;
        this.count = count;
    }
    public Reports(int year, int count){
        this.year = year;
        this.count = count;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
