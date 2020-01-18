package com.example.examservice.professor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examservice.R;
import com.example.examservice.database.User;

import java.util.ArrayList;

public class AllStudentsAdapter extends RecyclerView.Adapter<AllStudentsAdapter.ViewHolder> {

    private ArrayList<User> users ;
    private Context thisContext ;

    public AllStudentsAdapter(Context context, ArrayList<User> list){
        users = list;
        thisContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtName ;
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            txtName = itemView.findViewById(R.id.singleStudentNameTxtView);
        }
    }

    @NonNull
    @Override
    public AllStudentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.professor_student_row, parent, false);
        return new AllStudentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllStudentsAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        User temp = users.get(position) ;

        String name = temp.getFirst_name() + " " + temp.getLast_name();
        holder.txtName.setText(name);

    }

    @Override
    public int getItemCount() {
        return users.size();

    }

}