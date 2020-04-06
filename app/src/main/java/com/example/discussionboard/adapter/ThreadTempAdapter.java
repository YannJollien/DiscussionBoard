package com.example.discussionboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.ThreadTemp;

import java.util.ArrayList;
import java.util.List;

public class ThreadTempAdapter extends RecyclerView.Adapter<ThreadTempAdapter.ThreadTempHolder>{

    private List<ThreadTemp> threadTempList = new ArrayList<>();
    private ThreadTempAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public ThreadTempHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.thread_temp_view_2, viewGroup, false);
        return new ThreadTempHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadTempAdapter.ThreadTempHolder threadTempHolder, int i) {
        ThreadTemp currentThread = threadTempList.get(i);
        threadTempHolder.thread.setText(currentThread.getThread());
        threadTempHolder.category.setText(currentThread.getCategory());
        threadTempHolder.submitter.setText(currentThread.getSubmitter());
    }

    @Override
    public int getItemCount() {
        return threadTempList.size();
    }

    public void setThread(List<ThreadTemp> threads) {
        this.threadTempList = threads;
        notifyDataSetChanged();
    }

    public ThreadTemp getThreadTempAt(int position) {
        return threadTempList.get(position);
    }

    public void setOnItemClickListener(ThreadTempAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ThreadTemp threadTemp);
    }

    class ThreadTempHolder extends RecyclerView.ViewHolder {
        private TextView thread;
        private TextView category;
        private TextView submitter;


        //Holding the data
        private ThreadTempHolder(@NonNull View itemView) {
            super(itemView);
            thread = itemView.findViewById(R.id.thread_admin_view);
            category = itemView.findViewById(R.id.category_admin_view);
            submitter = itemView.findViewById(R.id.submitter_admin_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(threadTempList.get(position));
                    // }
                }
            });

        }
    }

}
