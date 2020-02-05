package com.example.examservice.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.LearningMaterial;
import com.example.examservice.database.LearningMaterialsGroup;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class NewLearningMaterialsGroup extends AppCompatActivity {

    private static final int ADD_MATERIAL_REQUEST_CODE = 1;
    private static final String TAG = "TAGNewGroup";
    EditText etName ;
    String name ;
    LearningMaterialsGroup group ;
    public static ArrayList<LearningMaterial> materialsList ;
    RecyclerView recyclerView;
    AllMaterialsLittleAdapter materialsLittleAdapter ;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_new_learning_materials_group);

        etName = findViewById(R.id.newMaterialGroupNameEditText);
        materialsList = new ArrayList<>();

        recyclerView = findViewById(R.id.littleMaterialsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        materialsLittleAdapter = new AllMaterialsLittleAdapter(this,materialsList );
        recyclerView.setAdapter(materialsLittleAdapter);

    }

    public void addFile(View view) {
        Intent intent = new Intent(getApplicationContext(), AddNewMaterial.class);
        intent.putExtra("NEW_GROUP", true);
        startActivityForResult(intent, ADD_MATERIAL_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_MATERIAL_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                materialsLittleAdapter.notifyDataSetChanged();
                //TODO iteracja po liście materiałów i zmienić id materiału i grupy
            } else{
                Toast.makeText(getApplicationContext(), R.string.somethingWrong, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addGroup(View view) {

        name = etName.getText().toString();

        if(name.isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter group name!", Toast.LENGTH_SHORT).show();
            return;
        }else if(materialsList.size() == 0){
            Toast.makeText(getApplicationContext(), "Add at least one material!", Toast.LENGTH_SHORT).show();
            return ;
        }

        int groupId = FileAdministration.groupsCounter;
        groupId ++ ;
        String groupIdStr = Integer.toString(groupId) ;

        LearningMaterialsGroup group = new LearningMaterialsGroup(groupId, name);
        Log.d(TAG, group.toString());
        DatabaseReference tempRef = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroup").child(groupIdStr);
        tempRef.setValue(group);
        for(int i =0; i<materialsList.size(); i++){
            LearningMaterial material = materialsList.get(i);
            material.setId(i);
            material.setLearning_materials_group_id(groupId);
            Log.d(TAG, material.toString());
            DatabaseReference tempMaterialRef = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroup")
                    .child(groupIdStr).child("LearningMaterial");
            String id = Integer.toString(i);
            tempMaterialRef.child(id).setValue(material);
        }
        setResult(RESULT_OK);
        finish();

    }
}
