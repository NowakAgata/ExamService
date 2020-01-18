package com.example.examservice.database;


public class Question {

    public String content ;
    public int exam_id ;
    public int id ;
    public int max_answers;
    public String name_of_file ;

    public Question(){

    }

    public Question(String content, int exam_id, int id, int max_answers, String name_of_file) {
        this.content = content;
        this.exam_id = exam_id;
        this.id = id;
        this.max_answers = max_answers;
        this.name_of_file = name_of_file;
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

    public String getName_of_file() {
        return name_of_file;
    }

    public void setName_of_file(String name_of_file) {
        this.name_of_file = name_of_file;
    }

    @Override
    public String toString() {
        return "Question{" +
                "content='" + content + '\'' +
                ", exam_id=" + exam_id +
                ", id=" + id +
                ", max_answers=" + max_answers +
                ", name_of_file='" + name_of_file + '\'' +
                '}';
    }
}
