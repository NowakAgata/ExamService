package com.example.examservice.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.examservice.ApplicationClass;
import com.example.examservice.MyFTPClient;
import com.example.examservice.R;
import com.example.examservice.database.LearningMaterialsGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FileAdministration extends AppCompatActivity {

    private static final int ADD_MATERIAL_TO_EXAM_REQUEST_CODE = 1;
    private final String TAG = "TAGFileAdmin" ;
    private MyFTPClient ftpclient = null;
    DatabaseReference groupsRef ;
    public static   ArrayList<LearningMaterialsGroup> groupsList;
    public static int groupsCounter ;
    boolean addMaterialToExam ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_file_administration);

        groupsList = new ArrayList<>();
        groupsCounter = 0;
        groupsRef = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroup");
        groupsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupsList.clear();
                for(DataSnapshot groupsSnapshot : dataSnapshot.getChildren()){
                    LearningMaterialsGroup group = groupsSnapshot.getValue(LearningMaterialsGroup.class);
                    if(group != null){
                        int temp = group.getLearning_materials_group_id();
                        Log.d(TAG, group.toString());
                        groupsCounter = (groupsCounter > temp) ? groupsCounter : temp ;
                        Log.d(TAG, Integer.toString(groupsCounter));
                        groupsList.add(group);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addMaterialToExam = getIntent().getBooleanExtra("SHOW_FILES", false);
        if(addMaterialToExam){
            Intent intent = new Intent(getApplicationContext(), AllMaterialsGroups.class);
            intent.putExtra("SHOW_FILES", true) ;
            startActivityForResult(intent, ADD_MATERIAL_TO_EXAM_REQUEST_CODE);
        }

    }



    public void listFilesByGroup(View view) {
        Intent intent = new Intent(getApplicationContext(), AllMaterialsGroups.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_MATERIAL_TO_EXAM_REQUEST_CODE){
            finish();
        }
    }
}
