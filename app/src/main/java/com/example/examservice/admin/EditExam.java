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

import com.example.examservice.ApplicationClass;
import com.example.examservice.R;
import com.example.examservice.database.Date;
import com.example.examservice.database.Exam;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class EditExam extends AppCompatActivity {

    EditText etName, etInfo, etFrom, etTo, etDuration, etAttempts, etQuestions ;
    Spinner spinnerLearning ;
    String name, info, availableFromStr, availableToStr, durationStr, attemptsStr, questionsStr;
    String newName, newInfo, newAvailableFromStr, newAvailableToStr, newDurationStr, newAttemptsStr, newQuestionsStr;
    Date from, to ;
    boolean isLearning, newIsLearning ;
    int duration, attempts, questions;
    Exam exam ;
    public static DateFormat format ;
    private static final String TAG = "TAGEditExam";
    DatabaseReference examRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_exam);

        etName = findViewById(R.id.adminExamNameEdit);
        etInfo = findViewById(R.id.adminExamInfoEdit);
        etFrom = findViewById(R.id.adminExamAvailableFromEdit);
        etTo = findViewById(R.id.adminExamAvailableToEdit);
        spinnerLearning = findViewById(R.id.adminExamIsLearningReqEditEspinner);
        etDuration = findViewById(R.id.adminExamDurationTimeEdit);
        etAttempts = findViewById(R.id.adminExamMaxAttemptsEdit);
        etQuestions = findViewById(R.id.adminExamMaxQuestionsEdit);

        exam = AllExamsList.currentExam;

        updateTextViews();

        format = Date.dateFormat ;
        format.setLenient(false);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.boolArray, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLearning.setAdapter(spinnerAdapter);

        spinnerLearning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newIsLearning = (position == 0) ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        examRef = ApplicationClass.mDatabase.getReference().child("Exam") ;
    }

    private void updateTextViews() {

        name = exam.getName();
        info = exam.getAdditional_information();
        from = exam.getStart_date();
        to = exam.getEnd_date();
        duration = exam.getDuration_of_exam();
        attempts = exam.getMax_attempts();
        questions = exam.getMax_questions();

        availableFromStr = from.getDate().toString().substring(0,10);
        availableToStr = to.getDate().toString().substring(0,10);

        durationStr = Integer.toString(duration);
        attemptsStr = Integer.toString(attempts);
        questionsStr = Integer.toString(questions);

        etName.setText(name);
        etInfo.setText(info);
        etFrom.setText(availableFromStr);
        etTo.setText(availableToStr);
        etDuration.setText(durationStr);
        etAttempts.setText(attemptsStr);
        etQuestions.setText(questionsStr);

    }

    public void ok(View view) {

        boolean isChanged = false ;

        if(!dataValidation()){
            return ;
        }

        int examId = exam.getExam_id();
        String examIdString = Integer.toString(examId) ;
        String timezone = new GregorianCalendar().getTimeZone().getID();
        int newDuration, newAttempts, newQuestions;
        newDuration = Integer.parseInt(newDurationStr);
        newAttempts = Integer.parseInt(newAttemptsStr);
        newQuestions = Integer.parseInt(newQuestionsStr);


        if(!name.equalsIgnoreCase(newName)){
            Log.d(TAG, "Editing exam's name.");
            examRef.child(examIdString).child("name").setValue(newName);
            exam.setName(newName);
            isChanged = true;
        }
        if(!info.equalsIgnoreCase(newInfo)){
            Log.d(TAG, "Editing exam's info.");
            examRef.child(examIdString).child("additional_information").setValue(newInfo);
            exam.setAdditional_information(newInfo);
            isChanged = true;

        }
        if(!availableFromStr.equalsIgnoreCase(newAvailableFromStr)){
            Log.d(TAG, "Editing exam's from date.");
            Date dateFrom = new Date(newAvailableFromStr, timezone);
            examRef.child(examIdString).child("start_date").setValue(dateFrom);
            exam.setStart_date(dateFrom);
            isChanged = true;

        }
        if(!availableToStr.equalsIgnoreCase(newAvailableToStr)){
            Log.d(TAG, "Editing exam's to date.");
            Date dateTo = new Date(newAvailableToStr, timezone);
            examRef.child(examIdString).child("end_date").setValue(dateTo);
            exam.setEnd_date(dateTo);
            isChanged = true;

        }

        if(isLearning != newIsLearning){
            Log.d(TAG, "Editing exam's info about required materials.");
            examRef.child(examIdString).child("learning_required").setValue(newIsLearning);
            exam.setLearning_required(newIsLearning);
            isChanged = true;

        }

        if(newDuration != duration){
            Log.d(TAG, "Editing exam's duration time.");
            examRef.child(examIdString).child("duration_of_exam").setValue(newDuration);
            exam.setDuration_of_exam(newDuration);
            isChanged = true;

        }
        if(newAttempts != attempts){
            Log.d(TAG, "Editing exam's max. attempts number.");
            examRef.child(examIdString).child("max_attempts").setValue(newAttempts);
            exam.setMax_attempts(newAttempts);
            isChanged = true;

        }
        if(newQuestions != questions){
            Log.d(TAG, "Editing exam's max questions number.");
            examRef.child(examIdString).child("max_questions").setValue(newQuestions);
            exam.setMax_questions(newQuestions);
            isChanged = true;

        }
        if(isChanged){
            AllExamsList.currentExam = exam ;
            setResult(RESULT_OK);
            finish();
        }else{
            setResult(RESULT_CANCELED);
            finish();
        }

    }

    private boolean dataValidation(){

        newName = etName.getText().toString();
        newInfo = etInfo.getText().toString();
        newAvailableFromStr = etFrom.getText().toString();
        newAvailableToStr = etTo.getText().toString();
        newDurationStr = etDuration.getText().toString();
        newAttemptsStr = etAttempts.getText().toString();
        newQuestionsStr = etQuestions.getText().toString();

        if( newName.isEmpty() || newInfo.isEmpty() || newAvailableFromStr.isEmpty() || newAvailableToStr.isEmpty()
                || newDurationStr.isEmpty() || newAttemptsStr.isEmpty() || newQuestionsStr.isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.allFields, Toast.LENGTH_SHORT).show();
            return false;
        } else if(!dateValidation(newAvailableFromStr)){
            Toast.makeText(getApplicationContext(), "'From' date is not in format dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!dateValidation(newAvailableToStr)){
            Toast.makeText(getApplicationContext(), "'To' date is not in format dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            return  false;
        } else if(!numberValidation(newDurationStr)){
            Toast.makeText(getApplicationContext(), "Duration time is not a valid, positive number", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!numberValidation(newAttemptsStr)){
            Toast.makeText(getApplicationContext(), "Max. number of attempts is not a valid, positive number", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!numberValidation(newQuestionsStr)){
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
