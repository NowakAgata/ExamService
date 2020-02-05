package com.example.examservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examservice.admin.MainAdminActivity;
import com.example.examservice.database.User;
import com.example.examservice.professor.MainProfessorActivity;
import com.example.examservice.student.MainStudentActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {

    private static final int ROLE_ACTIVITY_REQUEST_CODE = 1;
    EditText etEmail, etPassword ;
    String email, name, surname, password, role;
    int id ;
    String TAG = "TAGLoginActivity:" ;
    SharedPreferences preferences;
    private FirebaseAuth mAuth ;
    ArrayList<User> userArrayList ;
    DatabaseReference usersRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        etEmail= findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);

        userArrayList = ApplicationClass.allUsersList;

        mAuth = ApplicationClass.mAuth;
        usersRef = ApplicationClass.mDatabase.getReference().child("User");

    }

    public void login(View view) {

        if(!loginValidation())
            return ;

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            updateCurrentUser();
                            startRoleActivity();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void startRoleActivity() {
        if(role.equals(ApplicationClass.PROFESSOR_ROLE)){
            Intent intent = new Intent(getApplicationContext(), MainProfessorActivity.class);
            startActivityForResult(intent, ROLE_ACTIVITY_REQUEST_CODE);
        }else if(role.equals(ApplicationClass.STUDENT_ROLE)){
            Intent intent = new Intent(getApplicationContext(), MainStudentActivity.class);
            startActivityForResult(intent, ROLE_ACTIVITY_REQUEST_CODE);

        }else if(role.equals(ApplicationClass.ADMIN_ROLE)){
            Intent intent = new Intent(getApplicationContext(), MainAdminActivity.class);
            startActivityForResult(intent, ROLE_ACTIVITY_REQUEST_CODE);

        }
    }

    private boolean loginValidation() {

        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        if( email.isEmpty() || password.isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.allFields, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void  updateCurrentUser() {


        User user = null;
        for(User temp : userArrayList){
            Log.d(TAG, temp.toString());
            if(temp.getEmail().equalsIgnoreCase(email))
                user = temp ;
        }
        if(user != null) {

            Log.d(TAG, "Current user from database: ");
            Log.d(TAG, user.toString());

            name = user.getFirst_name();
            surname = user.getLast_name();
            role = user.getRole();
            id = user.getId();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(ApplicationClass.SHARED_PREFERENCES_FIRST_NAME_KEY, name);
            editor.putString(ApplicationClass.SHARED_PREFERENCES_LAST_NAME_KEY, surname);
            editor.putString(ApplicationClass.SHARED_PREFERENCES_EMAIL_KEY, email);
            editor.putString(ApplicationClass.SHARED_PREFERENCES_ROLE_KEY, role);
            editor.putInt(ApplicationClass.SHARED_PREFERENCES_ID_KEY, id);
            editor.apply();
        }
        else{
            Log.d(TAG, "User in null!!!!!");
            Toast.makeText(getApplicationContext(), R.string.somethingWrong, Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == ROLE_ACTIVITY_REQUEST_CODE){
//            if(resultCode == 1000){
//
//            }
//        }
//    }
}
