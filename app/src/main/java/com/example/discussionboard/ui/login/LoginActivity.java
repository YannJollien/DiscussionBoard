package com.example.discussionboard.ui.login;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.discussionboard.R;
import com.example.discussionboard.ui.menu.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static String CHANNEL_ID = "my_channel_01";
    //channel and id for Notification
    NotificationManagerCompat notificationManagerCompat;
    int NOTIFICATION_ID = 234;
    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp;
    private FirebaseAuth auth;

    ProgressBar progress;

    public String password;
    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        btnSignIn = (Button) findViewById(R.id.buttonSign);
        btnSignUp = (Button) findViewById(R.id.buttonReg);
        inputEmail = (EditText) findViewById(R.id.inMail);
        inputPassword = (EditText) findViewById(R.id.inPass);
        progress = findViewById(R.id.load);
        progress.setVisibility(View.INVISIBLE);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 progress.setVisibility(View.VISIBLE);
                 email = inputEmail.getText().toString();
                 password = inputPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_mail_enter), Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.INVISIBLE);
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_password_enter), Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.INVISIBLE);
                    return;
                }
                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        Toast.makeText(LoginActivity.this, getString(R.string.toast_password_short), Toast.LENGTH_LONG).show();
                                        progress.setVisibility(View.INVISIBLE);
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.toast_auth_fail), Toast.LENGTH_LONG).show();
                                        progress.setVisibility(View.INVISIBLE);
                                    }
                                } else {
                                    progress.setVisibility(View.GONE);
                                    addNotification();
                                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }


    //Adding the notification method
    private void addNotification() {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        //Nothing in intent --> delete notification wehn pressed
        Intent notificationIntent = new Intent();
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("DiscussionBoard")
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentText("Welcome " + email);

        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

}

