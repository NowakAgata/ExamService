package com.example.examservice.student;

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
import com.example.examservice.database.LearningMaterialsGroup;
import com.example.examservice.database.LearningMaterialsGroupExam;
import com.example.examservice.database.Result;
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
    ArrayList<Exam> allExamsList ;
    ArrayList<Result> allResultsList ;
    public static ArrayList<LearningMaterialsGroup> groupsList;
    DatabaseReference resultRef ;
    DatabaseReference groupRef ;
    boolean startExam, showResults ;
    public static Exam currentExam ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_all_exams_list);

        Intent intent = getIntent();
        startExam = intent.getBooleanExtra("START_EXAM", false);
        showResults = intent.getBooleanExtra("SHOW_RESULTS", false);

        allExamsList = MainStudentActivity.examsList;
        allResultsList = MainStudentActivity.allResultsList;
        sortResults();
        examsList = new ArrayList<>();
        groupsList = new ArrayList<>();
        examsList.clear();
        if(startExam){
            for(Exam exam : allExamsList){
                if(exam.isAvailable(6) && exam.isHas_questions()){
                    Log.d(TAG, "Dodano: " +exam.toString());
                    examsList.add(exam);
                }
            }
        }else if(showResults){
            for(Exam exam : allExamsList){
                if(exam.getResultsList().size() > 0){
                    Log.d(TAG, "Dodano: " + exam.toString());
                    examsList.add(exam);
                }
            }
        }

        groupRef = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroup");
        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupsList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    LearningMaterialsGroup group = snapshot.getValue(LearningMaterialsGroup.class);
                    if(group != null){
                        groupsList.add(group);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        recyclerView = findViewById(R.id.allAvailableExamsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        examsAdapter = new AllExamsAdapter(this, examsList);
        recyclerView.setAdapter(examsAdapter);

        resultRef = ApplicationClass.mDatabase.getReference().child("UserExam");

    }

    private void sortResults() {
        for(int i =0; i<allExamsList.size(); i++){
            Exam exam = allExamsList.get(i);
            for(int j=0; j< allResultsList.size(); j++){
                Result result = allResultsList.get(j);
                if(result.getExam_id() == exam.getExam_id()){
                    allExamsList.get(i).addResult(result);
                }
            }
        }
    }

    public void onExamClick(View view) {

        int index = (int) view.getTag();
        currentExam = examsList.get(index);
        if(startExam) {
            Intent intent = new Intent(getApplicationContext(), SingleExamView.class);
            startActivityForResult(intent, ON_EXAM_CLICK_REQUEST_CODE);
        }else if(showResults){
            Intent intent = new Intent(getApplicationContext(), AllResultsList.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ON_EXAM_CLICK_REQUEST_CODE && resultCode == 666){
            setResult(666);
            finish();
        }
    }
}
