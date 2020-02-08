package com.example.examservice.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.LearningMaterial;
import com.google.firebase.database.DatabaseReference;

public class EditMaterial extends AppCompatActivity {

    private static final String TAG = "TAGEditMaterial";
    EditText etName;
    Spinner spinner ;
    boolean isRequiredNew, isRequired ;
    String name, nameNew;
    LearningMaterial material ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acdmin_edit_material);

        etName = findViewById(R.id.editMaterialNameEditText);
        spinner = findViewById(R.id.editMaterialSpinner);

        material = AllMaterialsList.chosenMaterial;

        etName.setText(material.getName());        

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.boolArray, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isRequiredNew = (position == 0) ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                isRequiredNew =  material.isIs_required();
            }
        });
    }

    public void onOkClick(View view) {
        int group = material.getLearning_materials_group_id();
        String groupId = Integer.toString(group);
        String id = Integer.toString(material.getId());
        name = material.getName();
        isRequired = material.isIs_required();

        nameNew = etName.getText().toString();
        boolean changed = false;
        DatabaseReference ref = ApplicationClass.mDatabase.getReference().child("LearningMaterialsGroup")
                .child(groupId).child("LearningMaterial").child(id) ;
        if(isRequiredNew != isRequired){
            changed = true;
            ref.child("name").setValue(nameNew);
            Log.d(TAG, "Changed name");
            material.setName(nameNew);
        }if(!name.equals(nameNew)){
            changed = true;
            ref.child("is_required").setValue(isRequiredNew);
            Log.d(TAG, "Changed is_required");
            material.setIs_required(isRequiredNew);
        }

        if(changed){
            setResult(RESULT_OK);
            finish();
        }else{
            setResult(RESULT_CANCELED);
            finish();
        }

    }
}
