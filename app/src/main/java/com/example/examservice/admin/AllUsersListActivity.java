package com.example.examservice.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.User;
import com.example.examservice.database.UserExam;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AllUsersListActivity extends AppCompatActivity {

    private static final int ADD_NEW_USER_REQUEST_CODE = 1;
    private String TAG = "TAGAllUsers" ;
    RecyclerView recyclerView;
    AllUsersAdapter usersAdapter ;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> allUsersList ;
    DatabaseReference usersRef ;
    String role ;
    public static User chosenUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_professors_list);

        Intent intent = getIntent();
        role = intent.getStringExtra("ROLE");

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
        startActivityForResult(intent, ADD_NEW_USER_REQUEST_CODE);
    }

    public void onUserClick(View view) {
        int i = (int) view.getTag();
        chosenUser = allUsersList.get(i);

        Intent intent = new Intent(getApplicationContext(), UserView.class);
        startActivity(intent);

    }

    public void onUserDeleteClick(View view) {
        int index = (int) view.getTag();
        User user = allUsersList.get(index);
        int userId = user.getId();
        String userIdString = Integer.toString(userId);

        boolean canDelete = true;
        ArrayList<UserExam> userExamList = UserAdministration.userExamList;
        for(UserExam userExam : userExamList){
            if(userExam.getUser_id() == userId){
                canDelete = false;
            }
        }
        if(canDelete) {
            DatabaseReference tempRef = ApplicationClass.mDatabase.getReference().child("User");
            tempRef.child(userIdString).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) { getList();
                        usersAdapter.notifyDataSetChanged(); }
                });
        }else{
            Toast.makeText(getApplicationContext(),
                    R.string.cant_delete, Toast.LENGTH_SHORT).show();
            return;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NEW_USER_REQUEST_CODE){
            getList();
        }
    }

}
