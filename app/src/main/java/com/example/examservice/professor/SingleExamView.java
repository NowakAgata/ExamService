package com.example.examservice.professor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examservice.R;
import com.example.examservice.database.Date;
import com.example.examservice.database.Exam;

public class SingleExamView extends AppCompatActivity {

    private  static final int EDIT_EXAM_REQUEST_CODE = 1;
    TextView tvName, tvInfo, tvAvailable, tvIsLearning , tvDuration, tvAttempts, tvQuestions ;
    String name, info, availableStr, learningStr, durationStr, attemptsStr, questionsStr;
    Date from, to ;
    boolean isLearning ;
    int duration, attempts, questions;
    Exam exam ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_single_exam_view);

        tvName = findViewById(R.id.examNameTxtView);
        tvInfo = findViewById(R.id.examInfoTxtView);
        tvAvailable = findViewById(R.id.examAvailableFrom);
        tvIsLearning = findViewById(R.id.examIsLearningReq);
        tvDuration =findViewById(R.id.examDurationTime);
        tvAttempts =findViewById(R.id.examMaxAttempts);
        tvQuestions=findViewById(R.id.examMaxQuestions);

        exam = AllExamsList.currentExam;

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
        learningStr = isLearning ? "tak" : "ni" ;
        durationStr = duration + " minute(s)" ;
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

    public void okButton(View view) {
        finish();
    }

    public void editButton(View view) {

        Intent intent = new Intent(getApplicationContext(), EditExam.class);
        startActivityForResult(intent, EDIT_EXAM_REQUEST_CODE );

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
