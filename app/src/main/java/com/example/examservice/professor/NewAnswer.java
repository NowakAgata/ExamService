package com.example.examservice.professor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.examservice.R;
import com.example.examservice.database.Answer;

public class NewAnswer extends AppCompatActivity {

    EditText etContent ;
    Spinner correctSpinner ;
    boolean isCorrect ;
    String content ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_new_answer);

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
            int id = AddQuestion.answerList.size();
            Answer answer = new Answer(content, AddQuestion.examId, id, false, isCorrect, 0 );
            AddQuestion.answerList.add(answer);
            setResult(RESULT_OK);
            finish();
        }
    }
}
