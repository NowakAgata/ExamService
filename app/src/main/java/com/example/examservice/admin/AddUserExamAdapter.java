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
import com.example.examservice.database.User;

import java.util.ArrayList;

public class AddUserExamAdapter extends RecyclerView.Adapter<AddUserExamAdapter.ViewHolder>{

    private ArrayList<User> users ;
    private Context thisContext ;

    public AddUserExamAdapter(Context context, ArrayList<User> list){
        users = list;
        thisContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtEmail, txtName ;
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            txtEmail = itemView.findViewById(R.id.singleRowStudentEmail);
            txtName = itemView.findViewById(R.id.singleRowStudentName);
        }
    }

    @NonNull
    @Override
    public AddUserExamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_add_user_exam_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddUserExamAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        User temp = users.get(position) ;

        String name = temp.getFirst_name() + " " + temp.getLast_name();
        holder.txtName.setText(name);
        holder.txtEmail.setText(temp.getEmail());

    }

    @Override
    public int getItemCount() {
        return users.size();

    }
}
