package com.example.examservice.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.Date;
import com.example.examservice.database.User;

public class UserView extends AppCompatActivity {

    private static final int EDIT_USER_REQUEST_CODE = 1;
    TextView tvName, tvMail, tvRole, tvLastLogin, tvPass, tvReg ;
    User user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_view);

        tvName = findViewById(R.id.adminUserNameTxtView);
        tvMail = findViewById(R.id.adminUserEmailTxtView);
        tvRole = findViewById(R.id.adminUserRoleTxtView);
        tvLastLogin = findViewById(R.id.adminUserLastLoggedTxtView);
        tvPass = findViewById(R.id.adminUserLastChangeTxtView);
        tvReg = findViewById(R.id.adminUserRegistrationTxtView);

        user = AllUsersListActivity.chosenUser;

        updateTextViews();
    }

    private void updateTextViews() {
        String name = user.getFirst_name() + " " + user.getLast_name() ;
        String mail = user.getEmail();
        String role = user.getRole();
        String lastLog = user.getLast_login().getDate().substring(0,10);
        String lastPass = user.getLast_password_change().getDate().substring(0,10);
        String reg = user.getDate_registration().getDate().substring(0,10);

        switch(role){
            case ApplicationClass.ADMIN_ROLE:
                role = "admin" ;
                break;
            case ApplicationClass.PROFESSOR_ROLE:
                role = "professor" ;
                break;
            case ApplicationClass.STUDENT_ROLE:
                role = "student" ;
                break;
        }

        tvName.setText(name);
        tvMail.setText(mail);
        tvRole.setText(role);
        tvLastLogin.setText(lastLog);
        tvPass.setText(lastPass);
        tvReg.setText(reg);

    }


    public void delete(View view) {
    }

    public void editButton(View view) {
        Intent intent = new Intent(getApplicationContext(), EditUser.class);
        startActivityForResult(intent, EDIT_USER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_USER_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                user = AllUsersListActivity.chosenUser;
                updateTextViews();
            }
        }
    }
}
