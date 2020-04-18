package com.example.discussionboard.ui.login;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.User;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.example.discussionboard.viewmodel.user.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class RegisterActivity extends AppCompatActivity {

    //constant to track image chooser intent
    private static final int PICK_IMAGE_REQUEST = 1;
    public TextView firstname;
    public TextView lastname;
    private Button bSave;
    UserViewModel viewModel;
    FirebaseAuth auth;
    //firebase objects
    private EditText editEmail;
    private EditText editPassword;
    private Button choose;
    private ImageView image;

    Bitmap bitmap;

    ProgressBar progress;

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

        //UI
        initializeUI();

        setTitle("Registration");

        //Progress Bar
        progress = findViewById(R.id.load);
        progress.setVisibility(View.INVISIBLE);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        bSave = (Button) findViewById(R.id.button_save);
        choose = findViewById(R.id.btnChoose);
        image = findViewById(R.id.imgView);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create user
                progress.setVisibility(View.VISIBLE);
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

        //If no image error, else register new user
        if(image.getDrawable() == null) {
            Toast.makeText(RegisterActivity.this, "Please select profile picture",
                    Toast.LENGTH_LONG).show();
            progress.setVisibility(View.INVISIBLE);
        }else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.

                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                uploadImage(auth.getCurrentUser().getUid());
                            }
                        }
                    });

        }
    }

    public void saveUser(String idUser) {
        String firstNameString = (firstname.getText().toString());
        String lastNameString = (lastname.getText().toString());

        User userNew = new User(idUser, firstNameString, lastNameString, false);

        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), idUser);
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        viewModel.createUser(userNew, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
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
    }


    private void initializeUI() {
        editEmail = findViewById(R.id.mailRegister);
        editPassword = findViewById(R.id.passRegister);
        firstname = findViewById(R.id.fnameRegister);
        lastname = findViewById(R.id.lnameRegister);
    }


    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            if (data != null) {
                // Get the URI of the selected file
                final Uri uri = data.getData();
                useImage(uri);
            }
        }
    }

    void useImage(Uri uri)
    {
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        //use the bitmap as you like
        image.setImageBitmap(bitmap);
    }


    public void uploadImage(String id) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        StorageReference pathReference = FirebaseStorage.getInstance().getReference().child("profiles/" + id + ".png");
        UploadTask uploadTask = pathReference.putBytes(byteArray);
        uploadTask = pathReference.putBytes(byteArray);
        uploadTask.addOnCompleteListener(RegisterActivity.this, task -> {
            if(task.isSuccessful()) {
                saveUser(id);
            } else {

            }
        });
    }
}
