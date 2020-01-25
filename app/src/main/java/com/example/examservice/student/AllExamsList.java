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
import com.example.examservice.database.Exam;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AllExamsList extends AppCompatActivity {

    private final String TAG = "TAGAllExams" ;
    private final int ON_EXAM_CLICK_REQUEST_CODE =1;
    RecyclerView recyclerView ;
    RecyclerView.LayoutManager layoutManager;
    AllExamsAdapter examsAdapter ;
    ArrayList<Exam> examsList ;
    public static Exam currentExam ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_all_exams_list);

        examsList = MainStudentActivity.examsList;

        recyclerView = findViewById(R.id.allAvailableExamsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        examsAdapter = new AllExamsAdapter(this, examsList);
        recyclerView.setAdapter(examsAdapter);
    }


    public void onExamClick(View view) {

        int index = (int) view.getTag();
        currentExam = examsList.get(index);

        Intent intent = new Intent(getApplicationContext(), SingleExamView.class);
        startActivityForResult(intent, ON_EXAM_CLICK_REQUEST_CODE);

    }
}
