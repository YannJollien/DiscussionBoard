package com.example.discussionboard.ui.menu;

import android.content.Intent;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.User;
import com.example.discussionboard.settings.ContactActivity;
import com.example.discussionboard.settings.ProfileActivity;

import com.example.discussionboard.ui.admin.AdminActivity;
import com.example.discussionboard.ui.chat.ChatActivity;
import com.example.discussionboard.ui.login.LoginActivity;
import com.example.discussionboard.ui.thread.ThreadViewActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MenuActivity extends AppCompatActivity {

    String currentLanguage = "en", currentLang;
    TextView sumS;
    TextView sumP;
    double sumStorage;
    double sumPlantation;
    float[] am = {0, 0, 0};
    float[] hec = {0, 0, 0};
    String[] typ = {"Arabica", "Robusta", "Liberica"};
    Button profile;
    private Locale locale;

    private RadioButton en;
    private RadioButton de;
    private TextView alertTitle;

    public static boolean admin;

    DatabaseReference reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setTitle("Dashboard");

        System.out.println("Hello");

        //Display the drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawerLayout = findViewById(R.id.drawer_layout);

        //Calling the items and tell them what to do
        NavigationView navigationView = findViewById(R.id.nav_view);

        //Check if
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user1 = dataSnapshot1.getValue(User.class);
                    if (user1.getId().equals(user.getUid())) {
                        System.out.println("UserID "+user1.getId());
                        if (user1.getAdmin()==true){
                            Menu nav_Menu = navigationView.getMenu();
                            nav_Menu.findItem(R.id.nav_admin).setVisible(true);
                        } else {
                            Menu nav_Menu = navigationView.getMenu();
                            nav_Menu.findItem(R.id.nav_admin).setVisible(false);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.nav_thread:
                                Intent i1 = new Intent(MenuActivity.this, ThreadViewActivity.class);
                                startActivity(i1);
                                break;
                            case R.id.nav_admin:
                                Intent i2 = new Intent(MenuActivity.this, AdminActivity.class);
                                startActivity(i2);
                                break;
                            case R.id.nav_chat:
                                Intent i3 = new Intent(MenuActivity.this, ChatActivity.class);
                                startActivity(i3);
                                break;
                            case R.id.nav_logout:
                                Intent i4 = new Intent(MenuActivity.this, LoginActivity.class);
                                startActivity(i4);
                                break;
                            case R.id.nav_contact:
                                Intent i5 = new Intent(MenuActivity.this, ContactActivity.class);
                                startActivity(i5);
                                break;
                            case R.id.nav_lang:
                                System.out.println(R.id.nav_lang);
                                //calling changing langugage method
                                changeLanguage();
                            /*case R.id.nav_logout:
                                Intent i4 = new Intent(MenuActivity.this, LoginActivity.class);
                                startActivity(i4);
                                Toast.makeText(MenuActivity.this, "Logged out",
                                        Toast.LENGTH_LONG).show();
                                break;
                            case R.id.nav_about:
                                Intent i5 = new Intent(MenuActivity.this, SettingsAboutActivity.class);
                                startActivity(i5);
                                break;*/
                        }
                        return true;
                    }
                });

        ImageButton ib = (ImageButton) navigationView.getHeaderView(0).findViewById(R.id.nav_button);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
            }
        });
    }

    //Open the drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method to change the language
    public void changeLanguage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_language, null);

        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(dialogView);

        // Get the custom alert dialog view widgets reference
        en = dialogView.findViewById(R.id.check_en);
        de = dialogView.findViewById(R.id.check_de);
        alertTitle = dialogView.findViewById(R.id.dialog_title);

        String currentLangauge = alertTitle.getText().toString();

        if (currentLangauge.contains("Sprache")){
            de.setChecked(true);
        }

        if (currentLangauge.contains("language")){
            en.setChecked(true);
        }


        Button btn_positive = (Button) dialogView.findViewById(R.id.dialog_positive_btn);
        Button btn_negative = (Button) dialogView.findViewById(R.id.dialog_negative_btn);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        // Set positive/yes button click listener
        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (en.isChecked()){
                    String languageToLoad = "en"; // your language
                    Locale locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                    dialog.dismiss();


                    Intent refresh = new Intent(MenuActivity.this, LoginActivity.class);
                    startActivity(refresh);
                    finish();
                }
                if (de.isChecked()){
                    String languageToLoad = "de"; // your language
                    Locale locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                    dialog.dismiss();


                    Intent refresh = new Intent(MenuActivity.this, LoginActivity.class);
                    startActivity(refresh);
                    finish();
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



}



