package com.example.examservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UserView extends AppCompatActivity {


    private final int EDIT_USER_REQUEST_CODE = 1;
    TextView tvName, tvEmail, tvPassword ;
    String firstName, lastName,name,   email, password ;
    SharedPreferences prefs;
    FirebaseAuth mAuth ;
    //TODO zrobić wyświetlanie użytkownika i przejście do edyscji

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_user_view);

        mAuth = ApplicationClass.mAuth ;

        tvName = findViewById(R.id.userNameTxtView);
        tvEmail = findViewById(R.id.userEmailTxtView);
        //tvPassword = findViewById(R.id.userPasswordTxtView);

        prefs = getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        firstName = prefs.getString(ApplicationClass.SHARED_PREFERENCES_FIRST_NAME_KEY, "");
        lastName = prefs.getString(ApplicationClass.SHARED_PREFERENCES_LAST_NAME_KEY, "");
        email = prefs.getString(ApplicationClass.SHARED_PREFERENCES_EMAIL_KEY, "");

        //mAuth.getCurrentUser().get

    }

    public void onEditClick(View view) {

        Intent intent = new Intent(getApplicationContext(), EditUser.class);
        startActivityForResult(intent, EDIT_USER_REQUEST_CODE);

    }

    public void onOkClick(View view){
        setResult(RESULT_OK);
        finish();
    }

    public void SingOutButton(View view) {
    }
}
