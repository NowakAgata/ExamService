package com.example.examservice.professor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examservice.R;
import com.example.examservice.database.LearningMaterial;

import java.util.ArrayList;

public class AllMaterialsLittleAdapter extends RecyclerView.Adapter<AllMaterialsLittleAdapter.ViewHolder> {

    private ArrayList<LearningMaterial> materials ;
    private Context thisContext ;

    public AllMaterialsLittleAdapter(Context context, ArrayList<LearningMaterial> list){
        materials = list;
        thisContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.materialNameLittleRowTxtView);

        }
    }

    @NonNull
    @Override
    public AllMaterialsLittleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_little_row, parent, false);
        return new AllMaterialsLittleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllMaterialsLittleAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        LearningMaterial temp = materials.get(position);
        holder.name.setText(temp.getName());

    }

    @Override
    public int getItemCount() {
        return materials.size();

    }



}