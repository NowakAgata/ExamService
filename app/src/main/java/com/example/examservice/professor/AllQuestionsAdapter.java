package com.example.examservice.professor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examservice.R;
import com.example.examservice.database.Question;

import java.util.ArrayList;

public class AllQuestionsAdapter extends RecyclerView.Adapter<AllQuestionsAdapter.ViewHolder> {

    private ArrayList<Question> questions ;
    private Context thisContext ;

    public AllQuestionsAdapter(Context context, ArrayList<Question> list){
        questions = list;
        thisContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtContent, txtCorrect ;
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            txtContent = itemView.findViewById(R.id.singleQuestionContentTxtView);
            txtCorrect = itemView.findViewById(R.id.singleQuestionCorrectTxtView);
        }
    }

    @NonNull
    @Override
    public AllQuestionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.professor_question_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllQuestionsAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(position);
        Question temp = questions.get(position);
        Log.d("QuestionsAdapter " , temp.toString());
        holder.txtContent.setText(temp.getContent());
        int max = temp.getMax_answers();
        String maxStr = Integer.toString(max);
        holder.txtCorrect.setText(maxStr);

    }

    @Override
    public int getItemCount() {
        return questions.size();

    }

}