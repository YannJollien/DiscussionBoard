package com.example.discussionboard.ui.rest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.Thread;
import com.example.discussionboard.database.entity.User;
import com.example.discussionboard.database.viewmodel.ThreadViewModel;
import com.example.discussionboard.database.viewmodel.UserViewModel;
import com.example.discussionboard.ui.MenuActivity;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView mail;

    int userId;

    UserViewModel userViewModel;
    MenuActivity menuActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mail = findViewById(R.id.profile_mail);

        menuActivity = new MenuActivity();
        userId = menuActivity.userId;

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for (int i = 0 ; i < users.size(); i++){
                    if (users.get(i).getId() == userId){
                        mail.setText(users.get(i).getEmail());
                    }
                }
            }
        });

        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);


    }
}
