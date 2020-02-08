package com.example.examservice.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.examservice.R;
import com.example.examservice.database.Date;
import com.example.examservice.database.Exam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class AddNewExam extends AppCompatActivity {

    public static final String TAG = "TAGAddNewExam" ;
    EditText etName, etInfo, etFrom, etTo, etDuration, etAttempts, etQuestions, etPercentage ;
    Spinner isLearningSpinner ;
    String name, info, from, to, duration, attempts, questions, percent;
    int durationInt, attemptsInt, questionsInt, percentageInt ;
    boolean isLearning ;
    public static DateFormat format ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_new_exam);

        etName = findViewById(R.id.etAdminExamName);
        etInfo = findViewById(R.id.etAdminExamInfo);
        etFrom = findViewById(R.id.etAdminExamFrom);
        etTo = findViewById(R.id.etAdminExamTo);
        etDuration = findViewById(R.id.etAdminExamDuration);
        etAttempts = findViewById(R.id.etAdminExamMaxAtt);
        etQuestions = findViewById(R.id.etAdminExamMaxQuest);
        etPercentage = findViewById(R.id.etAdminExamPercentage);
        isLearningSpinner = findViewById(R.id.spinAdminExamLearning);

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

        format = Date.dateFormat ;
        format.setLenient(false);

    }

    public void addNewExam(View view) {
        if(!dataValidation()){
            return ;
        }

        int id = ++ ExamAdministration.examsCount ;
        String idString = Integer.toString(id) ;
        Log.d(TAG, "Current incremented id is:" + idString) ;
        attemptsInt = Integer.parseInt(attempts);
        durationInt = Integer.parseInt(duration);
        questionsInt = Integer.parseInt(questions);
        percentageInt = Integer.parseInt(percent);
        String timezone = new GregorianCalendar().getTimeZone().getID();
        Date start = new Date(from, timezone);
        Date end = new Date(to, timezone);
        Exam exam = new Exam(id, name, info, isLearning, -1, durationInt, attemptsInt,
                questionsInt,percentageInt, start, end) ;

        Log.d(TAG, "Adding new exam: " + name + " to database");

        ExamAdministration.examsRef.child(idString).setValue(exam);
        setResult(RESULT_OK);
        finish();

    }
    private boolean dataValidation(){

        name = etName.getText().toString();
        info = etInfo.getText().toString();
        from = etFrom.getText().toString();
        to = etTo.getText().toString();
        duration = etDuration.getText().toString();
        attempts = etAttempts.getText().toString();
        questions = etQuestions.getText().toString();
        percent = etPercentage.getText().toString();

        if( name.isEmpty() || info.isEmpty() || from.isEmpty() || to.isEmpty() ||duration.isEmpty()
                || attempts.isEmpty() || questions.isEmpty() || percent.isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.allFields, Toast.LENGTH_SHORT).show();
            return false;
        } else if(!dateValidation(from)){
            Toast.makeText(getApplicationContext(), "'From' date is not in format dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!dateValidation(to)){
            Toast.makeText(getApplicationContext(), "'To' date is not in format dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            return  false;
        } else if(!numberValidation(duration)){
            Toast.makeText(getApplicationContext(), "Duration time is not a valid, positive number", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!numberValidation(percent)){
            Toast.makeText(getApplicationContext(), "Percent to pass is not a valid, positive number", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!numberValidation(attempts)){
            Toast.makeText(getApplicationContext(), "Max. number of attempts is not a valid, positive number", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!numberValidation(questions)){
            Toast.makeText(getApplicationContext(), "Max. number of questions is not a valid, positive number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean dateValidation(String date){

        try {
            date = date + " 00:00:00.000000";
            format.parse(date);
            return true ;
        } catch (ParseException e) {
            Log.d(TAG, "Date " + date + " is not valid according to " +
                    ((SimpleDateFormat) format).toPattern() + " pattern.");
            return false;
        }
    }

    private boolean numberValidation(String text){
        try {
            if(text != null) {
                int number = Integer.valueOf(text);
                if (number > 0)
                    return true;
            }
        } catch(Exception e){
            Log.d(TAG, "Text " + text +" is not a valid number");
            return false;
        }
        return false;
    }

}
