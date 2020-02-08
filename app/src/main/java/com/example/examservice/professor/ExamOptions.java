package com.example.examservice.professor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.Exam;
import com.example.examservice.database.LearningMaterialsGroupExam;
import com.example.examservice.database.Question;
import com.example.examservice.database.UserExam;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class ExamOptions extends AppCompatActivity {

    private static final int ADD_GROUP_TO_EXAM_REQUEST_CODE = 5;
    public Exam exam ;

    private static final String TAG = "TAGExamOptions";
    private static final int SINGLE_EXAM_VIEW_REQUEST_CODE = 1;
    private static final int QUESTION_LIST_REQUEST_CODE = 2;
    private static final int ADD_QUESTION_REQUEST_CODE = 3;
    private static final int ADD_STUDENT_TO_EXAM_REQUEST_CODE = 4;

    public static ArrayList<Question> questionsList ;
    DatabaseReference questionsRef ;
    public static int questionsCount ;
    public static int qroupExamCount ;
    DatabaseReference examRef ;
    DatabaseReference userExamRef ;
    DatabaseReference groupExamRef ;
    ArrayList<UserExam> userExamList ;
    public static ArrayList<LearningMaterialsGroupExam> groupExamList ;
    public static HashSet<Integer> userIdsArray ;
    public static int userExamLastId ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_exam_options);


        userExamList = new ArrayList<>();
        questionsList = new ArrayList<>();
        groupExamList = new ArrayList<>();
        userIdsArray = new HashSet<>();
        exam = AllExamsList.currentExam ;
        qroupExamCount =0 ;


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

        userExamRef= ApplicationClass.mDatabase.getReference().child("UserExam");
        userExamRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userExamList.clear();
                for(DataSnapshot userExamSnapshot : dataSnapshot.getChildren()){
                    UserExam userExam = userExamSnapshot.getValue(UserExam.class);
                    if(userExam != null){
                        int tempId = userExam.getUser_exam_id();
                        Log.d(TAG, userExam.toString()) ;
                        userExamLastId = ( tempId > userExamLastId) ? tempId : userExamLastId ;
                        if(exam.getExam_id() == userExam.getExam_id()){
                            userExamList.add(userExam);
                            userIdsArray.add(userExam.getUser_id());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        groupExamRef= ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroupExam");
        groupExamRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupExamList.clear();
                for(DataSnapshot groupExamSnapshot : dataSnapshot.getChildren()){
                    LearningMaterialsGroupExam groupExam = groupExamSnapshot.getValue(LearningMaterialsGroupExam.class);
                    if(groupExam != null){
                        int temp = groupExam.getId();
                        qroupExamCount = (qroupExamCount > temp) ? qroupExamCount : temp ;
                        groupExamList.add(groupExam);
                        Log.d(TAG, groupExam.toString());
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

    public void addStudentToExam(View view) {

        Intent intent = new Intent(getApplicationContext(), StudentGroupsList.class);
        intent.putExtra("FROM_ADD_STUDENT", true);
        startActivityForResult(intent, ADD_STUDENT_TO_EXAM_REQUEST_CODE);
    }

    public void seeStudentToExamList(View view) {
        Intent intent = new Intent(getApplicationContext(), AllStudentsList.class);
        intent.putExtra("FROM_SEE_USER_EXAM", true );
        startActivity(intent);
    }

    public void seeMaterials(View view) {
        Intent intent = new Intent(getApplicationContext(), AllMaterialsGroup.class);
        intent.putExtra("SHOW_FILES", true);
        startActivityForResult(intent, ADD_GROUP_TO_EXAM_REQUEST_CODE);

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
                Toast.makeText(getApplicationContext(), "Something went wrong with deleting", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
