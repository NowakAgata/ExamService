package com.example.examservice.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.examservice.R;
import com.example.examservice.database.Answer;
import com.example.examservice.database.Exam;
import com.example.examservice.database.Question;

import java.util.ArrayList;

public class ExamQuestion extends AppCompatActivity {

    private final String TAG = "TAGExamQuestion" ;
    TextView tvName, tvInfo, tvQuestionsLeft, tvTime, tvQuestion ;
    RadioButton radio1, radio2, radio3, radio4 ;
    ArrayList<Question> questionsList ;
    ArrayList<Answer> answersList ;
    Question question ;
    Exam exam ;
    int counter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_exam_question);

        //Log.d(TAG, "jestem w examQuestion");
        questionsList = new ArrayList<>();
        answersList = new ArrayList<>();

        counter = 0;

        tvName = findViewById(R.id.activeExamName);
        tvInfo = findViewById(R.id.activeExamInfo);
        tvQuestionsLeft = findViewById(R.id.activeExamQuestionsLeft);
        tvTime = findViewById(R.id.activeExamTimeLeft);
        tvQuestion = findViewById(R.id.activeExamQuestionContent);

        radio1 = findViewById(R.id.activeExamAnswer1);
        radio2 = findViewById(R.id.activeExamAnswer2);
        radio3 = findViewById(R.id.activeExamAnswer3);
        radio4 = findViewById(R.id.activeExamAnswer4);

        exam = SingleExamView.exam ;
        questionsList = exam.getQuestionsList();

        try {
            Log.d(TAG, exam.toString());
            for (Question quest : questionsList) {
                Log.d(TAG, quest.toString());
            }
        }catch(Exception e){
               Log.d(TAG, e.getMessage());
        }
        tvName.setText(exam.getName());
        tvInfo.setText(exam.getAdditional_information());


        updateTextVies();


    }

    private void updateTextVies() {
        String time = Integer.toString(exam.getDuration_of_exam());
        if(questionsList.size() > 0){
            question = questionsList.get(counter);
            tvQuestionsLeft.setText(questionsList.size());
            tvQuestion.setText(question.getContent());
            tvTime.setText(time);
            answersList = question.getAnswersList();

            tvQuestion.setText(question.getContent());
        }else{
            Log.d(TAG, "Coś jest KURWA nie tak");
        }




    }

    public void onNextClick(View view) {


    }
}
