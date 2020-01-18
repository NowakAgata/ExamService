package com.example.examservice.professor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examservice.R;
import com.example.examservice.database.Answer;

import java.util.ArrayList;


public class StudentGroupsAdapter extends RecyclerView.Adapter<StudentGroupsAdapter.ViewHolder> {

    private ArrayList<String> groups ;
    private Context thisContext ;

    public StudentGroupsAdapter(Context context, ArrayList<String> list){
        groups = list;
        thisContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.singleGroupNameTxtView);

        }
    }

    @NonNull
    @Override
    public StudentGroupsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.professor_student_group_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentGroupsAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        String temp = groups.get(position);

        holder.name.setText(temp);

    }

    @Override
    public int getItemCount() {
        return groups.size();

    }

}