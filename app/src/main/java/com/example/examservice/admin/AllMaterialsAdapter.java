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
import com.example.examservice.database.LearningMaterial;

import java.util.ArrayList;

public class AllMaterialsAdapter extends RecyclerView.Adapter<AllMaterialsAdapter.ViewHolder> {

    private ArrayList<LearningMaterial> materials ;
    private Context thisContext ;

    public AllMaterialsAdapter(Context context, ArrayList<LearningMaterial> list){
        materials = list;
        thisContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, required;
        Button delete, edit;
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.materialNameTxtView);
            required = itemView.findViewById(R.id.materialIsRequiredTxtView);
            delete = itemView.findViewById(R.id.onMaterialDeleteClickButton);
            edit = itemView.findViewById(R.id.onMaterialEditClickButton);
        }
    }

    @NonNull
    @Override
    public AllMaterialsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllMaterialsAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(position);
        holder.delete.setTag(position);
        holder.edit.setTag(position);

        LearningMaterial temp = materials.get(position);

        String is = temp.isIs_required() ? "tak" : "nie" ;
        holder.name.setText(temp.getName());
        holder.required.setText(is);

    }

    @Override
    public int getItemCount() {
        return materials.size();

    }

}
