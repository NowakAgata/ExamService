package com.example.examservice.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.examservice.R;
import com.example.examservice.database.LearningMaterial;
import com.example.examservice.professor.AllMaterialsGroup;

import java.util.ArrayList;

public class AllMaterialsList extends AppCompatActivity {


    RecyclerView recyclerView;
    AllMaterialsAdapter materialsAdapter ;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<LearningMaterial> materialsList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_all_materials_list);

        materialsList = AllMaterialsGroups.materialsList;

        recyclerView = findViewById(R.id.allStudentMaterialsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        materialsAdapter = new AllMaterialsAdapter(this, materialsList);
        recyclerView.setAdapter(materialsAdapter);
    }

    public void onStudentMaterialClick(View view) {
        int index = (int) view.getTag();
        LearningMaterial material = materialsList.get(index) ;
        String link = "http://files01.radiokomunikacja.edu.pl/" + material.getName_of_content();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }
}
