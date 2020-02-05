package com.example.examservice;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.examservice.database.Exam;
import com.example.examservice.database.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ApplicationClass extends Application {



    static public FirebaseDatabase mDatabase ;
    static public FirebaseAuth mAuth;
    static public DatabaseReference usersRef ;
    //static public DatabaseReference examsRef ;
    public static final String SHARED_PREFERENCES_NAME ="examServicePreferences";
    public static final String SHARED_PREFERENCES_FIRST_NAME_KEY = ";name;";
    public static final String SHARED_PREFERENCES_LAST_NAME_KEY = ";surname;";
    public static final String SHARED_PREFERENCES_EMAIL_KEY = ";email;";
    public static final String SHARED_PREFERENCES_ROLE_KEY = ";role;";
    public static final String SHARED_PREFERENCES_ID_KEY = ";id;";
    public static final String PROFESSOR_ROLE = "ROLE_PROFESSOR";
    public static final String STUDENT_ROLE = "ROLE_STUDENT";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";
    public static final String TAG = "TAGApplicationClass" ;

    public static ArrayList<User> allUsersList ;
    public static ArrayList<User> allStudentsList ;
    public static ArrayList<User> allProfessorsList ;
    public static ArrayList<User> allAdminsList ;
    public static Set<String> allGroupsSet;

    public static int usersCount ;
    public static MyFTPClient ftpclient = null;

    @Override
    public void onCreate() {
        super.onCreate();

        ftpclient = new MyFTPClient() ;
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        connect();
        allUsersList = new ArrayList<>();
        allAdminsList = new ArrayList<>();
        allProfessorsList = new ArrayList<>();
        allStudentsList = new ArrayList<>();
        allGroupsSet = new HashSet<>();

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        usersRef = mDatabase.getReference().child("User");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allAdminsList.clear();
                allProfessorsList.clear();
                allStudentsList.clear();
                allUsersList.clear();
                String role ;
                if(dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User currentUser = userSnapshot.getValue(User.class);
                        if(currentUser != null) {
                            usersCount = currentUser.getId();
                            role = currentUser.getRole();
                            switch (role) {
                                case ADMIN_ROLE:
                                    allAdminsList.add(currentUser);
                                    allUsersList.add(currentUser);
                                    break;
                                case STUDENT_ROLE:
                                    allStudentsList.add(currentUser);
                                    allUsersList.add(currentUser);
                                    allGroupsSet.add(currentUser.getGroup_of_students());
                                    break;
                                case PROFESSOR_ROLE:
                                    allProfessorsList.add(currentUser);
                                    allUsersList.add(currentUser);
                                    break;
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

    }

    private void connect() {
        new Thread(new Runnable() {
            public void run() {
                boolean status = false;
                status = ftpclient.connect();
                if (status) {
                    Log.d(TAG, "Connection Success");
                } else {
                    Log.d(TAG, "Connection failed");
                }
            }
        }).start();
    }

}
