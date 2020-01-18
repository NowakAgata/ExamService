package com.example.examservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.examservice.admin.MainAdminActivity;
import com.example.examservice.database.User;
import com.example.examservice.professor.MainProfessorActivity;
import com.example.examservice.student.MainStudentActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAGMainActivity";
    public static boolean isLogged ;
    private FirebaseAuth mAuth ;
    public String role, name, email;
    SharedPreferences preferences;
    ArrayList<User> userArrayList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userArrayList = ApplicationClass.allUsersList;
        preferences = getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

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
                startActivity(intent);
            }else if(role.equalsIgnoreCase(ApplicationClass.STUDENT_ROLE)){
                Intent intent = new Intent(this, MainStudentActivity.class);
                startActivity(intent);
            } else if(role.equalsIgnoreCase(ApplicationClass.ADMIN_ROLE)){
                Intent intent = new Intent(this, MainAdminActivity.class);
                startActivity(intent);
           }

        }

    }


}
