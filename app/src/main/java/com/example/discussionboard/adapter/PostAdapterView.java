package com.example.discussionboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.Post;

import java.util.List;

public class PostAdapterView extends RecyclerView.Adapter<PostAdapterView.PostHolder>{

    private Context context;
    List<Post> postList;

    public PostAdapterView(Context context, List<Post> postList){
        this.context=context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostAdapterView.PostHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_view_2, viewGroup, false);
        return new PostAdapterView.PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapterView.PostHolder threadHolder, int i) {
        Post post = postList.get(i);
        threadHolder.submitter.setText(post.getSubmitter());
        threadHolder.date.setText(post.getDate());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder{
        public TextView submitter;
        public TextView date;

        public PostHolder(View itemView){
            super(itemView);

            submitter = itemView.findViewById(R.id.submitter_view);
            date = itemView.findViewById(R.id.date_view);

        }

    }

    public Post getPost(int position){
        Post post = postList.get(position);
        return post;
    }

}
