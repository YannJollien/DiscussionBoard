package com.example.discussionboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder>{

    private List<User> user = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_cardview, parent, false);
        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User currentUser = user.get(position);
        holder.firstName.setText(currentUser.getFirstName());
        holder.lastName.setText(currentUser.getLastName());
        holder.email.setText(currentUser.getEmail());

    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public void setUser(List<User> user) {
        this.user = user;
        notifyDataSetChanged();
    }

    public User getUserAt(int position) {
        return user.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    class UserHolder extends RecyclerView.ViewHolder {

        private TextView firstName;
        private TextView lastName;
        private TextView email;


        public UserHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.fname_view);
            lastName = itemView.findViewById(R.id.lname_view);
            email = itemView.findViewById(R.id.mail_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(user.get(position));
                    }

                }
            });
        }
    }

}
