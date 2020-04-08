package com.example.discussionboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.Post;
import com.example.discussionboard.databse.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{

    private List<User> userList = new ArrayList<>();
    private UserAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public UserAdapter.UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_view_2, viewGroup, false);
        return new UserAdapter.UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserHolder userHolder, int i) {
        User currentUser = userList.get(i);
        userHolder.firstname.setText(currentUser.getFirstname());
        userHolder.lastname.setText(currentUser.getLastname());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUser(List<User> users) {
        this.userList = users;
        notifyDataSetChanged();
    }

    public User getUserAt(int position) {
        return userList.get(position);
    }

    public void setOnItemClickListener(UserAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private TextView firstname;
        private TextView lastname;


        //Holding the data
        private UserHolder(@NonNull View itemView) {
            super(itemView);
            firstname = itemView.findViewById(R.id.fname_view);
            lastname = itemView.findViewById(R.id.lname_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(userList.get(position));
                    // }
                }
            });

        }
    }

}
