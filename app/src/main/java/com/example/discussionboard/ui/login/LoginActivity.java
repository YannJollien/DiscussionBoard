package com.example.discussionboard.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.User;
import com.example.discussionboard.database.viewmodel.UserViewModel;
import com.example.discussionboard.ui.MenuActivity;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    private Button login;
    private Button register;
    private EditText mail;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        startActivity();
    }

    public void startActivity() {
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        mail = findViewById(R.id.mail);
        pwd = findViewById(R.id.pass);

        //Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
                System.out.println("Click");
            }
        });

        //Register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(inent);
            }
        });
    }

    //Login Method
    public void login() {

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                String mailString = mail.getText().toString();
                String passString = pwd.getText().toString();

                int nr = userViewModel.getAllUsers().getValue().size();
                //Looping to check inputs
                for (int i = 0; i < nr; i++) {
                    if (userViewModel.getAllUsers().getValue().get(i).getEmail().equals(mailString)
                            && userViewModel.getAllUsers().getValue().get(i).getPassword().equals(passString)) {

                        Toast.makeText(getApplicationContext(), "Login successful",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        intent.putExtra("userId", users.get(i).getId());
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Please verrify E-Mail and Password",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}
