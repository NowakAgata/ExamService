package com.example.examservice.professor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.examservice.ApplicationClass;
import com.example.examservice.MyFTPClient;
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

public class AllMaterialsList extends AppCompatActivity {

    private static final String TAG = "TAGAllMaterials";
    private static final int ADD_MATERIAL_REQUEST_CODE = 1;
    private static final int EDIT_MATERIAL_REQUEST_CODE = 2;
    ArrayList<LearningMaterial> materialsList ;
    LearningMaterialsGroup group ;
    public static LearningMaterial chosenMaterial ;
    RecyclerView recyclerView;
    AllMaterialsAdapter materialsAdapter ;
    RecyclerView.LayoutManager layoutManager;
    public static int materialsCounter ;
    DatabaseReference materialsRef ;
    MyFTPClient ftpClient ;
    //boolean showFiles ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_all_materials_list);

        ftpClient = ApplicationClass.ftpclient;
        //showFiles = getIntent().getBooleanExtra("SHOW_FILES", false);

        group = AllMaterialsGroup.chosenGroup;
        String groupId = Integer.toString(group.getLearning_materials_group_id());
        materialsList = new ArrayList<>();
        materialsCounter = 0;
        materialsRef = ApplicationClass.mDatabase
                .getReference().child("LearningMaterialsGroup").child(groupId).child("LearningMaterial");
        materialsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                materialsList.clear();
                for(DataSnapshot materialSnapshot : dataSnapshot.getChildren()){
                    LearningMaterial material = materialSnapshot.getValue(LearningMaterial.class);
                    if(material != null){
                        int temp = material.getId();
                        materialsCounter = (materialsCounter > temp ) ? materialsCounter : temp ;
                        materialsList.add(material);
                        Log.d(TAG, material.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        recyclerView = findViewById(R.id.allProfMaterialsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        materialsAdapter = new AllMaterialsAdapter(this, materialsList);
        recyclerView.setAdapter(materialsAdapter);
    }

    public void onMaterialDeleteClick(View view) {
        //if(showFiles) return ;

        int index = (int) view.getTag();
        String groupId = Integer.toString(group.getLearning_materials_group_id());
        LearningMaterial material = materialsList.get(index);
        String id = Integer.toString(material.getId());
        boolean deleted = ftpClient.ftpDelete(material.getName_of_content());
        if(deleted) {
            Log.d(TAG, "deleted");
            DatabaseReference ref = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroup")
                    .child(groupId).child("LearningMaterial");
            ref.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    materialsAdapter.notifyDataSetChanged();
                }
            });


        }else{
            Log.d(TAG, "File not deleted, something went wrong");
        }
    }

    public void addNewMaterial(View view) {
        Intent intent = new Intent(getApplicationContext(), AddNewMaterial.class);
        startActivityForResult(intent, ADD_MATERIAL_REQUEST_CODE);
    }

    public void onMaterialClick(View view) {
        int index = (int) view.getTag();
        LearningMaterial material = materialsList.get(index) ;
        String link = "http://files01.radiokomunikacja.edu.pl/" + material.getName_of_content();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == ADD_MATERIAL_REQUEST_CODE && resultCode == RESULT_OK)
                || (requestCode == EDIT_MATERIAL_REQUEST_CODE && resultCode == RESULT_OK)){
            materialsAdapter.notifyDataSetChanged();
        }
    }

    public void onMaterialEditClick(View view) {
        //if(showFiles) return ;
        int index = (int) view.getTag();
        chosenMaterial = materialsList.get(index) ;
        Intent intent = new Intent(getApplicationContext(), EditMaterial.class);
        startActivityForResult(intent, EDIT_MATERIAL_REQUEST_CODE);

    }

    public void deleteGroup(View view) {

//        if(showFiles){
//            int groupId = group.getLearning_materials_group_id();
//            int examId = AllExamsList.currentExam.getExam_id();
//            for(final LearningMaterialsGroupExam groupExam : ExamOptions.groupExamList){
//                if(groupExam.getExam_id() == examId && groupExam.getLearning_materials_group_id() == groupId){
//                    String id = Integer.toString(groupExam.getId());
//                    DatabaseReference temp = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroupExam");
//                    temp.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Log.d(TAG, "deleted groupExam: " + groupExam);
//                            materialsAdapter.notifyDataSetChanged();
//                        }
//                    });
//                }
//            }
//        }else {
            if (materialsList.size() > 0) {
                Toast.makeText(getApplicationContext(), "You have to delete all materials first.", Toast.LENGTH_SHORT).show();
                return;
            }
            String groupId = Integer.toString(group.getLearning_materials_group_id());
            DatabaseReference ref = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroup")
                    .child(groupId);
            ref.removeValue();
            setResult(RESULT_OK);
            finish();
 //       }
    }



}
