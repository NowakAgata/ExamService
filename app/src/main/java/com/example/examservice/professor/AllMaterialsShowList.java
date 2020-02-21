package com.example.examservice.professor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.LearningMaterial;
import com.example.examservice.database.LearningMaterialsGroup;
import com.example.examservice.database.LearningMaterialsGroupExam;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllMaterialsShowList extends AppCompatActivity {

    RecyclerView recyclerView;
    AllMaterialsShowAdapter materialsAdapter ;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<LearningMaterial> materialsList ;
    DatabaseReference materialsRef ;
    LearningMaterialsGroup group ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_all_materials_show_list);

        materialsList = new ArrayList<>();
        group = AllMaterialsGroup.chosenGroup;
        String groupId = Integer.toString(group.getLearning_materials_group_id());

        materialsRef = ApplicationClass.mDatabase
                .getReference().child("LearningMaterialsGroup").child(groupId).child("LearningMaterial");
        materialsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                materialsList.clear();
                for(DataSnapshot materialSnapshot : dataSnapshot.getChildren()){
                    LearningMaterial material = materialSnapshot.getValue(LearningMaterial.class);
                    if(material != null){
                        materialsList.add(material);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        recyclerView = findViewById(R.id.allProfMaterialsShowListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        materialsAdapter = new AllMaterialsShowAdapter(this, materialsList);
        recyclerView.setAdapter(materialsAdapter);
    }

    public void deleteGroup(View view) {

        int groupId = group.getLearning_materials_group_id();
        int examId = AllExamsList.currentExam.getExam_id();
        for(final LearningMaterialsGroupExam groupExam : ExamOptions.groupExamList){
            if(groupExam.getExam_id() == examId && groupExam.getLearning_materials_group_id() == groupId){
                String id = Integer.toString(groupExam.getId());
                DatabaseReference temp = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroupExam");
                temp.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        }

    }
}
