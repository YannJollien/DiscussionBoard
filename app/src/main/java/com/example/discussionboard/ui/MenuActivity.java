package com.example.discussionboard.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.discussionboard.R;
import com.example.discussionboard.ui.login.LoginActivity;
import com.example.discussionboard.ui.thread.ShowThreads;
import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity {

    public static int userId;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        userId = getIntent().getExtras().getInt("userId");

        System.out.println("User id :"+userId);

        //Display the drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
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
                            /*case R.id.nav_about:
                                Intent i5 = new Intent(MenuActivity.this, SettingsAboutActivity.class);
                                startActivity(i5);
                                break;*/
                            /*case R.id.nav_lang:
                                System.out.println(R.id.nav_lang);
                                //calling changing langugage method
                                setLocale("de");*/
                        }
                        return true;
                    }
                });


    }

    public int getUserId() {
        return userId;
    }
}
