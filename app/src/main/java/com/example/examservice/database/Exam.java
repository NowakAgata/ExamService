package com.example.examservice.database;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;

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

    private int userExamId ;
    private Date exam_resolved ;

    private ArrayList<Result> resultsList ;
    private ArrayList<Question> questionsList ;
    private boolean has_questions ;

    public Exam(){

        resultsList = new ArrayList<>();
        questionsList = new ArrayList<>();
        has_questions = false;
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
        has_questions = false;

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

    public int getUserExamId() {
        return userExamId;
    }

    public void setUserExamId(int userExamId) {
        this.userExamId = userExamId;
    }

    public Date getExam_resolved() {
        return exam_resolved;
    }

    public void setExam_resolved(Date exam_resolved) {
        this.exam_resolved = exam_resolved;
    }

    public boolean isHas_questions() {
        return has_questions;
    }

    public void setHas_questions(boolean has_questions) {
        this.has_questions = has_questions;
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
                ", userExamId=" + userExamId +
                ", resultsList=" + resultsList.toString() +
                ", questionsList=" + questionsList.toString() +
                '}';
    }

    public boolean isAvailable(int i){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        String nowStr = dateFormat.format(calendar.getTime());

        String startStr = start_date.getDate().substring(0,10);
        String endStr = end_date.getDate().substring(0,10);
        String limitStr = "2005-01-01";
        String resolvedStr = exam_resolved.getDate().substring(0,10);

        try {
            java.util.Date now = dateFormat.parse(nowStr);
            java.util.Date start = dateFormat.parse(startStr);
            java.util.Date end = dateFormat.parse(endStr);
            java.util.Date limit = dateFormat.parse(limitStr);
            java.util.Date resolved = dateFormat.parse(resolvedStr);

            if(start.after(now) || end.before(now)){
                Log.d("Exam", "Zła data");
                return false;
            }else if( resolved.after(limit)){
                Log.d("Exam", "Rozwiązany");
                return false;
            }

        } catch(ParseException e){
            Log.d("ExamParseDate: ", e.getMessage());
            return false;
        }

        if(resultsList.size() >= max_attempts){
            Log.d("Exam", "Zła liczba podejść");
            return false;
        }
        return true ;
    }

    public boolean isPassed(int questions, int points){

        double score = ((double)points/(double)questions)*100;
        return (score >= percentage_passed_exam) ;
    }

    public void addResult(Result result){
        resultsList.add(result);
    }

    public int getResultListSize(){
        return resultsList.size();
    }
}
