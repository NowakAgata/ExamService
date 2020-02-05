package com.example.examservice.admin;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.examservice.R;
import com.example.examservice.database.User;

import java.util.ArrayList;

public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersAdapter.ViewHolder> {

        private ArrayList<User> users ;
        private Context thisContext ;

        public AllUsersAdapter(Context context, ArrayList<User> list){
            users = list;
            thisContext = context;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView txtEmail, txtName ;
            Button button ;
            public ViewHolder(@NonNull final View itemView){
                super(itemView);
                txtEmail = itemView.findViewById(R.id.singleRowProfessorEmail);
                txtName = itemView.findViewById(R.id.singleRowProfessorName);
                button = itemView.findViewById(R.id.adminDeleteUserButton);
            }
        }

        @NonNull
        @Override
        public AllUsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_user_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AllUsersAdapter.ViewHolder holder, int position) {

            holder.itemView.setTag(position);
            holder.button.setTag(position);

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