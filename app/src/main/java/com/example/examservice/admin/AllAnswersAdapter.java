package com.example.examservice.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examservice.R;
import com.example.examservice.database.Answer;

import java.util.ArrayList;

public class AllAnswersAdapter extends RecyclerView.Adapter<AllAnswersAdapter.ViewHolder> {

    private ArrayList<Answer> answers ;
    private Context thisContext ;

    public AllAnswersAdapter(Context context, ArrayList<Answer> list){
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
    public AllAnswersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_answer_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAnswersAdapter.ViewHolder holder, int position) {

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