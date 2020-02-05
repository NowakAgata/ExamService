package com.example.examservice.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.RegisterActivity;
import com.example.examservice.database.User;
import com.example.examservice.database.UserExam;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AllUsersListActivity extends AppCompatActivity {

    private String TAG = " TAGAllUsers" ;
    RecyclerView recyclerView;
    AllUsersAdapter usersAdapter ;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> allUsersList ;
    DatabaseReference usersRef ;
    DatabaseReference userExamRef ;
    String role ;
    boolean addUserExam ;
    int examId ;
    public static User chosenUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_professors_list);

        Intent intent = getIntent();
        addUserExam = intent.getBooleanExtra("ADD_USER", false);
        if(addUserExam){
            role = ApplicationClass.STUDENT_ROLE ;
            examId = intent.getIntExtra("EXAM_ID", 0);
        }else {
            role = intent.getStringExtra("ROLE");
        }
        getList();

        recyclerView = findViewById(R.id.allUsersListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        usersAdapter = new AllUsersAdapter(this, allUsersList);
        recyclerView.setAdapter(usersAdapter);

        usersRef = ApplicationClass.mDatabase.getReference().child("User") ;

    }

    private void getList() {
        switch(role)
        {
            case ApplicationClass.ADMIN_ROLE:
                allUsersList = ApplicationClass.allAdminsList;
                break;
            case ApplicationClass.PROFESSOR_ROLE:
                allUsersList = ApplicationClass.allProfessorsList;
                break;
            case ApplicationClass.STUDENT_ROLE:
                allUsersList = ApplicationClass.allStudentsList;
                break;
            default:
                allUsersList = ApplicationClass.allUsersList;
                break ;
        }
    }

    public  void addNewUser(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    public void onUserDeleteClick(View view) {
        int i = (int) view.getTag();
        User user = allUsersList.get(i);
        String id = Integer.toString(user.getId());
        usersRef.child(id).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        getList();
                        usersAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Something went wrong");
            }
        });


    }

    public void onUserClick(View view) {
        int i = (int) view.getTag();
        chosenUser = allUsersList.get(i);
        if(addUserExam){

            int userExamId = ExamAdministration.userExamsCount ;
            userExamId ++ ;
            String id = Integer.toString(userExamId);
            UserExam userExam = new UserExam(examId, userExamId, chosenUser.getId());
            Log.d(TAG, "Adding userExam : " + userExam.toString());

            userExamRef = ApplicationClass.mDatabase.getReference().child("UserExam").child(id);
            userExamRef.setValue(userExam) ;

            setResult(300);
            finish();
        }else {
            Intent intent = new Intent(getApplicationContext(), UserView.class);
            startActivity(intent);
        }
    }
}
