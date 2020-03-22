package com.example.discussionboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.ThreadTemp;

import java.util.ArrayList;
import java.util.List;

public class ThreadTempAdapter extends RecyclerView.Adapter<ThreadTempAdapter.ThreadTempHolder>{

    private List<ThreadTemp> threadsTemp = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ThreadTempHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.threads_admin_cardview, parent, false);
        return new ThreadTempHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadTempHolder holder, int position) {
        ThreadTemp currentThreadTemp = threadsTemp.get(position);
        holder.thread.setText(currentThreadTemp.getThread());
        holder.thread.setText(currentThreadTemp.getCategory());
        holder.submitter.setText(currentThreadTemp.getSubmitter());


    }

    @Override
    public int getItemCount() {
        return threadsTemp.size();
    }

    public void setThreadsTemp(List<ThreadTemp> threadsTemp) {
        this.threadsTemp = threadsTemp;
        notifyDataSetChanged();
    }

    public ThreadTemp getThreadTempAt(int position) {
        return threadsTemp.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ThreadTemp threadTemp);
    }

    class ThreadTempHolder extends RecyclerView.ViewHolder {

        private TextView thread;
        private TextView category;
        private TextView submitter;


        public ThreadTempHolder(@NonNull View itemView) {
            super(itemView);
            thread = itemView.findViewById(R.id.thread_admin_view);
            category = itemView.findViewById(R.id.category_admin_view);
            submitter = itemView.findViewById(R.id.submitter_admin_view);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(threadsTemp.get(position));
                    }

                }
            });
        }
    }

}
