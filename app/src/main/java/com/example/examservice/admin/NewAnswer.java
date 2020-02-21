package com.example.examservice.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.Answer;
import com.google.firebase.database.DatabaseReference;

public class NewAnswer extends AppCompatActivity {

    EditText etContent ;
    Spinner correctSpinner ;
    boolean isCorrect, fromList ;
    String content ;
    String examId, questionId ;
    DatabaseReference answerRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_new_answer);

        fromList = getIntent().getBooleanExtra("FROM_LIST", false);
        if(fromList){
            examId = getIntent().getStringExtra("EXAM_ID");
            questionId = getIntent().getStringExtra("QUESTION_ID");
        }
        etContent = findViewById(R.id.answerContentEditTxt);
        correctSpinner = findViewById(R.id.answerCorrectSpinner);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.boolArray, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        correctSpinner.setAdapter(spinnerAdapter);

        correctSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isCorrect = (position == 0) ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void addNewAnswerToList(View view) {
        content = etContent.getText().toString();
        if(content.isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.allFields, Toast.LENGTH_SHORT).show();
        } else{
            if(fromList){
                int answersCounter = AllAnswersList.answerCounter;
                answersCounter ++ ;
                int intExamId = Integer.parseInt(examId);
                int intQuestionId = Integer.parseInt(questionId);
                String id = Integer.toString(answersCounter);
                answerRef = ApplicationClass.mDatabase.getReference().child("Exam").child(examId).child("Question")
                        .child(questionId).child("Answer").child(id);

                Answer answer = new Answer(content, intExamId, answersCounter, isCorrect, intQuestionId );
                answerRef.setValue(answer);
                setResult(RESULT_OK);
                finish();
            }else {
                int id = AddNewQuestion.answerList.size();
                int examId = Integer.parseInt(AddNewQuestion.examId);
                Answer answer = new Answer(content, examId, id, isCorrect, 0);
                AddNewQuestion.answerList.add(answer);
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
