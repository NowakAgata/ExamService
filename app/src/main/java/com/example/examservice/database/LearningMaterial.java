package com.example.examservice.database;

public class LearningMaterial {

    private int id;
    private boolean is_required ;
    private int learning_materials_group_id;
    private String name ;
    private String name_of_content ;


    public LearningMaterial(){

    }

    public LearningMaterial(int id, boolean is_required, int learning_materials_group_id, String name, String name_of_content) {
        this.id = id;
        this.is_required = is_required;
        this.learning_materials_group_id = learning_materials_group_id;
        this.name = name;
        this.name_of_content = name_of_content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_required() {
        return is_required;
    }

    public void setIs_required(boolean is_required) {
        this.is_required = is_required;
    }

    public int getLearning_materials_group_id() {
        return learning_materials_group_id;
    }

    public void setLearning_materials_group_id(int learning_materials_group_id) {
        this.learning_materials_group_id = learning_materials_group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_of_content() {
        return name_of_content;
    }

    public void setName_of_content(String name_of_content) {
        this.name_of_content = name_of_content;
    }

    @Override
    public String toString() {
        return "LearningMaterial{" +
                "id=" + id +
                ", is_required=" + is_required +
                ", learning_materials_group_id=" + learning_materials_group_id +
                ", name='" + name + '\'' +
                ", name_of_content='" + name_of_content + '\'' +
                '}';
    }
}
