package com.example.examservice.professor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.admin.AllUsersAdapter;
import com.example.examservice.database.User;

import java.util.ArrayList;

public class AllStudentsList extends AppCompatActivity {

    RecyclerView recyclerView;
    AllStudentsAdapter usersAdapter ;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> allUsersList ;
    ArrayList<User> oneGroupList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_all_students_list);


        allUsersList = ApplicationClass.allUsersList ;
        oneGroupList = new ArrayList<>();
        String group = getIntent().getStringExtra("GROUP");
        if(group.equals("all")){
            oneGroupList = allUsersList ;
        }else {
            for (User temp : allUsersList) {
                if (temp.getGroup_of_students().equals(group)){
                    oneGroupList.add(temp);
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

}
