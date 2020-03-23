package com.example.discussionboard.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.Thread;
import com.example.discussionboard.database.viewmodel.ThreadViewModel;
import com.example.discussionboard.ui.admin.AdminActivity;
import com.example.discussionboard.ui.feed.FeedActivity;
import com.example.discussionboard.ui.login.LoginActivity;
import com.example.discussionboard.ui.rest.ProfileActivity;
import com.example.discussionboard.ui.thread.ShowThreads;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Locale;

public class MenuActivity extends AppCompatActivity {

    public static int userId;
    public static boolean admin;
    private DrawerLayout drawerLayout;
    private ThreadViewModel threadViewModel;
    int amountThreads;

    private TextView amount;

    String currentLanguage = "en", currentLang;
    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        amount = findViewById(R.id.amount);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            userId = getIntent().getExtras().getInt("userId");
            admin = getIntent().getExtras().getBoolean("admin");
        }

        System.out.println("User id :"+userId);

        //Display the drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        //Hide Admin menu if not admin
        if (admin == false){
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
                            case R.id.nav_admin:
                                Intent i6 = new Intent(MenuActivity.this, AdminActivity.class);
                                startActivity(i6);
                                break;
                            case R.id.nav_lang:
                                System.out.println(R.id.nav_lang);
                                //calling changing langugage method
                                setLocale("de");
                        }
                        return true;
                    }
                });

        getAmountThreads(amount);

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

    public void getAmountThreads(final TextView textView){
        threadViewModel = new ViewModelProvider(this).get(ThreadViewModel.class);
        threadViewModel.getAllThread().observe(this, new Observer<List<Thread>>() {
            @Override
            public void onChanged(List<Thread> threads) {
                amountThreads = threads.size();
                System.out.println("Size "+amountThreads);
                textView.setText(amountThreads+ " Threads online");
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

    //Change language method
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
    }
}
