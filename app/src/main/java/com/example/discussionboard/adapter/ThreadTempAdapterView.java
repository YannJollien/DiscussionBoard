package com.example.discussionboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.ThreadTemp;

import java.util.List;

public class ThreadTempAdapterView extends RecyclerView.Adapter<ThreadTempAdapterView.ThreadTempHolder>{

    private Context context;
    List<ThreadTemp> threadTempList;

    public ThreadTempAdapterView(Context context, List<ThreadTemp> threadTempList){
        this.context=context;
        this.threadTempList = threadTempList;
    }

    @NonNull
    @Override
    public ThreadTempHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.thread_temp_view_2, viewGroup, false);
        return new ThreadTempHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadTempAdapterView.ThreadTempHolder threadTempHolder, int i) {
        ThreadTemp threadTemp = threadTempList.get(i);
        threadTempHolder.thread.setText(threadTemp.getThread());
        threadTempHolder.category.setText(threadTemp.getCategory());
        threadTempHolder.submitter.setText(threadTemp.getSubmitter());
    }

    @Override
    public int getItemCount() {
        return threadTempList.size();
    }

    public class ThreadTempHolder extends RecyclerView.ViewHolder{
        public TextView thread;
        public TextView category;
        public TextView submitter;

        public ThreadTempHolder(View itemView){
            super(itemView);

            thread = itemView.findViewById(R.id.thread_admin_view);
            category = itemView.findViewById(R.id.category_admin_view);
            submitter = itemView.findViewById(R.id.submitter_admin_view);

        }

    }

    public ThreadTemp getThreadTemp(int position){
        ThreadTemp threadTemp = threadTempList.get(position);
        return threadTemp;
    }

}
