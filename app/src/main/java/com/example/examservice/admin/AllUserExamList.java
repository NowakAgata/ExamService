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
import com.example.examservice.database.Exam;
import com.example.examservice.database.User;
import com.example.examservice.database.UserExam;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AllUserExamList extends AppCompatActivity {

    private static final int ADD_USER_REQUEST_CODE =1 ;
    private String TAG = "TAGAllUserExam" ;
    RecyclerView recyclerView;
    AllUsersAdapter usersAdapter ;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> allUsersList ;
    ArrayList<User> usersList ;
    ArrayList<UserExam> userExamsList ;
    DatabaseReference usersRef ;
    //Exam exam ;
    int examId ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_user_exam_list);

        examId = getIntent().getIntExtra("EXAM_ID", 0);

        allUsersList = ApplicationClass.allStudentsList;

        usersList = new ArrayList<>();
        getList();

        recyclerView = findViewById(R.id.allUserExamsListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        usersAdapter = new AllUsersAdapter(this, usersList);
        recyclerView.setAdapter(usersAdapter);

        usersRef = ApplicationClass.mDatabase.getReference().child("UserExam") ;

    }

    private void getList() {
        userExamsList = ExamAdministration.userExamsList;
        usersList.clear();

        for(UserExam userExam : userExamsList){
            if(userExam.getExam_id() == examId){
                User user = getUser(userExam.getUser_id());
                if(user != null){
                    usersList.add(user);
                    Log.d(TAG, "getList:" + user.toString());
                }
            }
        }
    }

    private User  getUser(int userId) {

        for(User user : allUsersList){
            if(user.getId() == userId){
                return user ;
            }
        }
        return null;
    }

    public void onUserExamDeleteClick(View view) {

        int i = (int) view.getTag();
        User user = usersList.get(i);
        int userId = user.getId();
        UserExam userExam = getUserExam(userId);
        if(userExam != null){
            Log.d(TAG,"trying to delete:" +  userExam.toString());
            String id = Integer.toString(userExam.getUser_exam_id());
            Log.d(TAG, "with id: " + id);
            usersRef.child(id).removeValue().addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "UserExam deleted");
                            getList();
                            usersAdapter.notifyDataSetChanged();
                        }
                    }
            ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Coś poszło nie tak") ;
                        }
            });
        }else{
            Log.d(TAG, "Something went wrong");
        }

    }

    public void onUserClick(View view){

    }

    private UserExam getUserExam(int userId) {

        for(UserExam userExam : userExamsList){
            if((userExam.getUser_id() == userId) && (userExam.getExam_id() == examId)){
                return userExam;
            }
        }
        return null ;
    }

    public void addNewUser(View view) {
        Intent intent = new Intent(getApplicationContext(), AddNewUserExam.class);
        intent.putExtra("EXAM_ID", examId);
        startActivityForResult(intent, ADD_USER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_USER_REQUEST_CODE && resultCode == 300){
            Log.d(TAG, "Wróciło");
            getList();
            usersAdapter.notifyDataSetChanged();
        }
    }
}
