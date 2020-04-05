package com.example.discussionboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder>{

    private List<Post> postList = new ArrayList<>();
    private PostAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public PostAdapter.PostHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_view_2, viewGroup, false);
        return new PostAdapter.PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostHolder threadHolder, int i) {
        Post currentPost = postList.get(i);
        threadHolder.submitter.setText(currentPost.getSubmitter());
        threadHolder.date.setText(currentPost.getDate());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void setThread(List<Post> posts) {
        this.postList = posts;
        notifyDataSetChanged();
    }

    public Post getPostAt(int position) {
        return postList.get(position);
    }

    public void setOnItemClickListener(PostAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Post post);
    }

    class PostHolder extends RecyclerView.ViewHolder {
        private TextView submitter;
        private TextView date;


        //Holding the data
        private PostHolder(@NonNull View itemView) {
            super(itemView);
            submitter = itemView.findViewById(R.id.submitter_view);
            date = itemView.findViewById(R.id.date_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(postList.get(position));
                    // }
                }
            });

        }
    }

}
