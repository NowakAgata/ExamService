package com.example.examservice;

import androidx.annotation.Nullable;
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
    //TODO zrobić wyświetlanie użytkownika i przejście do edycji

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_user_view);

        mAuth = ApplicationClass.mAuth ;

        tvName = findViewById(R.id.userNameTxtView);
        tvEmail = findViewById(R.id.userEmailTxtView);

        prefs = getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        updateTextViews();

    }

    private void updateTextViews() {

        firstName = prefs.getString(ApplicationClass.SHARED_PREFERENCES_FIRST_NAME_KEY, "");
        lastName = prefs.getString(ApplicationClass.SHARED_PREFERENCES_LAST_NAME_KEY, "");
        email = prefs.getString(ApplicationClass.SHARED_PREFERENCES_EMAIL_KEY, "");
        name = firstName + " " + lastName ;
        tvName.setText(name);
        tvEmail.setText(email);

    }

    public void onEditClick(View view) {

        Intent intent = new Intent(getApplicationContext(), EditUser.class);
        startActivityForResult(intent, EDIT_USER_REQUEST_CODE);

    }


    public void signOutButton(View view) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationClass.SHARED_PREFERENCES_FIRST_NAME_KEY, "");
        editor.putString(ApplicationClass.SHARED_PREFERENCES_LAST_NAME_KEY, "");
        editor.putString(ApplicationClass.SHARED_PREFERENCES_EMAIL_KEY, "");
        editor.putString(ApplicationClass.SHARED_PREFERENCES_ROLE_KEY, "");
        editor.putInt(ApplicationClass.SHARED_PREFERENCES_ID_KEY, -1);
        editor.apply();

        mAuth.signOut();
        setResult(1000);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_USER_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                updateTextViews();
            }
        }
    }
}
