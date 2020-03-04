package com.example.discussionboard.ui.rest;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.User;
import com.example.discussionboard.database.viewmodel.UserViewModel;
import com.example.discussionboard.ui.MenuActivity;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    int userId;
    UserViewModel userViewModel;
    MenuActivity menuActivity;
    private TextView mail;
    private Button change;

    EditText passOld;
    EditText passNew;

    String firstName;
    String lastName;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mail = findViewById(R.id.profile_mail);
        change = findViewById(R.id.change);

        menuActivity = new MenuActivity();
        userId = menuActivity.userId;

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getId() == userId) {
                        mail.setText(users.get(i).getEmail());
                        email = users.get(i).getEmail();
                        firstName = users.get(i).getFirstName();
                        lastName = users.get(i).getLastName();
                        password = users.get(i).getPassword();

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

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alertdialog_custom_view,null);

                // Specify alert dialog is not cancelable/not ignorable
                builder.setCancelable(false);

                // Set the custom layout as alert dialog view
                builder.setView(dialogView);

                // Get the custom alert dialog view widgets reference
                Button btn_positive = (Button) dialogView.findViewById(R.id.dialog_positive_btn);
                Button btn_negative = (Button) dialogView.findViewById(R.id.dialog_negative_btn);
                passOld = (EditText) dialogView.findViewById(R.id.passwordOld);
                passNew = (EditText) dialogView.findViewById(R.id.passwordNew);

                // Create the alert dialog
                final AlertDialog dialog = builder.create();

                // Set positive/yes button click listener
                btn_positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the alert dialog
                        System.out.println(password);
                        System.out.println(passOld);
                        String passOldString = passOld.getText().toString();
                        String passNewString = passNew.getText().toString();
                        if (passOldString.equals(password) && !passOldString.equals(passNewString)){
                            User user = new User(firstName,lastName,email,passNew.getText().toString());
                            user.setId(userId);
                            userViewModel.update(user);
                            Toast.makeText(getApplicationContext(), "Password Changed!",
                                    Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }
                        if (passOldString.equals(passNewString)){
                            Toast.makeText(getApplicationContext(), "Same password!",
                                    Toast.LENGTH_LONG).show();
                            passNew.setTextColor(Color.RED);
                        }
                        if (!passOldString.equals(password)){
                            Toast.makeText(getApplicationContext(), "Wrong password!",
                                    Toast.LENGTH_LONG).show();
                            passOld.setTextColor(Color.RED);
                        }

                    }
                });

                // Set negative/no button click listener
                btn_negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss/cancel the alert dialog
                        //dialog.cancel();
                        dialog.dismiss();
                    }
                });

                // Display the custom alert dialog on interface
                dialog.show();
            }
        });
    }


}
