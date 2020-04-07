package com.example.discussionboard.ui.login;


import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.discussionboard.R;

import com.example.discussionboard.databse.entity.User;

import com.example.discussionboard.util.OnAsyncEventListener;
import com.example.discussionboard.viewmodel.post.PostViewModel;
import com.example.discussionboard.viewmodel.user.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class RegisterActivity extends AppCompatActivity {

    Button bSave;

    private EditText editEmail;
    private EditText editPassword;

    DatabaseReference databaseUser;

    DatabaseReference datebaserUser1;
    UserViewModel viewModel;

    public TextView firstname;
    public TextView lastname;

    FirebaseUser user;


    FirebaseAuth auth;
    static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        datebaserUser1 = FirebaseDatabase.getInstance().getReference("users");

        initializeUI();

        setTitle("Registration");

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        databaseUser = FirebaseDatabase.getInstance().getReference("user");

        bSave = (Button) findViewById(R.id.button_save);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create user

                registerNewUser();

            }
        });

    }

    private void registerNewUser() {

        String email, password;
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterActivity.this, "User created", Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        user = task.getResult().getUser();
                        FirebaseUser user = task.getResult().getUser();
                        System.out.println(("onComplete: uid=" + user.getUid()));
                        id = user.getUid();
                        saveUser(id);
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });

    }

    public void saveUser(String id){
        String firstNameString = (firstname.getText().toString());
        String lastNameString = (lastname.getText().toString());

        User user = new User(id, firstNameString, lastNameString);

        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), id);
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        viewModel.createUser(user, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        Toast.makeText(RegisterActivity.this, "Saved",
                Toast.LENGTH_LONG).show();
        firstname.setText("");
        lastname.setText("");
        firstname.setText("");
        lastname.setText("");
        System.out.println(id);
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }




    private void initializeUI() {
        editEmail = findViewById(R.id.mailRegister);
        editPassword = findViewById(R.id.passRegister);
        firstname = findViewById(R.id.fnameRegister);
        lastname = findViewById(R.id.lnameRegister);
    }




}
