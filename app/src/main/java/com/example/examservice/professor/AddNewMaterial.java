package com.example.examservice.professor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.examservice.ApplicationClass;
import com.example.examservice.MyFTPClient;
import com.example.examservice.R;
import com.example.examservice.database.LearningMaterial;
import com.example.examservice.database.LearningMaterialsGroup;
import com.google.firebase.database.DatabaseReference;

import java.io.File;

public class AddNewMaterial extends AppCompatActivity {

    private static final String TAG = "TAGNewMaterial" ;
    EditText etName ;
    String name;
    private MyFTPClient ftpclient = null;
    boolean status ;
    int materialsCounter ;
    Spinner isLearningSpinner ;
    boolean isLearning, newGroup ;
    LearningMaterialsGroup group ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_add_new_material);

        ftpclient = ApplicationClass.ftpclient;
        newGroup = getIntent().getBooleanExtra("NEW_GROUP", false) ;

        materialsCounter = AllMaterialsList.materialsCounter ;
        group = AllMaterialsGroup.chosenGroup;

        isLearningSpinner = findViewById(R.id.materialRequiredSpinner);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.boolArray, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isLearningSpinner.setAdapter(spinnerAdapter);

        isLearningSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isLearning = (position == 0) ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etName = findViewById(R.id.newMaterialNameEditText);
    }



    public void addDocument(View view) {

        name= etName.getText().toString();
        if(name.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter file name first!", Toast.LENGTH_SHORT).show();
            return ;
        }
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");

        startActivityForResult(intent, 2000);
    }

    public void addPhoto(View view) {
        name= etName.getText().toString();
        if(name.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter file name first!", Toast.LENGTH_SHORT).show();
            return ;
        }
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == RESULT_OK){

                Uri selectedImage = data.getData();
                if(selectedImage != null) {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    Log.d(TAG, "path: " + imgDecodableString) ;
                    String fileName = ftpclient.ftpUpload(imgDecodableString);
                    addMaterial(fileName);
                }

            }
        }else if(requestCode == 2000){
            if(resultCode == RESULT_OK){
                //TODO dodawanie doca nie działa plus usunąć przycisk list files
                Uri selectedDoc = data.getData();
                if(selectedDoc != null) {
                    File file = new File(selectedDoc.getPath());
                    String path = file.getAbsolutePath();
                    Log.d(TAG, "path: " + path);
                    ftpclient.ftpUpload(path);
                }
            }
        }
    }

    private void addMaterial(String fileName) {
        if(newGroup){
            if(fileName != null){
                LearningMaterial material = new LearningMaterial(0, isLearning, 0, name, fileName);
                NewLearningMaterialsGroup.materialsList.add(material);
                setResult(RESULT_OK);
                finish();
            }else{
                Log.d(TAG, "Something went wrong");
                setResult(RESULT_CANCELED);
                finish();
            }
        }else{
            materialsCounter ++ ;
            String materialId = Integer.toString(materialsCounter) ;
            int groupIntId = group.getLearning_materials_group_id();
            String groupId = Integer.toString(groupIntId);
            LearningMaterial material = new LearningMaterial(materialsCounter, isLearning, groupIntId, name, fileName);
            DatabaseReference materialRef = ApplicationClass.mDatabase.getReference()
                    .child("LearningMaterialsGroup").child(groupId).child("LearningMaterial").child(materialId);
            materialRef.setValue(material);
            setResult(RESULT_OK);
            finish();
        }
    }


    public void listFiles(View view) {
        ftpclient.printFilesList();
    }
}
