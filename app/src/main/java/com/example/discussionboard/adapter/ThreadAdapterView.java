package com.example.discussionboard.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.Thread;

import java.util.List;

public class ThreadAdapterView extends RecyclerView.Adapter<ThreadAdapterView.ThreadHolder> {

    private Context context;
    List<Thread> threadList;

    public ThreadAdapterView(Context context, List<Thread> threadList){
        this.context=context;
        this.threadList = threadList;
    }

    @NonNull
    @Override
    public ThreadAdapterView.ThreadHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.thread_view_2, viewGroup, false);
        return new ThreadHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadAdapterView.ThreadHolder threadHolder, int i) {
        Thread thread = threadList.get(i);
        threadHolder.thread.setText(thread.getThread());
        threadHolder.category.setText(thread.getCategory());
    }

    @Override
    public int getItemCount() {
        return threadList.size();
    }

    public class ThreadHolder extends RecyclerView.ViewHolder{
        public TextView thread;
        public TextView category;

        public ThreadHolder(View itemView){
            super(itemView);

            thread = itemView.findViewById(R.id.thread_view);
            category = itemView.findViewById(R.id.category_view);

        }

    }

    public Thread getPlantation(int position){
        Thread thread = threadList.get(position);
        return thread;
    }
}
