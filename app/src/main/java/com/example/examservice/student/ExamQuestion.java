package com.example.examservice.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.Answer;
import com.example.examservice.database.Date;
import com.example.examservice.database.Exam;
import com.example.examservice.database.Question;
import com.example.examservice.database.Result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventListener;

public class ExamQuestion extends AppCompatActivity {

    private final String TAG = "TAGExamQuestion" ;
    private CountDownTimer countDownTimer;
    TextView tvName, tvInfo, tvQuestionsLeft, tvTime, tvQuestion ;
    Button button ;
    LinearLayout fragmentLayout ;
    ArrayList<Question> questionsList, allQuestionsList;
    ArrayList<Answer> answersList ;
    ArrayList<Answer> tempAnswerList ;
    DatabaseReference answersRef ;
    DatabaseReference resultRef ;
    DatabaseReference userExamRef ;
    Question question ;
    Exam exam ;
    int questions, counter, points ;
    int resultCounter =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_exam_question);

        Log.d(TAG, "jestem w examQuestion");
        questionsList = new ArrayList<>();
        answersList = new ArrayList<>();
        tempAnswerList = new ArrayList<>();
        allQuestionsList = new ArrayList<>();
        counter = 0;
        points = 0 ;

        button = findViewById(R.id.nextQuestionButton);
        button.setText(R.string.start);

        fragmentLayout = findViewById(R.id.answersFragment);
        answersRef = ApplicationClass.mDatabase.getReference();

        tvName = findViewById(R.id.activeExamName);
        tvInfo = findViewById(R.id.activeExamInfo);
        tvQuestionsLeft = findViewById(R.id.activeExamQuestionsLeft);
        tvTime = findViewById(R.id.activeExamTimeLeft);
        tvQuestion = findViewById(R.id.activeExamQuestionContent);

        exam = SingleExamView.exam ;
        allQuestionsList = exam.getQuestionsList();
        questions = exam.getMax_questions() ;
        setQuestion();
        getAnswers();


        tvName.setText(exam.getName());
        tvInfo.setText(exam.getAdditional_information());
        tvQuestionsLeft.setText(Integer.toString(questions));
        tvTime.setText(Integer.toString(exam.getDuration_of_exam()));

        resultRef = ApplicationClass.mDatabase.getReference().child("UserExam");
        userExamRef = ApplicationClass.mDatabase.getReference().child("UserExam");

        String userExamId = Integer.toString(exam.getUserExamId());
        DatabaseReference tempRef = ApplicationClass.mDatabase.getReference().child("UserExam").child(userExamId)
                .child("Result");
        tempRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot resultSnapshot : dataSnapshot.getChildren()){
                            Result result = resultSnapshot.getValue(Result.class);
                            if(result != null) {
                                int temp = result.getId();
                                resultCounter = (resultCounter > temp) ? resultCounter : temp ;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

    }

    private void setQuestion() {

         if(allQuestionsList.size() < questions){
             questions = allQuestionsList.size();
             questionsList = allQuestionsList;
         }else{
             RandomIntsWithoutRepetition rand = new RandomIntsWithoutRepetition(allQuestionsList.size());
             int index;
             for(int i = 0; i<questions; i++){
                 index = rand.get();
                 questionsList.add(allQuestionsList.get(index));
                 Log.d("TAGWylosowano: " , allQuestionsList.get(index).toString());
             }
         }
         question = questionsList.get(0);
         Log.d(TAG, "Ustawiono question: " + question.toString());

    }

    private void getAnswers() {
        String examId = Integer.toString(exam.getExam_id());
        String id;
        for(Question question : questionsList) {
            id = Integer.toString(question.getId());
            answersRef.child("Exam").child(examId).child("Question").child(id).child("Answer").addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot answerSnapshot : dataSnapshot.getChildren()) {
                                Answer answer = answerSnapshot.getValue(Answer.class);
                                if (answer != null) {
                                    Log.d(TAG, answer.toString());
                                    answersList.add(answer);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, databaseError.getMessage());
                        }
                    }
            );
        }
    }

    private void sortAnswers(){
        for(int i =0; i<questionsList.size(); i++){
            int id = questionsList.get(i).getId();
            for(Answer answer : answersList){
                if(answer.getQuestion_id() == id){
                    questionsList.get(i).getAnswersList().add(answer);
                }
            }
        }
    }

    private void updateTextVies() {

        tvQuestionsLeft.setText(Integer.toString((questions - counter)));
        tvQuestion.setText(question.getContent());
        refreshQuestion();

    }

    private void refreshQuestion() {


        ArrayList<Answer> allAnswersList = question.getAnswersList();
        tempAnswerList.clear();
        int answers = question.getMax_answers() ;
        if(allAnswersList.size() <= answers){
            tempAnswerList = allAnswersList;
        }else{
            RandomIntsWithoutRepetition rand = new RandomIntsWithoutRepetition(allAnswersList.size());
            int index;
            for(int i = 0; i<answers; i++){
                index = rand.get();
                tempAnswerList.add(allAnswersList.get(index));
                Log.d("TAGWylosowano: " , allAnswersList.get(index).toString());
            }
        }

        int i =0;
        for( final Answer answer : tempAnswerList){
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(i);
            checkBox.setText(answer.getContent());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tempAnswerList.get(buttonView.getId()).setIs_active(isChecked);
                }
            });

            fragmentLayout.addView(checkBox);
            i++;
        }
    }

    public void onNextClick(View view) {

        Log.d(TAG, "counter= " + counter) ;
        if(counter == 0){
            sortAnswers();
            counter++ ;
            if(counter == questions){
                button.setText("ZAKOŃCZ");
            }
            updateTextVies();
            startTimer();
        }else{
            if((counter+1) == questions){
                button.setText(R.string.finish);
            }
            button.setText("DALEJ");
            if(givePoint()) {
                Log.d(TAG, "jest punkt");
                points++ ;
            }
            if(counter == questions){
                addResult();
            }else {
                question = questionsList.get(counter);
                counter++;
                fragmentLayout.removeAllViews();
                updateTextVies();
            }
            }
    }

    private  boolean givePoint() {

        for (Answer answer : tempAnswerList) {
            if (answer.getIs_true() != answer.isIs_active()) {
                Log.d(TAG, "to nie jest równe: " + answer.toString());
                return false;
            }
        }
        return true;
    }

    private void addResult(){
        boolean passed = exam.isPassed(questions, points);
        int resultLastId = resultCounter ;
        resultLastId ++ ;
        String userExamId = Integer.toString(exam.getUserExamId());
        String resultId = Integer.toString(resultLastId);
        Result result = new Result(resultLastId, points, passed, exam.getExam_id());
        resultRef.child(userExamId).child("Result").child(resultId).setValue(result);
        if(passed) {
            Date date = new Date();
            userExamRef.child(userExamId).child("date_of_resolve_exam").setValue(date);
        }
        setResult(666);
        finish();
    }

    private void startTimer(){

        int duration = exam.getDuration_of_exam();
        duration *=60000;
        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int time  = (int) (millisUntilFinished/1000);
                int minutes, seconds ;
                minutes = time/60 ;
                seconds = time%60 ;
                String temp = "" + minutes + ":" + seconds ;
                tvTime.setText(temp);
            }

            @Override
            public void onFinish() {
                addResult();
            }
        }.start();

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "You cant go back now!", Toast.LENGTH_SHORT).show();
    }
}
