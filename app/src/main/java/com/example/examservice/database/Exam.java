package com.example.examservice.database;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
    private int percentage_passed_exam;

    private ArrayList<Result> resultsList ;
    private ArrayList<Question> questionsList ;


    public Exam(){

        resultsList = new ArrayList<>();
        questionsList = new ArrayList<>();
    }

    public Exam( int exam_id, String name, String additional_information, boolean learning_required, int created_by,
                int duration_time, int max_attempts, int max_questions, int percentage_passed_exam,
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
        this.percentage_passed_exam = percentage_passed_exam ;
        resultsList = new ArrayList<>();
        questionsList = new ArrayList<>();

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

    public int getPercentage_passed_exam() {
        return percentage_passed_exam;
    }

    public void setPercentage_passed_exam(int percentage_passed_exam) {
        this.percentage_passed_exam = percentage_passed_exam;
    }


    public ArrayList<Result> getResultsList() {
        return resultsList;
    }

    public void setResultsList(ArrayList<Result> resultsList) {
        this.resultsList = resultsList;
    }

    public ArrayList<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(ArrayList<Question> questionsList) {
        this.questionsList = questionsList;
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
                ", percentage_passed_exam=" + percentage_passed_exam +
                '}';
    }

    public boolean isAvailable(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        String nowStr = dateFormat.format(calendar.getTime());

        String startStr = start_date.getDate().substring(0,10);
        String endStr = end_date.getDate().substring(0,10);

        try {
            java.util.Date now = dateFormat.parse(nowStr);
            java.util.Date start = dateFormat.parse(startStr);
            java.util.Date end = dateFormat.parse(endStr);

            if(start.after(now) || end.before(now)){
                return false;
            }

        } catch(ParseException e){
            Log.d("ExamParseDate: ", e.getMessage());
            return false;
        }

        return true ;
    }
}
