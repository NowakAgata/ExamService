package com.example.examservice.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.UserView;

public class MainAdminActivity extends AppCompatActivity {

    private static final int EDIT_PROFILE_REQUEST_CODE =1;
    private static final int PERMISSIONS_REQUEST_READ_FILES = 2;
    private static final String TAG = "TAGAdminMain";
    String username ;
    TextView helloAdmin ;
    SharedPreferences preferences ;
    String pass, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        preferences = getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        username = preferences.getString(ApplicationClass.SHARED_PREFERENCES_FIRST_NAME_KEY, "admin");
        helloAdmin = findViewById(R.id.helloAdminTxtView);
        username = "Hello " + username ;
        helloAdmin.setText(username);

        checkPermission();

    }

    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_READ_FILES);


        } else {
            Log.d(TAG,"Permission has already been granted");
        }

    }

    public void usersMode(View view) {
        Intent intent = new Intent(getApplicationContext(), UserAdministration.class);
        startActivity(intent);
    }

    public void examsMode(View view) {
        Intent intent = new Intent(getApplicationContext(), ExamAdministration.class);
        startActivity(intent);
    }

    public void filesMode(View view) {
        Intent intent = new Intent(getApplicationContext(), FileAdministration.class);
        startActivity(intent);
    }

    public void editProfile(View view) {
        Intent intent = new Intent(getApplicationContext(), UserView.class);
        intent.putExtra("ADMIN_PASS", pass);
        intent.putExtra("ADMIN_EMAIL", mail);
        startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_PROFILE_REQUEST_CODE){
            if(resultCode == 1000){
                //signed out
                setResult(1000);
                finish();
            }
        }
    }
}
