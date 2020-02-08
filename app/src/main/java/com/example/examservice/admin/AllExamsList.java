package com.example.examservice.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.Exam;
import com.example.examservice.database.User;
import com.example.examservice.database.UserExam;
import com.example.examservice.professor.AllExamsAdapter;

import java.util.ArrayList;

public class AllExamsList extends AppCompatActivity {

    private final int EDIT_EXAM_REQUEST_CODE =1;
    private final int EDIT_USERS_REQUEST_CODE =2;
    private final int EDIT_QUESTIONS_REQUEST_CODE =3;
    private final int EDIT_MATERIALS_REQUEST_CODE =4;
    ArrayList<Exam> examsList ;
    ArrayList<User> allStudents ;
    ArrayList<User> usersList ;
    RecyclerView recyclerView;
    AllExamsAdapter examsAdapter ;
    RecyclerView.LayoutManager layoutManager;
    public static Exam currentExam ;
    boolean showUsers, showExam, showQuestions, showFiles ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_exams_list);

        Intent intent = getIntent();
        showUsers = intent.getBooleanExtra("SHOW_USERS", false);
        showExam = intent.getBooleanExtra("SHOW_EXAM", false);
        showQuestions = intent.getBooleanExtra("SHOW_QUESTIONS", false);
        showFiles = intent.getBooleanExtra("SHOW_FILES", false);

        examsList = ExamAdministration.examsList;
        allStudents = ApplicationClass.allStudentsList ;
        usersList = new ArrayList<>();

        recyclerView = findViewById(R.id.allExamsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        examsAdapter = new AllExamsAdapter(this, examsList);
        recyclerView.setAdapter(examsAdapter);

    }



    public void onExamClick(View view){
        int index = (int) view.getTag();
        currentExam = examsList.get(index);
        if(showExam) {
            Intent intent = new Intent(getApplicationContext(), ExamView.class);
            startActivityForResult(intent, EDIT_EXAM_REQUEST_CODE);
        }else if(showUsers){
            Intent intent = new Intent(getApplicationContext(), AllUserExamList.class);
            intent.putExtra("EXAM_ID", currentExam.getExam_id());
            startActivityForResult(intent, EDIT_USERS_REQUEST_CODE);
        }else if(showQuestions){
            Intent intent = new Intent(getApplicationContext(), AllQuestionsList.class);
            startActivityForResult(intent, EDIT_QUESTIONS_REQUEST_CODE);
        }else if(showFiles){
            Intent intent = new Intent(getApplicationContext(), FileAdministration.class);
            intent.putExtra("SHOW_FILES", true);
            startActivityForResult(intent, EDIT_MATERIALS_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_EXAM_REQUEST_CODE && resultCode == RESULT_OK){
            examsList = ExamAdministration.examsList;
            examsAdapter.notifyDataSetChanged();
        }
    }
}
