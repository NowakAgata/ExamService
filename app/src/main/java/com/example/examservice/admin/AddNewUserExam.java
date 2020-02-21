package com.example.examservice.admin;

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
import com.example.examservice.database.User;
import com.example.examservice.database.UserExam;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AddNewUserExam extends AppCompatActivity {

    private static final int ADD_NEW_USER_REQUEST_CODE = 1;
    private String TAG = "TAGAllUsers" ;
    RecyclerView recyclerView;
    AddUserExamAdapter usersAdapter ;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> allUsersList ;
    DatabaseReference usersRef ;
    DatabaseReference userExamRef ;
    String role ;
    int examId ;
    public static User chosenUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_new_user_exam);

        Intent intent = getIntent();

        role = ApplicationClass.STUDENT_ROLE ;
        examId = intent.getIntExtra("EXAM_ID", 0);

        allUsersList = ApplicationClass.allStudentsList;

        recyclerView = findViewById(R.id.addUserExamListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        usersAdapter = new AddUserExamAdapter(this, allUsersList);
        recyclerView.setAdapter(usersAdapter);

        usersRef = ApplicationClass.mDatabase.getReference().child("User") ;

    }

    public void onUserClick(View view) {
        int i = (int) view.getTag();
        chosenUser = allUsersList.get(i);

        int userExamId = ExamAdministration.userExamsCount ;
        userExamId ++ ;
        String id = Integer.toString(userExamId);
        UserExam userExam = new UserExam(examId, userExamId, chosenUser.getId());
        Log.d(TAG, "Adding userExam : " + userExam.toString());

        userExamRef = ApplicationClass.mDatabase.getReference().child("UserExam").child(id);
        userExamRef.setValue(userExam) ;

        setResult(300);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NEW_USER_REQUEST_CODE){
            allUsersList = ApplicationClass.allStudentsList;
        }
    }


}
