package com.example.examservice.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.Exam;
import com.example.examservice.database.LearningMaterialsGroup;
import com.example.examservice.database.LearningMaterialsGroupExam;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AllMaterialsGroups extends AppCompatActivity {

    private static final int ADD_NEW_GROUP_REQUEST_CODE =1;
    private static final int SEE_MATERIALS_REQUEST_CODE =2;
    private static final String TAG = "TAGAllGroups" ;
    private static final int ADD_GROUP_EXAM_REQUEST_CODE = 3;
    ArrayList<LearningMaterialsGroup> allGroupsList ;
    ArrayList<LearningMaterialsGroup> groupsList ;
    ArrayList<LearningMaterialsGroupExam> groupExam ;
    RecyclerView recyclerView;
    AllMaterialsGroupsAdapter groupsAdapter ;
    RecyclerView.LayoutManager layoutManager;
    public static LearningMaterialsGroup chosenGroup;
    boolean showFiles, addGroupExam ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_materials_groups);

        Intent intent = getIntent();
        showFiles = intent.getBooleanExtra("SHOW_FILES", false);
        addGroupExam = intent.getBooleanExtra("ADD_MATERIAL_TO_EXAM", false);

        allGroupsList = FileAdministration.groupsList;
        groupsList = new ArrayList<>();
        groupExam = new ArrayList<>();

        if(showFiles){
            groupExam = ExamAdministration.materialExamsList;
            getList();
        }else{
            groupsList = allGroupsList ;
        }

        recyclerView = findViewById(R.id.allMaterialGroupsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        groupsAdapter = new AllMaterialsGroupsAdapter(this, groupsList);
        recyclerView.setAdapter(groupsAdapter);

    }

    private void getList() {
        Exam exam = AllExamsList.currentExam;
        for(LearningMaterialsGroupExam i : groupExam){
            if(i.getExam_id() == exam.getExam_id()){
                for(LearningMaterialsGroup j : allGroupsList){
                    if(j.getLearning_materials_group_id() == i.getLearning_materials_group_id()){
                        groupsList.add(j);
                    }
                }
            }
        }
    }

    public void onMaterialGroupClick(View view) {

        int index = (int) view.getTag();
        chosenGroup = groupsList.get(index);

        if(addGroupExam){
            Exam exam = AllExamsList.currentExam;
            int counter = ExamAdministration.materialExamsCount;
            counter++;
            String id = Integer.toString(counter);
            LearningMaterialsGroupExam temp = new LearningMaterialsGroupExam(exam.getExam_id(), counter,
                    chosenGroup.getLearning_materials_group_id());
            DatabaseReference tempRef = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroupExam")
                    .child(id);
            tempRef.setValue(temp) ;
            Log.d(TAG, "Adding: " + temp.toString());
            setResult(RESULT_OK);
            finish();
        }else {
            Intent intent = new Intent(getApplicationContext(), AllMaterialsList.class);
            startActivityForResult(intent, SEE_MATERIALS_REQUEST_CODE);
        }

    }

    public void addNewMaterialGroup(View view) {
        if(showFiles) {
            Intent intent = new Intent(getApplicationContext(), AllMaterialsGroups.class);
            intent.putExtra("ADD_MATERIAL_TO_EXAM", true);
            startActivityForResult(intent, ADD_GROUP_EXAM_REQUEST_CODE);
        }else{
            Intent intent = new Intent(getApplicationContext(), NewLearningMaterialsGroup.class);
            startActivityForResult(intent, ADD_NEW_GROUP_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            allGroupsList = FileAdministration.groupsList;
            if(showFiles){
                groupExam = ExamAdministration.materialExamsList;
                getList();
            }else{
                groupsList = allGroupsList ;
            }
            groupsAdapter.notifyDataSetChanged();
        }
    }
}
