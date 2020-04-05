package com.example.discussionboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.Thread;

import java.util.ArrayList;
import java.util.List;

//Thread adapter
public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.ThreadHolder> {

    private List<Thread> threadList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ThreadHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.thread_view_2, viewGroup, false);
        return new ThreadHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadHolder threadHolder, int i) {
        Thread currentThread = threadList.get(i);
        threadHolder.thread.setText(currentThread.getThread());
        threadHolder.category.setText(currentThread.getCategory());
    }

    @Override
    public int getItemCount() {
        return threadList.size();
    }

    public void setThread(List<Thread> plantations) {
        this.threadList = plantations;
        notifyDataSetChanged();
    }

    public Thread getThreadAt(int position) {
        return threadList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Thread thread);
    }

    class ThreadHolder extends RecyclerView.ViewHolder {
        private TextView thread;
        private TextView category;


        //Holding the data
        private ThreadHolder(@NonNull View itemView) {
            super(itemView);
            thread = itemView.findViewById(R.id.thread_view);
            category = itemView.findViewById(R.id.category_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(threadList.get(position));
                    // }
                }
            });

        }
    }
}
