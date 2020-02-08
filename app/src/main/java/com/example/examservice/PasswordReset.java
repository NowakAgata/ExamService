package com.example.examservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {

    private static final String TAG = "TAGResetEmail";
    EditText etEmail ;
    String email ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_password_reset);

        etEmail = findViewById(R.id.resetPasswordEmailEditText);
    }

    public void reset(View view) {

        email = etEmail.getText().toString();

        if(email.isEmpty() || !isEmailValid(email)){
            Toast.makeText(getApplicationContext(), R.string.enterValidEmail, Toast.LENGTH_SHORT).show();
            return ;
        }
        FirebaseAuth mAuth = ApplicationClass.mAuth;
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "Reset email sent correctly") ;
                setResult(RESULT_OK);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Reset email not sent") ;
                setResult(RESULT_CANCELED);
                e.printStackTrace();
                finish();
            }
        });
    }

    static boolean isEmailValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
