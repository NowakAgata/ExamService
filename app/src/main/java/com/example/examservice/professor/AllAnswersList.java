package com.example.examservice.professor;

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
import com.example.examservice.database.Answer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AllAnswersList extends AppCompatActivity {

    private static final String TAG = "TAGAllAnswers";
    RecyclerView recyclerView;
    AllAnswersAdapter allAnswersAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Answer> answerList ;
    DatabaseReference questionRef ;
    DatabaseReference answerRef ;

    String examId, questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_all_answers_list);

        Intent intent = getIntent();
        examId = intent.getStringExtra("EXAM_ID");
        questionId = intent.getStringExtra("QUESTION_ID");



        answerList = AllQuestionsList.answerArrayList;

        recyclerView = findViewById(R.id.allAnswersListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        allAnswersAdapter = new AllAnswersAdapter(this, answerList);
        recyclerView.setAdapter(allAnswersAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onDeleteQuestionClick(View view) {

        questionRef = ApplicationClass.mDatabase.getReference().child("Exam").child(examId)
                .child("Question").child(questionId);
        Log.d(TAG, "Deleting question with id: " + questionId);

        questionRef.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Deleting complete");
                        setResult(100);
                        finish();
                    }
                }
                ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Something went wrong");
                        setResult(200);
                        finish();
            }
        });
    }

    public void onAnswerDeleteClick(View view) {
        int index = (int) view.getTag();
        Answer temp = answerList.get(index);
        int answer ;
        answer = temp.getId();
        String answerId ;
        answerId = Integer.toString(answer);

        answerRef = ApplicationClass.mDatabase.getReference().child("Exam").child(examId)
                .child("Question").child(questionId).child("Answer").child(answerId);
        Log.d(TAG, "Deleting answer with id: " + answerId);

        answerRef.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Deleting complete");
                        updateAnswersList();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                     public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Something went wrong");

                    }
        });

    }

    private void updateAnswersList(){
        answerList = AllQuestionsList.answerArrayList ;
        allAnswersAdapter.notifyDataSetChanged();
    }
}
