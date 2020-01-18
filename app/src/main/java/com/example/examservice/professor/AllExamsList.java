package com.example.examservice.professor;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.core.SyncTree;

import java.util.ArrayList;

public class AllExamsList extends AppCompatActivity {

    private static final String TAG = "TAGAllExamsList";
    ArrayList<Exam> examsList ;
    RecyclerView recyclerView;
    AllExamsAdapter examsAdapter ;
    RecyclerView.LayoutManager layoutManager;
    public static Exam currentExam ;
    DatabaseReference examRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_all_exams_list);

        examsList = MainProfessorActivity.examsList;

        recyclerView = findViewById(R.id.allExamsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        examsAdapter = new AllExamsAdapter(this, examsList);
        recyclerView.setAdapter(examsAdapter);

    }

    public void onProfessorExamClick(View view) {

        int index = (int) view.getTag();
        currentExam = examsList.get(index);

        Intent intent = new Intent(getApplicationContext(), ExamOptions.class);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == 100){
                //Deleting OK
                setResult(100);
                finish();
            }else if(resultCode == 200){
                setResult(200);
                finish();
            }
        }
    }
}
