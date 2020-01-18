package com.example.examservice.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.RegisterActivity;

public class MainAdminActivity extends AppCompatActivity {

    String username ;
    TextView helloAdmin ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        helloAdmin = findViewById(R.id.helloAdminTxtView);

        helloAdmin.setText("Hello " + username);

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
