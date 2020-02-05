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
import com.example.examservice.RegisterActivity;
import com.example.examservice.UserView;

public class MainAdminActivity extends AppCompatActivity {

    private static final int EDIT_PROFILE_REQUEST_CODE =1;
    private static final int PERMISSIONS_REQUEST_READ_FILES = 2;
    private static final String TAG = "TAGAdminMain";
    String username ;
    TextView helloAdmin ;
    SharedPreferences preferences ;

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

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_FILES);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Log.d(TAG,"Permission has already been granted");
            // Permission has already been granted
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_FILES: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
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
