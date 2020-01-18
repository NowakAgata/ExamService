package com.example.examservice.professor;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.examservice.R;
import com.example.examservice.database.Answer;
import com.example.examservice.database.Exam;

import java.util.ArrayList;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private ArrayList<Answer> answers ;
    private Context thisContext ;

    public AnswersAdapter(Context context, ArrayList<Answer> list){
        answers = list;
        thisContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView answer, correct;
        Button delete ;
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            answer = itemView.findViewById(R.id.singleAnswerContentTxtView);
            correct = itemView.findViewById(R.id.singleAnswerCorrectTxtView);
            delete = itemView.findViewById(R.id.onAnswerDeleteClickButton);
        }
    }

    @NonNull
    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.professor_single_answer_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(position);
        holder.delete.setTag(position);

        Answer temp = answers.get(position) ;
        String is = temp.getIs_true() ? "yes" : "no" ;
        holder.answer.setText(temp.getContent());
        holder.correct.setText(is);

    }

    @Override
    public int getItemCount() {
        return answers.size();

    }

}
