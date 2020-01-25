package com.example.examservice.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
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
    public static ArrayList<Result> resultsList ;
    public static HashSet<Integer> examIdsList ;
    public static int userId ;
    public static Exam currentExam ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main);
        tvName = findViewById(R.id.helloStudentTxtView);

        examsList = new ArrayList<>();
        resultsList = new ArrayList<>();
        //lista id egzaminów, które poprzez userExam są połączone z aktualnym studentem
        examIdsList = new HashSet<>();

        prefs = getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String hello = "Hello " + prefs.getString(ApplicationClass.SHARED_PREFERENCES_FIRST_NAME_KEY, "");
        tvName.setText(hello);
        userId = prefs.getInt(ApplicationClass.SHARED_PREFERENCES_ID_KEY, 0);

        examsRef = ApplicationClass.mDatabase.getReference().child("Exam");
        userExamsRef = ApplicationClass.mDatabase.getReference().child("UserExam");
        resultsRef = ApplicationClass.mDatabase.getReference().child("UserExam");

        userExamsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                examIdsList.clear();
                for(DataSnapshot userExamSnapshot : dataSnapshot.getChildren()){
                    UserExam userExam = userExamSnapshot.getValue(UserExam.class);
                    if(userExam != null && userId == userExam.getUser_id()){
                        Log.i(TAG, userExam.toString());
                        getExam(userExam.getExam_id(), userExam.getUser_exam_id());

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getResults(int user_exam_id) {

        resultsList.clear();
        String id = Integer.toString(user_exam_id);
        resultsRef.child(id).child("Result").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getChildrenCount() ;
                for(DataSnapshot resultsSnapshot : dataSnapshot.getChildren()){
                    Result result = resultsSnapshot.getValue(Result.class);
                    if(result != null){
                        resultsList.add(result);
                        Log.i(TAG, result.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getExam(int examId, final int user_exam_id) {

        String id = Integer.toString(examId);
        examsRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Exam exam = dataSnapshot.getValue(Exam.class);
                Log.i(TAG, exam.toString());
                getResults(user_exam_id);
                exam.setResultsList(resultsList);
                examsList.add(exam);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void studentExamsList(View view) {
        Intent intent = new Intent(getApplicationContext(), AllExamsList.class);
        startActivityForResult(intent, EXAMS_LIST_REQUEST_CODE);
    }

    public void studentResultList(View view) {

    }

    public void studentProfileView(View view) {
    }


}
