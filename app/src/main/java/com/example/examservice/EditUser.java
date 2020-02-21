package com.example.examservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examservice.database.Date;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class EditUser extends AppCompatActivity {

    private final String TAG = "TAGEditUser" ;
    EditText etName, etSurname, etFristPass, etSecondPass;
    String firstName, lastName, newFirstName, newLastName, fisrtPass, secondPass;
    SharedPreferences prefs ;
    DatabaseReference userRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_edit_user);

        prefs = getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        userRef = ApplicationClass.mDatabase.getReference().child("User");
        etName = findViewById(R.id.userNameEditTxt);
        etSurname = findViewById(R.id.userSurnameEditTxt);
        etFristPass = findViewById(R.id.userPasswordFirstEditTxt);
        etSecondPass = findViewById(R.id.userPasswordSecondEditTxt);

        updateEditTexts();

    }

    private void updateEditTexts() {
        firstName = prefs.getString(ApplicationClass.SHARED_PREFERENCES_FIRST_NAME_KEY, "");
        lastName = prefs.getString(ApplicationClass.SHARED_PREFERENCES_LAST_NAME_KEY, "");

        etName.setText(firstName);
        etSurname.setText(lastName);
    }

    public void onOkClick(View view) {

        if(!validData()){
            return ;
        }
        newFirstName = etName.getText().toString();
        newLastName = etSurname.getText().toString();
        int id = prefs.getInt(ApplicationClass.SHARED_PREFERENCES_ID_KEY, 0);
        String userId = Integer.toString(id);
        boolean isChanged = false ;
        SharedPreferences.Editor editor = prefs.edit();

        if(!firstName.equals(newFirstName)){
            Log.d(TAG, "Editing user's name.");
            userRef.child(userId).child("first_name").setValue(newFirstName);
            editor.putString(ApplicationClass.SHARED_PREFERENCES_FIRST_NAME_KEY, newFirstName);
            isChanged = true;
        }if(!lastName.equals(newLastName)){
            Log.d(TAG, "Editing user's surname.");
            userRef.child(userId).child("last_name").setValue(newLastName);
            editor.putString(ApplicationClass.SHARED_PREFERENCES_LAST_NAME_KEY, newLastName);
            isChanged = true;
        }if(!fisrtPass.isEmpty()){
            Log.d(TAG, "editing user's password");
            Date date = new Date();
            userRef.child(userId).child("last_password_change").setValue(date);
            FirebaseUser firebaseUser = ApplicationClass.mAuth.getCurrentUser();
            firebaseUser.updatePassword(fisrtPass)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User password updated.");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "User password not updated.");
                            e.printStackTrace();
                        }
            });

        }
        if(isChanged){
            editor.apply();
            setResult(RESULT_OK);
            finish();
        }else{
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    private boolean validData() {

        fisrtPass = etFristPass.getText().toString();
        secondPass = etSecondPass.getText().toString();

        if((fisrtPass.isEmpty() && !secondPass.isEmpty()) || (!fisrtPass.isEmpty() && secondPass.isEmpty())){
            Toast.makeText(getApplicationContext(), "If you want to change password, please fill both fields.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!fisrtPass.equals(secondPass)){
            Toast.makeText(getApplicationContext(), "Please enter identical passwords.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true ;
    }

    public void confirmEdit(View view) {
    }
}
