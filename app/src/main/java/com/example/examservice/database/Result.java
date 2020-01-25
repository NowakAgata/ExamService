package com.example.examservice.database;

public class Result {

    private int id ;
    private int exam_id;
    private int user_id;

    public Result(int id, int exam_id, int user_id) {
        this.id = id;
        this.exam_id = exam_id;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
