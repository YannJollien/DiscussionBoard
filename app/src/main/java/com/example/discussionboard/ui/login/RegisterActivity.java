package com.example.discussionboard.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.User;
import com.example.discussionboard.database.viewmodel.UserViewModel;

public class RegisterActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    private EditText fname;
    private EditText lname;
    private EditText mail;
    private EditText pass;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = findViewById(R.id.fname_in);
        lname = findViewById(R.id.lname_in);
        mail = findViewById(R.id.email_in);
        pass = findViewById(R.id.pwd_in);
        save = findViewById(R.id.add);

        setTitle("Add User");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
                Toast.makeText(getApplicationContext(), "User Added",
                        Toast.LENGTH_LONG).show();
                Intent inent= new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(inent);
            }
        });

    }

    public void saveUser(){
        String fnameString = fname.getText().toString();
        String lnameString = lname.getText().toString();
        String mailString = mail.getText().toString();
        String passString = pass.getText().toString();

        if (fnameString.trim().isEmpty() ||lnameString.trim().isEmpty() || mailString.trim().isEmpty() ||
        passString.trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter all informations",
                    Toast.LENGTH_LONG).show();
            return;
        }

        User user = new User(fnameString,lnameString,mailString,passString);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.insert(user);

    }
}
