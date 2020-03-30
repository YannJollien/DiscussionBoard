package com.example.discussionboard.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.discussionboard.R;
import com.example.discussionboard.database.viewmodel.ThreadViewModel;
import com.example.discussionboard.ui.admin.AdminActivity;
import com.example.discussionboard.ui.feed.FeedActivity;
import com.example.discussionboard.ui.login.LoginActivity;
import com.example.discussionboard.ui.rest.InfoActivity;
import com.example.discussionboard.ui.rest.ProfileActivity;
import com.example.discussionboard.ui.thread.ShowThreads;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class MenuActivity extends AppCompatActivity {

    public static int userId;
    public static boolean admin;
    public static final String KEY_PREF_LANGUAGE = "pref_language";
    public String languagePref_ID;
    private DrawerLayout drawerLayout;
    private ThreadViewModel threadViewModel;
    private ImageView image;
    private Locale locale;

    private CheckBox en;
    private CheckBox de;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        image = findViewById(R.id.image_menu);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            userId = getIntent().getExtras().getInt("userId");
            admin = getIntent().getExtras().getBoolean("admin");
        }

        System.out.println("User id :" + userId);

        //Display the drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        //Hide Admin menu if not admin
        if (admin == false) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_admin).setVisible(false);
        }
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.nav_thread:
                                Intent i1 = new Intent(MenuActivity.this, ShowThreads.class);
                                startActivity(i1);
                                break;
                            case R.id.nav_logout:
                                Intent i4 = new Intent(MenuActivity.this, LoginActivity.class);
                                startActivity(i4);
                                Toast.makeText(MenuActivity.this, "Logged out",
                                        Toast.LENGTH_LONG).show();
                                break;
                            case R.id.nav_chat:
                                Intent i5 = new Intent(MenuActivity.this, FeedActivity.class);
                                startActivity(i5);
                                break;
                            case R.id.nav_about:
                                Intent i7 = new Intent(MenuActivity.this, InfoActivity.class);
                                startActivity(i7);
                                break;
                            case R.id.nav_admin:
                                Intent i6 = new Intent(MenuActivity.this, AdminActivity.class);
                                startActivity(i6);
                                break;
                            case R.id.nav_lang:
                                System.out.println(R.id.nav_lang);
                                //calling changing langugage method
                                changeLanguage();

                        }
                        return true;
                    }
                });

        ImageButton ib = (ImageButton) navigationView.getHeaderView(0).findViewById(R.id.nav_button);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i6 = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(i6);
            }
        });


    }

    public int getUserId() {
        return userId;
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
