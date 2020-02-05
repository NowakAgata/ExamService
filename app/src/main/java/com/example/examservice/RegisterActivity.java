package com.example.examservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.examservice.database.Date;
import com.example.examservice.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etSurname, etPassword, etEmail, etGroup;
    Spinner spinner ;
    String name, surname, password, email, role, group;
    public static final String TAG = "TAGRegisterActivity: " ;
    private FirebaseAuth mAuth ;
    private DatabaseReference usersRef ;
    SharedPreferences preferences ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etRegisterFirstName);
        etSurname = findViewById(R.id.etRegisterLastName);
        etPassword = findViewById(R.id.etRegisterPassword);
        etEmail = findViewById(R.id.etRegisterEmail);
        etGroup = findViewById(R.id.etRegisterGroup);
        spinner = findViewById(R.id.spinRegisterRole);

        group = "0";

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.rolesArray, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String temp = parent.getItemAtPosition(position).toString();
                if(temp.equalsIgnoreCase("student")) {
                    role = ApplicationClass.STUDENT_ROLE ;
                    etGroup.setInputType(InputType.TYPE_CLASS_TEXT);
                } else if(temp.equalsIgnoreCase("professor")){
                    role = ApplicationClass.PROFESSOR_ROLE;
                    etGroup.setInputType(InputType.TYPE_NULL);
                } else if(temp.equalsIgnoreCase("admin")){
                    role = ApplicationClass.ADMIN_ROLE;
                    etGroup.setInputType(InputType.TYPE_NULL);
                } else{
                    Toast.makeText(getApplicationContext(), "Please choose your role.", Toast.LENGTH_SHORT).show();
                    etGroup.setInputType(InputType.TYPE_NULL);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                role = null ;
            }
        });
        mAuth = ApplicationClass.mAuth;
        usersRef = ApplicationClass.mDatabase.getReference("User");
    }





    public void addNewUser(View view){
        if(!validateForm()){
            return ;
        }

        Log.d(TAG, "creating user: " + email);


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           Log.d(TAG, "createUserWithEmail:success");
                           addNewUserToDatabase();
                           updateCurrentUser();
                           finish();
                       } else {
                           Log.w(TAG, "createUserWithEmail:failure", task.getException());
                           Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                            }
                        });



    }

    private void addNewUserToDatabase() {

        int counter = ApplicationClass.usersCount + 1 ;
        Date date = new Date();
        if(!role.equals(ApplicationClass.STUDENT_ROLE)) group = "-1" ;
        User user1 = new User(counter, name, surname, role, email, date, date, date, group);
        Log.d(TAG, "Adding user to database:");
        Log.d(TAG, user1.toString());
        String id = Integer.toString(counter);
        usersRef.child(id).setValue(user1);
    }

    private void updateCurrentUser() {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success, user: " + email);

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        preferences = getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationClass.SHARED_PREFERENCES_EMAIL_KEY, email);
        editor.putString(ApplicationClass.SHARED_PREFERENCES_FIRST_NAME_KEY, name);
        editor.putString(ApplicationClass.SHARED_PREFERENCES_LAST_NAME_KEY, surname);

        editor.putString(ApplicationClass.SHARED_PREFERENCES_ROLE_KEY, role);
        editor.apply();

        Log.d(TAG, "Changing user in shared preferences: ");
        Log.d(TAG, "username: " + name + ", role: " + role);
    }

    private boolean validateForm(){

        name = etName.getText().toString();
        surname = etSurname.getText().toString();
        password = etPassword.getText().toString();
        email = etEmail.getText().toString();
        group = etGroup.getText().toString();

        if( name.isEmpty() || surname.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.allFields, Toast.LENGTH_SHORT).show();
            return false;
        }else if(role == null){
            Toast.makeText(getApplicationContext(), "Please choose your role!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isEmailValid(email)){
            Toast.makeText(getApplicationContext(), "Are you sure that your email address is correct?", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isPasswordValid(password)){
            Toast.makeText(getApplicationContext(), "Password should be at least 6 characters long.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(role.equals(ApplicationClass.STUDENT_ROLE) && group.isEmpty()){
            Toast.makeText(getApplicationContext(), "If you are adding new student, you have to enter his group name.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true ;
    }

    static boolean isEmailValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    static boolean isPasswordValid(String password){
        int length = password.length();
        return (length >=6) ;
    }


}
