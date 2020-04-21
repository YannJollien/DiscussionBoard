package com.example.discussionboard.settings;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.User;
import com.example.discussionboard.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class ProfileActivity extends AppCompatActivity {

    TextView name;
    LoginActivity main = new LoginActivity();

    String email;
    EditText passNew1;
    EditText passNew;
    ImageView image;
    DatabaseReference reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Button change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        name = (TextView) findViewById(R.id.text_name);
        change = findViewById(R.id.change);
        image = findViewById(R.id.showImage);


        //Get informations and profile picture
        if(user != null) {
            // Name, email address, and profile photo Url
            email = user.getEmail();

            try {
                final File tmpFile = File.createTempFile("img", "png");
                StorageReference reference = FirebaseStorage.getInstance().getReference().child("profiles/");

                //  "id" is name of the image file....

                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+".png").getFile(tmpFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        Bitmap bitmap = BitmapFactory.decodeFile(tmpFile.getAbsolutePath());

                        image.setImageBitmap(bitmap);

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }

        name.setText("Logged in as " + email);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePwd();
            }
        });


    }

    //Method to change the password
    public void changePwd() {
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alertdialog_custom_view, null);

                // Specify alert dialog is not cancelable/not ignorable
                builder.setCancelable(false);

                // Set the custom layout as alert dialog view
                builder.setView(dialogView);

                // Get the custom alert dialog view widgets reference
                Button btn_positive = (Button) dialogView.findViewById(R.id.dialog_positive_btn);
                Button btn_negative = (Button) dialogView.findViewById(R.id.dialog_negative_btn);
                passNew1 = (EditText) dialogView.findViewById(R.id.passwordNew1);
                passNew = (EditText) dialogView.findViewById(R.id.passwordNew);

                // Create the alert dialog
                final AlertDialog dialog = builder.create();

                // Set positive/yes button click listener
                btn_positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the alert dialog
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String passNew1String = passNew1.getText().toString();
                        String passNewString = passNew.getText().toString();

                        if (passNew1String.equals(passNewString) && passNewString.length() >= 6) {
                            user.updatePassword(passNewString)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), getString(R.string.password_ok),
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), getString(R.string.password_ok),
                                    Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }else {
                            passNew1.setTextColor(Color.RED);
                            passNew.setTextColor(Color.RED);
                            Toast.makeText(getApplicationContext(), getString(R.string.password_fail),
                                    Toast.LENGTH_LONG).show();
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
