package com.example.discussionboard.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.User;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.example.discussionboard.viewmodel.user.UserListViewModel;
import com.example.discussionboard.viewmodel.user.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class AdminUserDetailActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private CheckBox admin;

    private Button save;

    String id;
    String fname;
    String lname;
    boolean adm;

    UserListViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users_detail);

        startActivity();
    }

    public void startActivity(){
        firstName = findViewById(R.id.fname_details);
        lastName = findViewById(R.id.lname_details);
        admin = findViewById(R.id.admin);

        save = findViewById(R.id.save);


        //Disable the editText for the view only
        firstName.setEnabled(false);
        lastName.setEnabled(false);

        fname = getIntent().getExtras().getString("fname");
        lname = getIntent().getExtras().getString("lname");
        adm = getIntent().getExtras().getBoolean("admin");
        id = getIntent().getExtras().getString("id");

        System.out.println("LNAME"+ lname);

        firstName.setText(fname);
        lastName.setText(lname);

        if (adm == true){
            admin.setChecked(true);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });


        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.show_admin_details_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void updateUser(){
        String fnameString = firstName.getText().toString();
        String lnameString = lastName.getText().toString();
        if (admin.isChecked()){
            adm = true;
        }

        if (!admin.isChecked()){
            adm = false;
        }

        User user = new User(id,fnameString,lnameString,adm);
        UserListViewModel.Factory factory = new UserListViewModel.Factory(getApplication(), id);
        model = ViewModelProviders.of(this, factory).get(UserListViewModel.class);
        model.updateUser(user, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        Toast.makeText(getApplicationContext(), getString(R.string.toast_user_update),
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(AdminUserDetailActivity.this,AdminUsersActivity.class);
        startActivity(intent);
    }

}
