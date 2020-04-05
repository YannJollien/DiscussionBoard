package com.example.discussionboard.ui.menu;

import android.content.Intent;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.discussionboard.R;
import com.example.discussionboard.settings.ProfileActivity;

import com.example.discussionboard.ui.thread.ThreadViewActivity;
import com.google.android.material.navigation.NavigationView;

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

    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setTitle("Dashboard");

        //Display the drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawerLayout = findViewById(R.id.drawer_layout);

        //Calling the items and tell them what to do
        NavigationView navigationView = findViewById(R.id.nav_view);
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
                            /*case R.id.nav_plantation:
                                Intent i2 = new Intent(MenuActivity.this, ThreadViewActivity.class);
                                startActivity(i2);
                                break;
                            case R.id.nav_settings:
                                Intent i3 = new Intent(MenuActivity.this, SettingsActivity.class);
                                startActivity(i3);
                                break;
                            case R.id.nav_logout:
                                Intent i4 = new Intent(MenuActivity.this, LoginActivity.class);
                                startActivity(i4);
                                Toast.makeText(MenuActivity.this, "Logged out",
                                        Toast.LENGTH_LONG).show();
                                break;
                            case R.id.nav_about:
                                Intent i5 = new Intent(MenuActivity.this, SettingsAboutActivity.class);
                                startActivity(i5);
                                break;
                            case R.id.nav_lang:
                                System.out.println(R.id.nav_lang);
                                //calling changing langugage method
                                setLocale("de");*/
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

    /*Change language method
    public void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            locale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = locale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, LoginActivity.class);
            refresh.putExtra(currentLang, localeName);
            startActivity(refresh);
        } else {
            Toast.makeText(MenuActivity.this, "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }*/



}



