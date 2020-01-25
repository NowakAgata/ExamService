package com.example.examservice.professor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.admin.AllUsersAdapter;
import com.example.examservice.database.Date;
import com.example.examservice.database.Exam;
import com.example.examservice.database.User;
import com.example.examservice.database.UserExam;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashSet;

public class AllStudentsList extends AppCompatActivity {

    RecyclerView recyclerView;
    AllStudentsAdapter usersAdapter ;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> allUsersList ;
    ArrayList<User> oneGroupList ;
    DatabaseReference userExamRef ;
    boolean flagFromAddStudent ;
    boolean flagFromSeeUserExam ;
    HashSet<Integer> userIdsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_all_students_list);

        flagFromAddStudent = getIntent().getBooleanExtra("FROM_ADD_STUDENT", false);
        flagFromSeeUserExam = getIntent().getBooleanExtra("FROM_SEE_USER_EXAM", false);

        allUsersList = ApplicationClass.allUsersList ;
        oneGroupList = new ArrayList<>();
        String group = getIntent().getStringExtra("GROUP");
        if(flagFromSeeUserExam){
            userIdsArray = ExamOptions.userIdsArray ;
            for(User temp : allUsersList){
                if(userIdsArray.contains(temp.getId())){
                    oneGroupList.add(temp);
                }
            }
        }
        else {
            if (group.equals("all")) {
                oneGroupList = allUsersList;
            } else {
                for (User temp : allUsersList) {
                    if (temp.getGroup_of_students().equals(group)) {
                        oneGroupList.add(temp);
                    }
                }
            }
        }

        recyclerView = findViewById(R.id.allStudentsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        usersAdapter = new AllStudentsAdapter(this, oneGroupList);
        recyclerView.setAdapter(usersAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onStudentClick(View view) {

        if(flagFromAddStudent) {
            int index = (int) view.getTag();
            User user = oneGroupList.get(index);
            Exam exam = AllExamsList.currentExam ;
            int userId = user.getId();
            int examId = exam.getExam_id();

            int userExamId = ExamOptions.userExamLastId ;
            userExamId ++ ;
            String id = Integer.toString(userExamId);

            UserExam userExam = new UserExam(examId,userExamId, userId);

            userExamRef = ApplicationClass.mDatabase.getReference().child("UserExam").child(id);
            userExamRef.setValue(userExam) ;

            setResult(RESULT_OK);
            finish();
        }
    }
}
