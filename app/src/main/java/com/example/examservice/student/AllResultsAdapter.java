package com.example.examservice.student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examservice.R;
import com.example.examservice.database.Exam;
import com.example.examservice.database.Result;

import java.util.ArrayList;

public class AllResultsAdapter extends  RecyclerView.Adapter<AllResultsAdapter.ViewHolder>{

    private ArrayList<Result> results ;
    private Context thisContext ;

    public AllResultsAdapter(Context context, ArrayList<Result> list){
        results = list;
        thisContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtPoints, txtPassed ;
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            txtPoints = itemView.findViewById(R.id.resultRowPoints);
            txtPassed = itemView.findViewById(R.id.resultRowIsPassed);
        }
    }

    @NonNull
    @Override
    public AllResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllResultsAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        Result temp = results.get(position);

        String points = Integer.toString(temp.getPoints());
        holder.txtPoints.setText(points);
        String passed = temp.getIs_passed() ? "yes" : "no";
        holder.txtPassed.setText(passed);

    }

    @Override
    public int getItemCount() {
        return results.size();

    }
}
