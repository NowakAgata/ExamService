package com.example.examservice.database;

import androidx.annotation.NonNull;

public class Result {

    private int id ;
    private int points;
    private boolean is_passed;
    private int exam_id;

    public Result(){

    }

    public Result(int id, int points, boolean is_passed, int exam_id) {
        this.id = id;
        this.points = points;
        this.is_passed = is_passed;
        this.exam_id = exam_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean getIs_passed() {
        return is_passed;
    }

    public void setIs_passed(boolean is_passed) {
        this.is_passed = is_passed;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", points=" + points +
                ", is_passed=" + is_passed +
                ", exam_id=" + exam_id +
                '}';
    }
}
