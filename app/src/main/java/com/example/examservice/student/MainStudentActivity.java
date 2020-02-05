package com.example.examservice.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.UserView;
import com.example.examservice.database.Date;
import com.example.examservice.database.Exam;
import com.example.examservice.database.Result;
import com.example.examservice.database.UserExam;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class MainStudentActivity extends AppCompatActivity {

    private final int EXAMS_LIST_REQUEST_CODE = 1;
    private final int RESULTS_LIST_REQUEST_CODE = 2;
    private final int PROFILE_VIEW_REQUEST_CODE =3;
    private final String TAG = "TAGMainStudent" ;
    SharedPreferences prefs ;
    TextView tvName ;
    DatabaseReference examsRef ;
    DatabaseReference userExamsRef ;
    DatabaseReference resultsRef ;
    public static ArrayList<Exam> examsList ;
    public static ArrayList<Result> allResultsList ;
    public static int userId ;
    public static Exam currentExam ;
    //public static int resultCounter;
    public static int i ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main);
        tvName = findViewById(R.id.helloStudentTxtView);

        examsList = new ArrayList<>();
        allResultsList = new ArrayList<>();

        prefs = getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String hello = "Hello " + prefs.getString(ApplicationClass.SHARED_PREFERENCES_FIRST_NAME_KEY, "");
        tvName.setText(hello);
        userId = prefs.getInt(ApplicationClass.SHARED_PREFERENCES_ID_KEY, 0);
        //resultCounter =0 ;

        examsRef = ApplicationClass.mDatabase.getReference().child("Exam");
        userExamsRef = ApplicationClass.mDatabase.getReference().child("UserExam");
        resultsRef = ApplicationClass.mDatabase.getReference().child("UserExam");

        userExamsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                examsList.clear();
                for(DataSnapshot userExamSnapshot : dataSnapshot.getChildren()){
                    UserExam userExam = userExamSnapshot.getValue(UserExam.class);
                    if(userExam != null && userId == userExam.getUser_id()){
                        Log.i(TAG, userExam.toString());
                        Date date = userExam.getDate_of_resolve_exam() ;
                        getExam(userExam.getExam_id(), userExam.getUser_exam_id(), date);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        return;
    }

    private void getResults() {

        allResultsList.clear();
        Log.d(TAG, "size: " + examsList.size());
        for(i = 0; i < examsList.size()-1; i++) {
            Log.d(TAG, "iteracja: " + i);
            Exam exam = examsList.get(i);
            Log.d(TAG, "current: "+exam.toString());
            String userExamId = Integer.toString(exam.getUserExamId());
            resultsRef.child(userExamId).child("Result").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot resultsSnapshot : dataSnapshot.getChildren()) {
                        Result result = resultsSnapshot.getValue(Result.class);
                        if (result != null) {
                            //TODO może spróbować jednak od razu tu dodawać result do konkretnego examu
                            //a nie w AllExamsList je sortować ?
                            allResultsList.add(result);
                            Log.i(TAG, result.toString());
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }


    private void getExam(int examId, final int user_exam_id, final Date date) {

        String id = Integer.toString(examId);
        examsRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Exam exam = dataSnapshot.getValue(Exam.class);
                exam.setUserExamId(user_exam_id);
                exam.setExam_resolved(date);
                Log.i(TAG, exam.toString());
                examsList.add(exam);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void studentExamsList(View view) {
        getResults();
        Intent intent = new Intent(getApplicationContext(), AllExamsList.class);
        intent.putExtra("START_EXAM", true);
        startActivityForResult(intent, RESULTS_LIST_REQUEST_CODE);
    }

    public void studentResultList(View view) {
        getResults();
        Intent intent = new Intent(getApplicationContext(), AllExamsList.class);
        intent.putExtra("SHOW_RESULTS", true);
        startActivityForResult(intent, EXAMS_LIST_REQUEST_CODE);
    }

    public void studentProfileView(View view) {
        Intent intent = new Intent(getApplicationContext(), UserView.class);
        startActivityForResult(intent, PROFILE_VIEW_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PROFILE_VIEW_REQUEST_CODE){
            if(resultCode == 1000){
                setResult(1000);
                finish();
            }
        }
    }
}
