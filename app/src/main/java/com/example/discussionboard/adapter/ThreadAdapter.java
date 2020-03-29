package com.example.discussionboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.Post;
import com.example.discussionboard.database.entity.Thread;

import java.util.ArrayList;
import java.util.List;

public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.ThreadHolder> {

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(Thread thread);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    private List<Thread> threads = new ArrayList<>();

    @NonNull
    @Override
    public ThreadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thread_cardview,parent,false);
        return new ThreadHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadHolder holder, int position) {
        Thread currentThread = threads.get(position);
        holder.thread.setText(currentThread.getThread());
        holder.category.setText(currentThread.getCategory());

    }

    public Thread getThreadAt(int position) {
        return threads.get(position);
    }

    @Override
    public int getItemCount() {
        return threads.size();
    }

    public void setThreads(List<Thread> threads){
        this.threads = threads;
        notifyDataSetChanged();
    }

    class ThreadHolder extends RecyclerView.ViewHolder{

        private TextView thread;
        private TextView category;


        public ThreadHolder(@NonNull View itemView) {
            super(itemView);
            thread = itemView.findViewById(R.id.thread_view);
            category = itemView.findViewById(R.id.category_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onItemClick(threads.get(position));
                }
            });
        }
    }

}
