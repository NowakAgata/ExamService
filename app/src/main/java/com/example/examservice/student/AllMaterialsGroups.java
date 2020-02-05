package com.example.examservice.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.LearningMaterial;
import com.example.examservice.database.LearningMaterialsGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllMaterialsGroups extends AppCompatActivity {

    private static final String TAG = "TAGAllGroups";
    RecyclerView recyclerView;
    AllMaterialsGroupsAdapter groupsAdapter ;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<LearningMaterialsGroup> groupsList ;
    public static ArrayList<LearningMaterial> materialsList ;
    DatabaseReference materialsRef ;
    LearningMaterialsGroup chosenGroup ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_all_materials_groups);

        groupsList = SingleExamView.groupList ;
        materialsList = new ArrayList<>();

        materialsRef = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroup");

        recyclerView = findViewById(R.id.allStudentMaterialGroupsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        groupsAdapter = new AllMaterialsGroupsAdapter(this, groupsList);
        recyclerView.setAdapter(groupsAdapter);

    }

    public void onMaterialGroupClick(View view){
        int index = (int) view.getTag();
        chosenGroup = groupsList.get(index);

        getList();

        Intent intent = new Intent(getApplicationContext(), AllMaterialsList.class);
        startActivity(intent);

    }

    private void getList() {
        String groupId = Integer.toString(chosenGroup.getLearning_materials_group_id());
        //TODO tu coś nie gra, dziwnie wyświetla się lista materiałów, jest jeden materiał a pobiera 5
        Log.d(TAG, "Group id: " + groupId);
        materialsRef.child(groupId).child("LearningMaterial");
        materialsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                materialsList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    LearningMaterial material = snapshot.getValue(LearningMaterial.class);
                    if(material != null){
                        materialsList.add(material) ;
                        Log.d(TAG, material.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
