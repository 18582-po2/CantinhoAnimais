package com.example.cantinhodosanimais.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.cantinhodosanimais.R;
import com.example.cantinhodosanimais.View.AddAnimalActivity;
import com.example.cantinhodosanimais.View.AdminAdoptionsActivity;
import com.example.cantinhodosanimais.View.MainAdminActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * This class corresponds to the administrators page frame
 * that contains the menu
 */
public class MainContainerAdminActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container_admin);

        bottomNav = findViewById(R.id.bottom_nav_admin);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_admin_frag, new MainAdminActivity()).commit();
    }

    /**
     * According to selected option , this method opens the corresponding activity
     * Req. 2 - Interface com utilizadores
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedActivity = null;

            switch (item.getItemId()) {
                case R.id.nav_admin_main:
                    selectedActivity = new MainAdminActivity();
                    break;
                case R.id.nav_admin_new_animal:
                    selectedActivity = new AddAnimalActivity();
                    break;
                case R.id.nav_admin_adoptions:
                    selectedActivity = new AdminAdoptionsActivity();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container_admin_frag, selectedActivity).commit();
            return true;
        }

    };
}