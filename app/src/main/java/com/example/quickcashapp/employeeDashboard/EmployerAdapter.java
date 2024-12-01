package com.example.quickcashapp.employeeDashboard;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class EmployerAdapter extends RecyclerView.Adapter<EmployerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> employerList;

    public EmployerAdapter(Context context, ArrayList<String> employerList) {
        this.context = context;
        this.employerList = employerList;
    }

    @NonNull
    @Override
    public EmployerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployerAdapter.ViewHolder holder, int position) {
        String name = employerList.get(position);
        holder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return employerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
