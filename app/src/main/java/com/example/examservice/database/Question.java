package com.example.examservice.database;


import java.util.ArrayList;

public class Question {

    public String content ;
    public int exam_id ;
    public int id ;
    public int max_answers;
    private ArrayList<Answer> listOfAnswers ;

    public Question(){
        listOfAnswers = new ArrayList<>() ;
    }

    public Question(String content, int exam_id, int id, int max_answers) {
        this.content = content;
        this.exam_id = exam_id;
        this.id = id;
        this.max_answers = max_answers;
        listOfAnswers = new ArrayList<>() ;
    }

    public void addAnswer(Answer answer){
        listOfAnswers.add(answer) ;
    }

    public ArrayList<Answer> getAnswersList(){
        return listOfAnswers ;
    }

    public void setListOfAnswers(ArrayList<Answer> list){
        this.listOfAnswers = list ;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMax_answers() {
        return max_answers;
    }

    public void setMax_answers(int max_answers) {
        this.max_answers = max_answers;
    }


    public int getAnswersCount(){
        return listOfAnswers.size();
    }
    @Override
    public String toString() {
        return "Question{" +
                "content='" + content + '\'' +
                ", exam_id=" + exam_id +
                ", id=" + id +
                ", max_answers=" + max_answers +
                '}';
    }
}
