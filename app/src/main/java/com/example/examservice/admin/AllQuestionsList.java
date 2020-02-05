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

public class AllQuestionsList extends AppCompatActivity {

    private static final int ADD_NEW_QUESTION_REQUEST = 1;
    private final String TAG = "TAGAllQuestions" ;
    Exam exam ;
    public static Question currentQuestion;
    String examId, questionId ;
    ArrayList<Question> questionsList ;
    public static int questionsCount ;
    public static ArrayList<Answer> answerArrayList ;
    RecyclerView recyclerView;
    AllQuestionsAdapter allQuestionsAdapter ;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference questionsRef ;
    DatabaseReference answerRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_questions_list);

        exam = AllExamsList.currentExam ;
        examId = Integer.toString(exam.getExam_id());
        questionsRef = ApplicationClass.mDatabase.getReference().child("Exam").child(examId).child("Question");
        questionsCount = 0;
        questionsList = new ArrayList<>();
        answerArrayList = new ArrayList<>();
        getList();

        recyclerView = findViewById(R.id.allAdminQuestionsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        allQuestionsAdapter = new AllQuestionsAdapter(this, questionsList);
        recyclerView.setAdapter(allQuestionsAdapter);
    }

    private void getList() {

        questionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionsList.clear();
                for(DataSnapshot questionsSnapsot : dataSnapshot.getChildren()){
                    Question question = questionsSnapsot.getValue(Question.class);
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

    public void onQuestionClick(View view) {
        int i = (int) view.getTag();
        int question_id = questionsList.get(i).getId();
        String id = Integer.toString(question_id) ;
        updateAnswersList(id);

        Intent intent = new Intent(getApplicationContext(), AllAnswersList.class);
        intent.putExtra("QUESTION_ID", id);
        intent.putExtra("EXAM_ID", examId);
        startActivity(intent);

    }

    public void onQuestionDeleteClick(View view) {

        int index = (int) view.getTag() ;
        Question question = questionsList.get(index) ;
        questionId = Integer.toString(question.getId());
        questionsRef = ApplicationClass.mDatabase.getReference().child("Exam").child(examId)
                .child("Question").child(questionId);
        Log.d(TAG, "Deleting question with id: " + questionId);

        questionsRef.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               Log.d(TAG, "Deleting complete");
                                               setResult(100);
                                               finish();
                                           }
                                       }
                ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Something went wrong");
                setResult(200);
                finish();
            }
        });
    }

    private void updateAnswersList(String question) {

        answerRef = ApplicationClass.mDatabase.getReference().child("Exam").child(examId)
                .child("Question").child(question).child("Answer");

        answerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                answerArrayList.clear();
                for (DataSnapshot answerSnapshot : dataSnapshot.getChildren()) {
                    Answer temp = answerSnapshot.getValue(Answer.class);
                    if (temp != null) {
                        Log.d("Answer: ", temp.toString());
                        answerArrayList.add(temp);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addNewQuestion(View view) {
        Intent intent = new Intent(getApplicationContext(), AddNewQuestion.class);
        intent.putExtra("EXAM_ID", examId);
        startActivityForResult(intent, ADD_NEW_QUESTION_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NEW_QUESTION_REQUEST && resultCode == RESULT_OK){
            allQuestionsAdapter.notifyDataSetChanged();
        }
    }


}
