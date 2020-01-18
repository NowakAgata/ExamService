package com.example.examservice.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.RegisterActivity;
import com.example.examservice.database.User;

import java.util.ArrayList;

public class AllUsersListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AllUsersAdapter usersAdapter ;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> allUsersList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_professors_list);

        Intent intent = getIntent();
        String role = intent.getStringExtra("ROLE");

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

        recyclerView = findViewById(R.id.allUsersListView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        usersAdapter = new AllUsersAdapter(this, allUsersList);
        recyclerView.setAdapter(usersAdapter);

    }

    public  void addNewUser(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}
