package com.example.examservice.professor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    public static DatabaseReference examRef ;
    public static int examsCount ;
    public static SharedPreferences preferences ;
    public static ArrayList<Exam> examsList ;
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
        String temp = "Hello " + professorName + "!" ;
        txtProfessor.setText(temp);

        examsList = new ArrayList<>() ;
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
                        if(exam.getCreated_by() == professorId){
                            examsList.add(exam);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
//        Intent intent = new Intent(getApplicationContext(), EditUser.class);
//        startActivityForResult(intent, SEE_PROFILE_REQUEST_CODE);
        //TODO tutuaj przejście do edycji / widoku profilu ??? przemyśleć

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
        }
    }



}
