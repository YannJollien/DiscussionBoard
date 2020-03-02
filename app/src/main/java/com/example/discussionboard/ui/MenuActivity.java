package com.example.discussionboard.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.Thread;
import com.example.discussionboard.database.viewmodel.ThreadViewModel;
import com.example.discussionboard.ui.login.LoginActivity;
import com.example.discussionboard.ui.rest.ProfileActivity;
import com.example.discussionboard.ui.thread.ShowThreads;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MenuActivity extends AppCompatActivity {

    public static int userId;
    private DrawerLayout drawerLayout;
    private ThreadViewModel threadViewModel;
    int amountThreads;

    private TextView amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        amount = findViewById(R.id.amount);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            userId = getIntent().getExtras().getInt("userId");
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
}
