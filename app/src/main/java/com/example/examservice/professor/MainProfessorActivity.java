package com.example.examservice.professor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examservice.ApplicationClass;
import com.example.examservice.EditUser;
import com.example.examservice.R;
import com.example.examservice.UserView;
import com.example.examservice.database.Exam;
import com.example.examservice.database.LearningMaterialsGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainProfessorActivity extends AppCompatActivity {

    private static final int ALL_EXAMS_REQUEST_CODE = 1;
    private static final int ADD_EXAM_REQUEST_CODE = 2;
    private static final int SEE_STUDENTS_REQUEST_CODE =3;
    private static final int SEE_PROFILE_REQUEST_CODE =4;
    private static final int SEE_MATERIALS_REQUEST_CODE =5;
    private static final int PERMISSIONS_REQUEST_READ_FILES = 6;
    public static DatabaseReference examRef ;
    public static DatabaseReference groupsRef ;
    public static int examsCount ;
    public static int groupsCount ;
    public static SharedPreferences preferences ;
    public static ArrayList<Exam> examsList ;
    public static ArrayList<LearningMaterialsGroup> groupsList ;
    public static int professorId ;
    public String professorName ;
    private static final String TAG = "TAGMainProfessor";
    public TextView txtProfessor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_main);

        preferences = getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        professorId = preferences.getInt(ApplicationClass.SHARED_PREFERENCES_ID_KEY, 0);
        professorName = preferences.getString(ApplicationClass.SHARED_PREFERENCES_FIRST_NAME_KEY, "professor");

        txtProfessor = findViewById(R.id.helloProfessorTxtView);
        String temp = "Witaj " + professorName + "!" ;
        txtProfessor.setText(temp);

        examsCount = 0;
        groupsCount = 0;
        examsList = new ArrayList<>() ;
        groupsList = new ArrayList<>();

        examRef = ApplicationClass.mDatabase.getReference().child("Exam") ;
        examRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                examsList.clear();
                for(DataSnapshot examSnapshot : dataSnapshot.getChildren()){
                    Exam exam = examSnapshot.getValue(Exam.class);
                    if(exam != null){
                        int temp = exam.getExam_id();
                        examsCount = (temp > examsCount) ? temp : examsCount ;
                        Log.d(TAG, exam.toString());
                        if(exam.getCreated_by() == professorId || exam.getCreated_by() == -1){
                            examsList.add(exam);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        groupsRef = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroup");
        groupsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupsList.clear();
                for(DataSnapshot groupsSnapshot : dataSnapshot.getChildren()){
                    LearningMaterialsGroup group = groupsSnapshot.getValue(LearningMaterialsGroup.class);
                    if(group != null){
                        int temp = group.getLearning_materials_group_id();
                        groupsCount = (groupsCount > temp ) ? groupsCount : temp ;
                        groupsList.add(group);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        checkPermission();
    }
    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_READ_FILES);


        } else {
            Log.d(TAG,"Permission has already been granted");
        }

    }

    public void professorAllExamsList(View view) {
        Intent intent = new Intent(getApplicationContext(), AllExamsList.class);
        startActivityForResult(intent, ALL_EXAMS_REQUEST_CODE);
    }

    public void professorAddNewExam(View view) {
        Intent intent = new Intent(getApplicationContext(), AddNewExam.class);
        startActivityForResult(intent, ADD_EXAM_REQUEST_CODE);
    }

    public void professorSeeStudents(View view) {
        Intent intent = new Intent(getApplicationContext(), StudentGroupsList.class);
        startActivityForResult(intent, SEE_STUDENTS_REQUEST_CODE);

    }
    public void professorProfileView(View view) {
        Intent intent = new Intent(getApplicationContext(), UserView.class);
        startActivityForResult(intent, SEE_PROFILE_REQUEST_CODE);
    }
    public void professorSeeMaterials(View view) {
        Intent intent = new Intent(getApplicationContext(), AllMaterialsGroup.class);
        startActivityForResult(intent, SEE_MATERIALS_REQUEST_CODE);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_EXAM_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(), "Exam added successfully!", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(getApplicationContext(), R.string.somethingWrong, Toast.LENGTH_SHORT).show();

            }
        }else if(requestCode == ALL_EXAMS_REQUEST_CODE){
            if(resultCode == 100){
                Toast.makeText(getApplicationContext(), "Exam deleted successfully!", Toast.LENGTH_SHORT).show();
            }else if(resultCode == 200){
                Toast.makeText(getApplicationContext(), R.string.somethingWrong, Toast.LENGTH_SHORT).show();

            }
        }else if(requestCode == SEE_PROFILE_REQUEST_CODE){
            if(resultCode == 1000){
                setResult(1000);
                finish();
            }
        }
    }

}
