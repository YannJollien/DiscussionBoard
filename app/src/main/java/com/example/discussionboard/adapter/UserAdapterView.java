package com.example.discussionboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.User;

import java.util.List;

public class UserAdapterView extends RecyclerView.Adapter<UserAdapterView.UserHolder>{

    private Context context;
    List<User> userList;

    public UserAdapterView(Context context, List<User> userList){
        this.context=context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_view_2, viewGroup, false);
        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterView.UserHolder userHolder, int i) {
        User user = userList.get(i);
        userHolder.firstname.setText(user.getFirstname());
        userHolder.lastname.setText(user.getLastname());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder{
        public TextView firstname;
        public TextView lastname;

        public UserHolder(View itemView){
            super(itemView);

            firstname = itemView.findViewById(R.id.fname_view);
            lastname = itemView.findViewById(R.id.lname_view);

        }

    }

    public User getUser(int position){
        User user = userList.get(position);
        return user;
    }

}
