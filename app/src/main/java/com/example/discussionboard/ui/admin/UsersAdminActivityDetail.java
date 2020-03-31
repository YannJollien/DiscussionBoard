package com.example.discussionboard.ui.admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.User;
import com.example.discussionboard.database.viewmodel.UserViewModel;
import com.example.discussionboard.ui.MenuActivity;

public class UsersAdminActivityDetail extends AppCompatActivity {

    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private CheckBox admin;

    private Button save;
    private Button delete;

    int id;
    int currentId;
    String fname;
    String lname;
    String mail;
    String pass;
    boolean adm;

    UserViewModel userViewModel;

    MenuActivity menuActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_admin_detail);

        startActivity();
    }

    public void startActivity(){
        firstName = findViewById(R.id.fname_details);
        lastName = findViewById(R.id.lname_details);
        email = findViewById(R.id.mail_details);
        admin = findViewById(R.id.admin);

        save = findViewById(R.id.save);
        delete = findViewById(R.id.delete_user);


        //Disable the editText for the view only
        firstName.setEnabled(false);
        lastName.setEnabled(false);
        email.setEnabled(false);

        id = getIntent().getExtras().getInt("id");
        fname = getIntent().getExtras().getString("fname");
        lname = getIntent().getExtras().getString("lname");
        mail = getIntent().getExtras().getString("email");
        pass = getIntent().getExtras().getString("pwd");
        adm = getIntent().getExtras().getBoolean("admin");

        currentId = menuActivity.userId;

        System.out.println("LNAME"+ lname);

        firstName.setText(fname);
        lastName.setText(lname);
        email.setText(mail);

        if (adm == true){
            admin.setChecked(true);
        }


        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fnameString = firstName.getText().toString();
                String lnameString = lastName.getText().toString();
                String mailString = email.getText().toString();
                if (admin.isChecked()){
                    adm = true;
                }

                if (!admin.isChecked()){
                    adm = false;
                }
                User user = new User(fnameString,lnameString,mailString,pass,adm);
                user.setId(id);
                userViewModel.update(user);
                Toast.makeText(getApplicationContext(), getString(R.string.toast_user_update),
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UsersAdminActivityDetail.this,UsersAdminActivity.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == currentId){
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_user_delete_false),
                            Toast.LENGTH_LONG).show();
                } else {
                    String fnameString = firstName.getText().toString();
                    String lnameString = lastName.getText().toString();
                    String mailString = email.getText().toString();
                    User user = new User(fnameString,lnameString,mailString,pass,adm);
                    user.setId(id);
                    userViewModel.delete(user);
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_user_delete),
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UsersAdminActivityDetail.this,UsersAdminActivity.class);
                    startActivity(intent);
                }
            }
        });

        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.show_admin_details_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
    }
}
