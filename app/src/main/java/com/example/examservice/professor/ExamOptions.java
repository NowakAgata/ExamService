package com.example.examservice.professor;

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
import com.example.examservice.database.Exam;
import com.example.examservice.database.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExamOptions extends AppCompatActivity {

    public Exam exam ;
    TextView tvName, tvInfo ;

    private static final String TAG = "TAGExamOptions";
    private static final int SINGLE_EXAM_VIEW_REQUEST_CODE = 1;
    private static final int QUESTION_LIST_REQUEST_CODE = 2;
    private static final int ADD_QUESTION_REQUEST_CODE = 3;
    public static ArrayList<Question> questionsList ;
    DatabaseReference questionsRef ;
    public static int questionsCount ;
    DatabaseReference examRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_exam_options);

        tvName = findViewById(R.id.professorExamNameTxt);
        tvInfo = findViewById(R.id.professorExamInfoTxt);

        questionsList = new ArrayList<>();
        exam = AllExamsList.currentExam ;

        tvName.setText(exam.getName());
        tvInfo.setText(exam.getAdditional_information());

        String examIdString = Integer.toString(exam.getExam_id());

        questionsRef = ApplicationClass.mDatabase.getReference().child("Exam").child(examIdString).child("Question");

        questionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionsList.clear();
                for(DataSnapshot questionSnapshot : dataSnapshot.getChildren()){
                    Question question = questionSnapshot.getValue(Question.class);
                    if(question != null){
                        int temp = question.getId();
                        questionsCount = (temp > questionsCount) ? temp : questionsCount ;
                        questionsList.add(question);
                        Log.d(TAG, question.toString());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void singleExamView(View view) {
        Intent intent = new Intent(getApplicationContext(), SingleExamView.class);
        startActivityForResult(intent, SINGLE_EXAM_VIEW_REQUEST_CODE);
    }

    public void questionsList(View view) {
        Intent intent = new Intent(getApplicationContext(), AllQuestionsList.class);
        intent.putExtra("EXAM_ID", exam.getExam_id());
        startActivityForResult(intent, QUESTION_LIST_REQUEST_CODE);
    }

    public void addQuestion(View view) {
        Intent intent = new Intent(getApplicationContext(), AddQuestion.class);
        startActivityForResult(intent, ADD_QUESTION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SINGLE_EXAM_VIEW_REQUEST_CODE){
            exam = AllExamsList.currentExam ;

        }else if(requestCode == ADD_QUESTION_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                Toast.makeText(getApplicationContext(), "Question added successfully.", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == QUESTION_LIST_REQUEST_CODE){
            if(resultCode == 100){
                Toast.makeText(getApplicationContext(), "Question deleted successfully.", Toast.LENGTH_SHORT).show();
            }else if(resultCode == 200){
                Toast.makeText(getApplicationContext(), "QSomething went wrong with deleting", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void deleteExam(View view) {

        String examId = Integer.toString(exam.getExam_id());
        Log.d(TAG, "Deleting exam with id: "+ examId);
        examRef = ApplicationClass.mDatabase.getReference().child("Exam").child(examId);
        examRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "Deleting completed");
                setResult(100);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Something went wrong");
                setResult(200);
                finish();
            }
        });

    }
}
