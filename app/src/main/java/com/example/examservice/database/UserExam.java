package com.example.examservice.database;

import java.util.ArrayList;

public class UserExam {

    private Date date_of_resolve_exam ;
    private int exam_id ;
    private int user_exam_id ;
    private int user_id ;
    private ArrayList<Result> resultsList ;


    public UserExam(){
        resultsList = new ArrayList<>();

    }


    public UserExam(int exam_id, int user_exam_id, int user_id) {
        this.date_of_resolve_exam = new Date("1970-01-01", "Europe/Berlin");
        this.exam_id = exam_id;
        this.user_exam_id = user_exam_id;
        this.user_id = user_id;
        resultsList = new ArrayList<>();
    }

    public Date getDate_of_resolve_exam() {
        return date_of_resolve_exam;
    }

    public void setDate_of_resolve_exam(Date date_of_resolve_exam) {
        this.date_of_resolve_exam = date_of_resolve_exam;
    }


    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }


    public int getUser_exam_id() {
        return user_exam_id;
    }

    public void setUser_exam_id(int user_exam_id) {
        this.user_exam_id = user_exam_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "UserExam{" +
                "date_of_resolve_exam=" + date_of_resolve_exam +
                ", exam_id=" + exam_id +
                ", user_exam_id=" + user_exam_id +
                ", user_id=" + user_id +
                '}';
    }

    public ArrayList<Result> getResultsList() {
        return resultsList;
    }

    public void setResultsList(ArrayList<Result> resultsList) {
        this.resultsList = resultsList;
    }
}
