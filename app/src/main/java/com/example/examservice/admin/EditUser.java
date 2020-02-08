package com.example.examservice.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.User;
import com.google.firebase.database.DatabaseReference;

public class EditUser extends AppCompatActivity {

    private final String TAG = "TAGEditUser" ;
    User user ;
    EditText etName, etSurname, etGroup ;
    String name, surname, group, role ;
    boolean isStudent ;
    DatabaseReference userRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_user);

        user = AllUsersListActivity.chosenUser;
        userRef = ApplicationClass.usersRef ;

        etName = findViewById(R.id.adminEditFirstName);
        etSurname = findViewById(R.id.adminEditLastName);
        etGroup = findViewById(R.id.adminEditGroup);

        updateViews();

    }

    private void updateViews() {
        name = user.getFirst_name();
        surname = user.getLast_name();

        etName.setText(name);
        etSurname.setText(surname);
        role = user.getRole();
        if(role.equals(ApplicationClass.STUDENT_ROLE)){
            group = user.getGroup_of_students();
            etGroup.setText(group);
            isStudent = true ;
        }else{
            etGroup.setInputType(InputType.TYPE_NULL);
            isStudent = false;
        }
    }

    public void confirmEdit(View view) {
        String newName, newSurname, newGroup, userId;
        newName = etName.getText().toString();
        newSurname = etSurname.getText().toString();
        newGroup = etGroup.getText().toString();
        userId = Integer.toString(user.getId());
        boolean isChanged = false;

        if(!name.equals(newName)){
            Log.d(TAG, "Editing user's name.");
            userRef.child(userId).child("first_name").setValue(newName);
            user.setFirst_name(newName);
            isChanged = true;
        }if(!surname.equals(newSurname)){
            Log.d(TAG, "Editing user's surname.");
            userRef.child(userId).child("last_name").setValue(newSurname);
            user.setLast_name(newSurname);
            isChanged = true;
        }if((isStudent) && (!group.equals(newGroup))){
            Log.d(TAG, "Editing user's group.");
            userRef.child(userId).child("group_of_students").setValue(newGroup);
            user.setLast_name(newGroup);
            isChanged = true;
        }

        if(isChanged){
            AllUsersListActivity.chosenUser = user ;
            setResult(RESULT_OK);
            finish();
        }else{
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
