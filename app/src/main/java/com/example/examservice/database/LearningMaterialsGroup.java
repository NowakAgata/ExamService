package com.example.examservice.database;

public class LearningMaterialsGroup {

    private int learning_materials_groups_id ;
    private String name_of_group ;

    public LearningMaterialsGroup(){

    }

    public LearningMaterialsGroup(int learning_materials_group_id, String name_of_group) {
        this.learning_materials_groups_id = learning_materials_group_id;
        this.name_of_group = name_of_group;
    }

    public int getLearning_materials_group_id() {
        return learning_materials_groups_id;
    }

    public void setLearning_materials_group_id(int learning_materials_group_id) {
        this.learning_materials_groups_id = learning_materials_group_id;
    }

    public String getName_of_group() {
        return name_of_group;
    }

    public void setName_of_group(String name_of_group) {
        this.name_of_group = name_of_group;
    }

    @Override
    public String toString() {
        return "LearningMaterialsGroup{" +
                "learning_materials_group_id=" + learning_materials_groups_id +
                ", name_of_group='" + name_of_group + '\'' +
                '}';
    }
}
