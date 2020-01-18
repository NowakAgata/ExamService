package com.example.examservice.professor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class StudentGroupsList extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentGroupsAdapter groupsAdapter ;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> groupsList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_student_groups_list);

        Set<String> tempSet = ApplicationClass.allGroupsSet ;
        groupsList = new ArrayList<>();
        for(String temp : tempSet){
            groupsList.add(temp);
        }

        recyclerView = findViewById(R.id.allGroupsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        groupsAdapter = new StudentGroupsAdapter(this, groupsList);
        recyclerView.setAdapter(groupsAdapter);

    }

    public void onGroupClick(View view) {
        int i = (int) view.getTag() ;
        String chosen = groupsList.get(i);
        Log.d("GROUPTAG", "Wybrano: " + chosen) ;
        Intent intent = new Intent(getApplicationContext(), AllStudentsList.class);
        intent.putExtra("GROUP", chosen);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
