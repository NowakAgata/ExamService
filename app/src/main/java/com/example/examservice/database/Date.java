package com.example.examservice.database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
    String date;
    String timezone ;
    int timezone_type ;


    public Date() {
        Calendar calendar = new GregorianCalendar();
        this.timezone = calendar.getTimeZone().getID();
        this.timezone_type = 3;
        this.date = dateFormat.format(calendar.getTime());

    }

    public Date(String date, String timezone){
        date = date + " 00:00:00.000000";
        this.date = date;
        this.timezone = timezone;
        this.timezone_type = 3;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getTimezone_type() {
        return timezone_type;
    }

    public void setTimezone_type(int timezone_type) {
        this.timezone_type = timezone_type;
    }

    @Override
    public String toString() {
        return "Date{" +
                "date='" + date + '\'' +
                ", timezone=" + timezone +
                ", timezone_type='" + timezone_type + '\'' +
                '}';
    }
}
