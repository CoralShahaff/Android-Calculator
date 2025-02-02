package com.example.myapplicationrv.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationrv.R;
import com.example.myapplicationrv.models.Data;

import java.util.ArrayList;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.myViewHolder> {

    private ArrayList<Data> arr;         // Original data
    private ArrayList<Data> filteredList; // Filtered data

    // Constructor to initialize the adapter with the original data
    public CustomeAdapter(ArrayList<Data> arr) {
        this.arr = arr;
        this.filteredList = new ArrayList<>(arr); // Initialize filteredList as a copy of the original list
    }

    // ViewHolder class to hold the views
    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView nameVersion;
        ImageView imageViewItem;

        public myViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textName);
            nameVersion = itemView.findViewById(R.id.textVer);
            imageViewItem = itemView.findViewById(R.id.imageView);
        }
    }

    // Inflate the layout for each item in the RecyclerView
    @NonNull
    @Override
    public CustomeAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new myViewHolder(view);
    }

    // Bind the data to the views in each item
    @Override
    public void onBindViewHolder(@NonNull CustomeAdapter.myViewHolder holder, int position) {
        Data currentData = filteredList.get(position);
        holder.username.setText(currentData.getName());
        holder.nameVersion.setText(currentData.getVersion());
        holder.imageViewItem.setImageResource(currentData.getImage());
    }

    // Return the size of the filtered data
    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    // Method to filter the list based on the query
    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(arr); // If no query, show all items
        } else {
            for (Data data : arr) {
                if (data.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(data); // Add items that match the query
                }
            }
        }
        notifyDataSetChanged(); // Refresh the RecyclerView
    }
}