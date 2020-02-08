package com.example.examservice.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.Date;
import com.example.examservice.database.Exam;
import com.example.examservice.database.UserExam;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ExamView extends AppCompatActivity {

    private  static final int EDIT_EXAM_REQUEST_CODE = 1;
    private final String TAG = "TAGExamView" ;
    TextView tvName, tvInfo, tvAvailable, tvIsLearning , tvDuration, tvAttempts, tvQuestions ;
    String name, info, availableStr, learningStr, durationStr, attemptsStr, questionsStr;
    Date from, to ;
    boolean isLearning ;
    int duration, attempts, questions;
    Exam exam ;
    DatabaseReference examsRef ;
    DatabaseReference userExamRef ;
    boolean canDelete ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_exam_view);

        tvName = findViewById(R.id.adminExamNameTxtView);
        tvInfo = findViewById(R.id.adminExamInfoTxtView);
        tvAvailable = findViewById(R.id.adminExamAvailableFrom);
        tvIsLearning = findViewById(R.id.adminExamIsLearningReq);
        tvDuration =findViewById(R.id.adminExamDurationTime);
        tvAttempts =findViewById(R.id.adminExamMaxAttempts);
        tvQuestions=findViewById(R.id.adminExamMaxQuestions);

        canDelete = true ;
        examsRef = ApplicationClass.mDatabase.getReference().child("Exam");
        userExamRef = ApplicationClass.mDatabase.getReference().child("UserExam");
        exam = AllExamsList.currentExam ;

        checkIfCanDelete();
        updateTextViews();
    }

    private void updateTextViews() {
        name = exam.getName();
        info = exam.getAdditional_information();
        from = exam.getStart_date();
        to = exam.getEnd_date();
        isLearning = exam.getLearning_required();
        duration = exam.getDuration_of_exam();
        attempts = exam.getMax_attempts();
        questions = exam.getMax_questions();

        availableStr = "od: " + from.getDate().toString().substring(0,10) + ", do: " + to.getDate().toString().substring(0,10);
        learningStr = isLearning ? "tak" : "nie" ;
        durationStr = duration + " minut" ;
        attemptsStr = Integer.toString(attempts);
        questionsStr = Integer.toString(questions);

        tvName.setText(name);
        tvInfo.setText(info);
        tvAvailable.setText(availableStr);
        tvIsLearning.setText(learningStr);
        tvDuration.setText(durationStr);
        tvAttempts.setText(attemptsStr);
        tvQuestions.setText(questionsStr);

    }

    public void editButton(View view) {
        Intent intent = new Intent(getApplicationContext(), EditExam.class);
        startActivityForResult(intent, EDIT_EXAM_REQUEST_CODE);
    }

    public void deleteButton(View view) {

        if(canDelete){
            int examId = exam.getExam_id() ;
            String id  = Integer.toString(examId) ;
            DatabaseReference tempRef = ApplicationClass.mDatabase.getReference().child("Exam");
            tempRef.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d(TAG, "Usunięto exam");
                    setResult(RESULT_OK);
                    finish();

                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "You can't delete this exam, because there are UserExam connections", Toast.LENGTH_SHORT).show();

        }
    }

    private void checkIfCanDelete() {

        final int examId = exam.getExam_id() ;
        userExamRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userExamData : dataSnapshot .getChildren()){
                    UserExam userExam = userExamData.getValue(UserExam.class);
                    if((userExam != null) && (userExam.getExam_id() == examId)){
                        canDelete = false;
                        return ;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_EXAM_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                exam = AllExamsList.currentExam;
                updateTextViews();
                Toast.makeText(getApplicationContext(), "Exam updated successfully!", Toast.LENGTH_SHORT).show();
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "There was nothing to change.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
