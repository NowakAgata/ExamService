package com.example.examservice.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.Exam;
import com.example.examservice.database.LearningMaterialsGroupExam;
import com.example.examservice.database.User;
import com.example.examservice.database.UserExam;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExamAdministration extends AppCompatActivity {

    private final String TAG = "TAGExamAdmin";
    private final int EXAMS_LIST_REQUEST_CODE = 1;
    private final int ADD_EXAM_REQUEST_CODE = 2;
    private final int USER_EXAM_REQUEST_CODE = 3;
    private final int EDIT_QUESTIONS_REQUEST_CODE = 4;
    private final int MATERIAL_EXAM_REQUEST_CODE = 5;
    public static DatabaseReference examsRef ;
    public static DatabaseReference userExamsRef ;
    public static DatabaseReference materialExamsRef ;
    public static ArrayList<User> usersList ;
    public static ArrayList<Exam> examsList ;
    public static ArrayList<UserExam> userExamsList ;
    public static ArrayList<LearningMaterialsGroupExam> materialExamsList ;
    public static int examsCount ;
    public static int userExamsCount ;
    public static int materialExamsCount ;
    ArrayList<User> allStudents ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_exam_administration);

        examsCount = 0;
        examsList = new ArrayList<>() ;
        usersList = new ArrayList<>();
        userExamsList = new ArrayList<>();
        materialExamsList = new ArrayList<>();
        allStudents = ApplicationClass.allStudentsList ;

        examsRef = ApplicationClass.mDatabase.getReference().child("Exam");
        examsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                examsList.clear();
                for(DataSnapshot examSnapshot : dataSnapshot.getChildren()){
                    Exam exam = examSnapshot.getValue(Exam.class);
                    if(exam != null){
                        int temp = exam.getExam_id();
                        examsCount = (temp > examsCount) ? temp : examsCount ;
                        examsList.add(exam);
                        Log.i(TAG, exam.toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        userExamsRef = ApplicationClass.mDatabase.getReference().child("UserExam");
        userExamsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                userExamsList.clear();
                for(DataSnapshot userExamSnapshot : dataSnapshot.getChildren()){
                    UserExam userExam = userExamSnapshot.getValue(UserExam.class);
                    if(userExam != null){
                        int temp = userExam.getUser_exam_id();
                        userExamsCount = (temp > userExamsCount) ? temp : userExamsCount ;
                        userExamsList.add(userExam);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        materialExamsRef = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroupExam");
        materialExamsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                materialExamsList.clear();
                for(DataSnapshot materialExamSnapshot : dataSnapshot.getChildren()){
                    LearningMaterialsGroupExam materialExam = materialExamSnapshot.getValue(LearningMaterialsGroupExam.class);
                    if(materialExam != null){
                        int temp = materialExam.getId();
                        materialExamsCount = (materialExamsCount > temp) ? materialExamsCount : temp ;
                        materialExamsList.add(materialExam) ;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addExam(View view) {
        Intent intent = new Intent(getApplicationContext(), AddNewExam.class);
        startActivityForResult(intent, EXAMS_LIST_REQUEST_CODE);
    }

    public void editExam(View view) {
        Intent intent = new Intent(getApplicationContext(), AllExamsList.class);
        intent.putExtra("SHOW_EXAM", true);
        startActivityForResult(intent, ADD_EXAM_REQUEST_CODE);
    }

    public void editQuestions(View view) {
        Intent intent = new Intent(getApplicationContext(), AllExamsList.class);
        intent.putExtra("SHOW_QUESTIONS", true);
        startActivityForResult(intent, EDIT_QUESTIONS_REQUEST_CODE);

    }

    public void editUserExam(View view) {
        Intent intent = new Intent(getApplicationContext(), AllExamsList.class);
        intent.putExtra("SHOW_USERS", true);
        startActivityForResult(intent, USER_EXAM_REQUEST_CODE);
    }

    public void editMaterialExam(View view) {
        Intent intent = new Intent(getApplicationContext(), AllExamsList.class);
        intent.putExtra("SHOW_FILES", true);
        startActivityForResult(intent, MATERIAL_EXAM_REQUEST_CODE);
    }
}
