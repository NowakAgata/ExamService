package com.example.examservice.professor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class AllQuestionsList extends AppCompatActivity {

    private final String TAG = "TAGAllQuestions";
    private final int ALL_ANSWERS_REQUEST_CODE = 1 ;
    String exam, question ;
    RecyclerView recyclerView ;
    RecyclerView.LayoutManager layoutManager ;
    AllQuestionsAdapter allQuestionsAdapter ;
    ArrayList<Question> questionsList ;
    public static ArrayList<Answer> answerArrayList ;
    DatabaseReference answerRef ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_all_questions_list);

        questionsList = ExamOptions.questionsList;
        answerArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.allQuestionsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        allQuestionsAdapter = new AllQuestionsAdapter(this, questionsList);
        recyclerView.setAdapter(allQuestionsAdapter);


    }

    public void onQuestionClick(View view) {
        int i = (int) view.getTag();
        int question_id = questionsList.get(i).getId();
        int exam_id = getIntent().getIntExtra("EXAM_ID", 0);

        exam = Integer.toString(exam_id);
        question = Integer.toString(question_id);

        updateAnswersList();
        Intent intent = new Intent(getApplicationContext(), AllAnswersList.class);
        intent.putExtra("EXAM_ID", exam);
        intent.putExtra("QUESTION_ID", question);
        startActivityForResult(intent, ALL_ANSWERS_REQUEST_CODE);

    }

    private void updateAnswersList(){

        answerRef = ApplicationClass.mDatabase.getReference().child("Exam").child(exam)
                .child("Question").child(question).child("Answer");

        answerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                answerArrayList.clear();
                for(DataSnapshot answerSnapshot : dataSnapshot.getChildren()){
                    Answer temp = answerSnapshot.getValue(Answer.class);
                    if(temp != null){
                        Log.d("Answer: " , temp.toString());
                        answerArrayList.add(temp);
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

        if(requestCode == ALL_ANSWERS_REQUEST_CODE){
            if(resultCode == 100){
                setResult(100);
                finish();
            }else if (resultCode == 200){
                setResult(200);
                finish();
            }
        }
    }
}
