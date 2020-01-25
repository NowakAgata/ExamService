package com.example.examservice.student;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.examservice.R;
import com.example.examservice.database.Exam;

import java.util.ArrayList;

public class AllExamsAdapter extends RecyclerView.Adapter<AllExamsAdapter.ViewHolder> {

    private ArrayList<Exam> exams ;
    private Context thisContext ;

    public AllExamsAdapter(Context context, ArrayList<Exam> list){
        exams = list;
        thisContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtName, txtInfo ;
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            txtInfo = itemView.findViewById(R.id.singleRowExamInformation);
            txtName = itemView.findViewById(R.id.singleRowExamName);
        }
    }

    @NonNull
    @Override
    public AllExamsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllExamsAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        Exam temp = exams.get(position) ;

        holder.txtName.setText(temp.getName());
        holder.txtInfo.setText(temp.getAdditional_information());

    }

    @Override
    public int getItemCount() {
        return exams.size();

    }

}
