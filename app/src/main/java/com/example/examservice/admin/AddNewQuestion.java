package com.example.examservice.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.Answer;
import com.example.examservice.database.Question;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AddNewQuestion extends AppCompatActivity {

    EditText etContent, etMaxAnswers ;
    String content ;
    RecyclerView recyclerView;
    AllAnswersAdapter allAnswersAdapter;
    RecyclerView.LayoutManager layoutManager;
    public static ArrayList<Answer> answerList ;
    public static String examId ;
    public static int questionId ;
    int maxAnswers ;
    DatabaseReference examRef ;

    private static final int NEW_ANSWER_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_new_question);

        etContent = findViewById(R.id.etQuestionContent);
        etMaxAnswers = findViewById(R.id.etMaxAnswers);

        answerList = new ArrayList<>();

        recyclerView = findViewById(R.id.littleAdminQuestionRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        allAnswersAdapter = new AllAnswersAdapter(this, answerList);
        recyclerView.setAdapter(allAnswersAdapter);

        examId = getIntent().getStringExtra("EXAM_ID");
        examRef = ApplicationClass.mDatabase.getReference().child("Exam");
    }

    public void addNewAnswer(View view) {

        Intent intent = new Intent(getApplicationContext(), NewAnswer.class);
        startActivityForResult(intent, NEW_ANSWER_REQUEST_CODE);

    }

    public void addNewQuestion(View view) {

        content = etContent.getText().toString();
        String maxAnswersStr = etMaxAnswers.getText().toString();
        maxAnswers = Integer.parseInt(maxAnswersStr);

        if(answerList.size() == 0){
            Toast.makeText(getApplicationContext(), "Answer list is empty. ", Toast.LENGTH_SHORT).show();
        } else if(content.isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.allFields, Toast.LENGTH_SHORT).show();
        } else{
            questionId = AllQuestionsList.questionsCount;
            questionId++;
            int examIdInt = Integer.parseInt(examId) ;
            Question question = new Question(content, examIdInt, questionId, maxAnswers);

            String questionIdStr = Integer.toString(questionId);
            examRef.child(examId).child("Question").child(questionIdStr).setValue(question);

            for(Answer answer : answerList){
                answer.setQuestion_id(questionId);
                String answerIdStr = Integer.toString(answer.getId());
                examRef.child(examId).child("Question").child(questionIdStr)
                        .child("Answer").child(answerIdStr).setValue(answer) ;
                setResult(RESULT_OK);
                finish();

            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_ANSWER_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                allAnswersAdapter.notifyDataSetChanged();
            }
        }
    }

}
