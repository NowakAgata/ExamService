package com.example.examservice.admin;

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
import com.example.examservice.database.Answer;
import com.example.examservice.database.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllAnswersList extends AppCompatActivity {

    private static final int ADD_NEW_ANSWER_REQUEST_CODE =1;
    private static final String TAG = "TAGAllAnswers";
    RecyclerView recyclerView;
    AllAnswersAdapter allAnswersAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Answer> answerList ;
    public static int answerCounter ;
    DatabaseReference answerRef ;
    String questionId, examId ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_answers_adapter);

        answerList = AllQuestionsList.answerArrayList;
        questionId = getIntent().getStringExtra("QUESTION_ID");
        examId = getIntent().getStringExtra("EXAM_ID");

        recyclerView = findViewById(R.id.allAnswersListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        allAnswersAdapter = new AllAnswersAdapter(this, answerList);
        recyclerView.setAdapter(allAnswersAdapter);

        answerCounter = 0 ;
        DatabaseReference tempRef = ApplicationClass.mDatabase.getReference().child("Exam").child(examId)
                .child("Question").child(questionId).child("Answer");
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot answerSnapshot : dataSnapshot.getChildren()){
                    Answer answer = answerSnapshot.getValue(Answer.class);
                    if(answer != null){
                        int temp = answer.getId();
                        answerCounter = (answerCounter > temp) ? answerCounter : temp ;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    public void addNewAnswer(View view) {
        Intent intent = new Intent(getApplicationContext(), NewAnswer.class);
        intent.putExtra("FROM_LIST", true);
        intent.putExtra("EXAM_ID", examId);
        intent.putExtra("QUESTION_ID", questionId);
        startActivityForResult(intent, ADD_NEW_ANSWER_REQUEST_CODE);
    }

    private void updateAnswersList(){
        answerList = AllQuestionsList.answerArrayList ;
        allAnswersAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NEW_ANSWER_REQUEST_CODE)
            updateAnswersList();
    }


}
