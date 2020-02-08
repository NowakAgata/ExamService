package com.example.examservice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.examservice.ApplicationClass;
import com.example.examservice.LoginActivity;
import com.example.examservice.R;
import com.example.examservice.admin.MainAdminActivity;
import com.example.examservice.database.User;
import com.example.examservice.professor.MainProfessorActivity;
import com.example.examservice.student.MainStudentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static int TIME = 5000 ;
    private static final int NEW_ACTIVITY_REQUEST_CODE =1;
    public static final String TAG = "TAGMainActivity";
    public static boolean isLogged ;
    private FirebaseAuth mAuth ;
    public String role, name, email;
    SharedPreferences preferences;
    ArrayList<User> userArrayList ;
    ImageView logoView ;
    Animation slide, bounce ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userArrayList = ApplicationClass.allUsersList;
        preferences = getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

        logoView = findViewById(R.id.logoImgView);

        bounce = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        slide = AnimationUtils.loadAnimation(this, R.anim.slide_animation);

        checkIfLogged();
    }

    private void checkIfLogged() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d(TAG, "current user is: " + currentUser);
        if(currentUser != null) {
            isLogged = true ;
            email = currentUser.getEmail();
        }
        else isLogged = false;

        if(!isLogged){
            Log.d(TAG , "User is not logged, starting login activity" );
            Intent intent = new Intent(this, LoginActivity.class);

            startActivity(intent);

        } else{
            role = preferences.getString(ApplicationClass.SHARED_PREFERENCES_ROLE_KEY, ApplicationClass.STUDENT_ROLE);
            if(role.equalsIgnoreCase(ApplicationClass.PROFESSOR_ROLE)){
                Intent intent = new Intent(this, MainProfessorActivity.class);
                startActivityForResult(intent, NEW_ACTIVITY_REQUEST_CODE);
            }else if(role.equalsIgnoreCase(ApplicationClass.STUDENT_ROLE)){
                Intent intent = new Intent(this, MainStudentActivity.class);
                startActivityForResult(intent, NEW_ACTIVITY_REQUEST_CODE);
            } else if(role.equalsIgnoreCase(ApplicationClass.ADMIN_ROLE)){
                Intent intent = new Intent(this, MainAdminActivity.class);
                startActivityForResult(intent, NEW_ACTIVITY_REQUEST_CODE);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_ACTIVITY_REQUEST_CODE){
            if(resultCode == 1000){
                checkIfLogged();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
