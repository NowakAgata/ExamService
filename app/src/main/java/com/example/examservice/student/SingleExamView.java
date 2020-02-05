package com.example.examservice.student;

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
import com.example.examservice.database.Answer;
import com.example.examservice.database.Date;
import com.example.examservice.database.Exam;
import com.example.examservice.database.LearningMaterialsGroup;
import com.example.examservice.database.LearningMaterialsGroupExam;
import com.example.examservice.database.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SingleExamView extends AppCompatActivity {

    private final String TAG = "TAGSingleExam";
    private final int START_EXAM_REQUEST_CODE = 1;
    TextView tvName, tvInfo, tvAvailable, tvIsLearning , tvDuration, tvAttempts, tvQuestions ;
    String name, info, availableStr, learningStr, durationStr, attemptsStr, questionsStr, examId;
    Date to ;
    boolean isLearning ;
    int duration, attempts, questions;
    public static Exam exam ;
    public static ArrayList<Question> questionsList ;
    public static ArrayList<LearningMaterialsGroupExam> groupExamList ;
    public static ArrayList<LearningMaterialsGroup> groupList ;
    DatabaseReference questionsRef ;
    DatabaseReference answersRef ;
    DatabaseReference groupsExamRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_single_exam_view);

        tvName = findViewById(R.id.studentExamNameTxtView);
        tvInfo = findViewById(R.id.studentExamInfoTxtView);
        tvAvailable = findViewById(R.id.studentExamAvailableFrom);
        tvIsLearning = findViewById(R.id.studentExamIsLearningReq);
        tvDuration =findViewById(R.id.studentExamDurationTime);
        tvAttempts =findViewById(R.id.studentExamAttemptsLeft);
        tvQuestions=findViewById(R.id.studentexamQuestions);

        exam = AllExamsList.currentExam ;
        questionsList = new ArrayList<>();
        groupExamList = new ArrayList<>();
        groupList = new ArrayList<>();

        examId = Integer.toString(exam.getExam_id());

        questionsRef = ApplicationClass.mDatabase.getReference() ;
        answersRef = ApplicationClass.mDatabase.getReference().child("Exam").child(examId).child("Question");

        questionsRef.child("Exam").child(examId).child("Question").addListenerForSingleValueEvent(
                new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionsList.clear();
                Log.d(TAG, "Downloading questions for exam: " +examId );
                for(DataSnapshot questionSnapshot : dataSnapshot.getChildren()){
                    Question question = questionSnapshot.getValue(Question.class);
                    if(question != null){
                        //addAnswersToQuestion(question);
                        Log.i(TAG, question.toString());
                        //question.setListOfAnswers(answersList);
                        questionsList.add(question);

                    }
                }
                exam.setQuestionsList(questionsList);
                updateTextViews();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        groupsExamRef = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroupExam");
        groupsExamRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupExamList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    LearningMaterialsGroupExam group = snapshot.getValue(LearningMaterialsGroupExam.class);
                    if((group != null) && (group.getExam_id() == exam.getExam_id())){
                        groupExamList.add(group);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void showMaterials(View view) {
        getList();
        Intent intent = new Intent(getApplicationContext(), AllMaterialsGroups.class);
        startActivity(intent);
    }

    private void getList() {
        groupList.clear();
        ArrayList<LearningMaterialsGroup> allGroupsList = AllExamsList.groupsList ;
        for(LearningMaterialsGroupExam groupExam : groupExamList){
            int groupId = groupExam.getLearning_materials_group_id();
            for(LearningMaterialsGroup group : allGroupsList){
                if(group.getLearning_materials_group_id() == groupId){
                    groupList.add(group);
                }
            }
        }
    }

    public void onStartExamClick(View view) {


        if(questionsList.size() == 0){
            Toast.makeText(getApplicationContext(), "Exam has no question, you cant start it", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Log.d(TAG, "starting exam");
            Intent intent = new Intent(getApplicationContext(), ExamQuestion.class);
            startActivityForResult(intent, START_EXAM_REQUEST_CODE);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    private void updateTextViews() {
        name = exam.getName();
        info = exam.getAdditional_information();
        to = exam.getEnd_date();
        isLearning = exam.getLearning_required();
        duration = exam.getDuration_of_exam();
        attempts = exam.getMax_attempts();
        attempts -= (exam.getResultListSize());
        questions = exam.getMax_questions();
        questions = (questionsList.size() < questions) ? questionsList.size() : questions ;


        availableStr = to.getDate().toString().substring(0,10);
        learningStr = isLearning ? "yes" : "no" ;
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

        Log.d(TAG, "Update done");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 666){
            setResult(666);
            finish();
        }
    }


}
