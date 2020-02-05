package com.example.examservice.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.examservice.R;
import com.example.examservice.database.Result;

import java.util.ArrayList;

public class AllResultsList extends AppCompatActivity {

    RecyclerView recyclerView ;
    RecyclerView.LayoutManager layoutManager;
    AllResultsAdapter resultsAdapter ;
    ArrayList<Result> resultsList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_results_list);


        resultsList = AllExamsList.currentExam.getResultsList();

        recyclerView = findViewById(R.id.allResultsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        resultsAdapter = new AllResultsAdapter(this, resultsList);
        recyclerView.setAdapter(resultsAdapter);

    }
}
