package com.example.examservice.student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examservice.R;
import com.example.examservice.database.LearningMaterialsGroup;

import java.util.ArrayList;

public class AllMaterialsGroupsAdapter extends RecyclerView.Adapter<AllMaterialsGroupsAdapter.ViewHolder> {

    private ArrayList<LearningMaterialsGroup> groups ;
    private Context thisContext ;

    public AllMaterialsGroupsAdapter(Context context, ArrayList<LearningMaterialsGroup> list){
        groups = list;
        thisContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.materialsGroupName);
        }
    }

    @NonNull
    @Override
    public AllMaterialsGroupsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_group_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllMaterialsGroupsAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        LearningMaterialsGroup temp = groups.get(position) ;

        holder.name.setText(temp.getName_of_group());

    }

    @Override
    public int getItemCount() {
        return groups.size();

    }

}
