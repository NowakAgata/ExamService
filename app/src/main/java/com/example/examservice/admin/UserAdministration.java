package com.example.examservice.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.UserExam;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserAdministration extends AppCompatActivity {

    public static ArrayList<UserExam> userExamList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_administration);

        DatabaseReference userExamRef = ApplicationClass.mDatabase.getReference().child("UserExam");
        userExamList = new ArrayList<>();
        userExamRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userExamList.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    UserExam userExam = snap.getValue(UserExam.class);
                    if(userExam != null){
                        userExamList.add(userExam);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void allProfessors(View view) {
        Intent intent = new Intent(getApplicationContext(), AllUsersListActivity.class);
        intent.putExtra("ROLE", ApplicationClass.PROFESSOR_ROLE);
        startActivity(intent);
    }
    public void allAdmins(View view) {
        Intent intent = new Intent(getApplicationContext(), AllUsersListActivity.class);
        intent.putExtra("ROLE", ApplicationClass.ADMIN_ROLE);
        startActivity(intent);
    }
    public void allStudents(View view) {
        Intent intent = new Intent(getApplicationContext(), AllUsersListActivity.class);
        intent.putExtra("ROLE", ApplicationClass.STUDENT_ROLE);
        startActivity(intent);
    }
    public void allUsers(View view) {
        Intent intent = new Intent(getApplicationContext(), AllUsersListActivity.class);
        intent.putExtra("ROLE", "ALL");
        startActivity(intent);
    }

    public void addNewUserButton(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}
