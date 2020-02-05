package com.example.examservice.database;

public class LearningMaterialsGroupExam {

    private int exam_id;
    private int id ;
    private int learning_materials_group_id;

    public LearningMaterialsGroupExam(){

    }

    public LearningMaterialsGroupExam(int exam_id, int id, int learning_materials_group_id) {
        this.exam_id = exam_id;
        this.id = id;
        this.learning_materials_group_id = learning_materials_group_id;
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

    public int getLearning_materials_group_id() {
        return learning_materials_group_id;
    }

    public void setLearning_materials_group_id(int learning_materials_group_id) {
        this.learning_materials_group_id = learning_materials_group_id;
    }

    @Override
    public String toString() {
        return "LearningMaterialsGroupExam{" +
                "exam_id=" + exam_id +
                ", id=" + id +
                ", learning_materials_group_id=" + learning_materials_group_id +
                '}';
    }
}
