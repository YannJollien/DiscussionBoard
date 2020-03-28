package com.example.discussionboard.ui.admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.discussionboard.R;
import com.example.discussionboard.adapter.UsersAdapter;
import com.example.discussionboard.database.entity.User;
import com.example.discussionboard.database.viewmodel.UserViewModel;

import java.util.List;

public class UsersAdminActivity extends AppCompatActivity {

    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_admin);
        setTitle(getString(R.string.admin_tile_users));


        RecyclerView recyclerView = findViewById(R.id.recycler_view_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final UsersAdapter adapter = new UsersAdapter();

        recyclerView.setAdapter(adapter);


        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> user) {
                adapter.setUser(user);
            }
        });

        adapter.setOnItemClickListener(new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                Intent intent = new Intent(UsersAdminActivity.this, UsersAdminActivityDetail.class);
                intent.putExtra("id",user.getId());
                intent.putExtra("fname", user.getFirstName());
                intent.putExtra("lname", user.getLastName());
                intent.putExtra("email", user.getEmail());
                intent.putExtra("pwd",user.getPassword());
                intent.putExtra("admin",user.isAdmin());
                startActivity(intent);
            }
        });

        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.users_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

    }

}
