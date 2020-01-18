package com.example.examservice.database;

public class Exam {

    private String additional_information ;
    private int created_by ;
    private int duration_of_exam;
    private Date end_date;
    private int exam_id ;
    private boolean learning_required;
    private int max_attempts;
    private int max_questions;
    private String name;
    private Date start_date;



    public Exam(){

    }

    public Exam( int exam_id, String name, String additional_information, boolean learning_required, int created_by,
                int duration_time, int max_attempts, int max_questions,
                 Date start_date, Date end_date) {
        this.additional_information = additional_information;
        this.learning_required = learning_required;
        this.created_by = created_by;
        this.duration_of_exam = duration_time;
        this.exam_id = exam_id;
        this.max_attempts = max_attempts;
        this.max_questions = max_questions;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getAdditional_information() {
        return additional_information;
    }

    public void setAdditional_information(String additional_information) {
        this.additional_information = additional_information;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getDuration_of_exam() {
        return duration_of_exam;
    }

    public void setDuration_of_exam(int duration_of_exam) {
        this.duration_of_exam = duration_of_exam;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public boolean getLearning_required() {
        return learning_required;
    }

    public void setLearning_required(boolean learning_required) {
        this.learning_required = learning_required;
    }

    public int getMax_attempts() {
        return max_attempts;
    }

    public void setMax_attempts(int max_attempts) {
        this.max_attempts = max_attempts;
    }

    public int getMax_questions() {
        return max_questions;
    }

    public void setMax_questions(int max_questions) {
        this.max_questions = max_questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "additional_information='" + additional_information + '\'' +
                ", created_by=" + created_by +
                ", duration_of_exam=" + duration_of_exam +
                ", end_date=" + end_date +
                ", exam_id=" + exam_id +
                ", learning_required=" + learning_required +
                ", max_attempts=" + max_attempts +
                ", max_questions=" + max_questions +
                ", name='" + name + '\'' +
                ", start_date=" + start_date +
                '}';
    }
}
