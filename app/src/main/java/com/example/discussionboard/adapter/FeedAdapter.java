package com.example.discussionboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.Feed;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder>{



    private List<Feed> feeds = new ArrayList<>();

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_cardview,parent,false);
        return new FeedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder holder, int position) {
        Feed currentFeed = feeds.get(position);
        holder.context.setText(currentFeed.getSubmitter()+" "+currentFeed.getContext());
        holder.date_time.setText(currentFeed.getDate()+" "+currentFeed.getTime());
        if (currentFeed.getImageCode()==1){
            holder.image.setImageResource(R.drawable.ic_message_black_24dp);
        }
        if (currentFeed.getImageCode()==2){
            holder.image.setImageResource(R.drawable.ic_rss_feed_black_24dp);
        }

    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public void setFeeds(List<Feed> feeds){
        this.feeds = feeds;
        notifyDataSetChanged();
    }

    class FeedHolder extends RecyclerView.ViewHolder{

        private TextView context;
        private TextView date_time;
        private ImageView image;

        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.findViewById(R.id.feed_context);
            date_time = itemView.findViewById(R.id.feed_date_time);
            image = itemView.findViewById(R.id.feed_image);

        }
    }

}
