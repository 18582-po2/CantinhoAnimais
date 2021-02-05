package com.example.cantinhodosanimais;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainContainerUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container_user);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_user);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_user_frag, new MainAdminActivity()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedActivity = null;

        switch (item.getItemId()) {
            case R.id.nav_user_main:
                selectedActivity = new MainUserActivity();
                break;
            case R.id.nav_user_adoptions:
                selectedActivity = new UserAdoptionsActivity();
                break;
            case R.id.nav_about:
                selectedActivity = new AboutActivity();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_user_frag, selectedActivity).commit();
        return true;
    };
}