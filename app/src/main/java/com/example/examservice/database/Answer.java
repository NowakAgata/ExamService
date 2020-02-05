package com.example.examservice.database;

public class Answer {

    public String content ;
    public int exam_id ;
    public int id;
    public boolean is_true ;
    public int question_id ;
    private boolean is_active ;

    public Answer(){
        is_active = false;
    }

    public Answer(String content, int exam_id, int id, boolean is_true, int question_id) {
        this.content = content;
        this.exam_id = exam_id;
        this.id = id;
        this.is_true = is_true;
        this.question_id = question_id;
        this.is_active = false ;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public boolean getIs_true() {
        return is_true;
    }

    public void setIs_true(boolean is_true) {
        this.is_true = is_true;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "content='" + content + '\'' +
                ", exam_id=" + exam_id +
                ", id=" + id +
                ", is_true=" + is_true +
                ", question_id=" + question_id +
                '}';
    }
}
