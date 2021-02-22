package com.example.cantinhodosanimais.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.cantinhodosanimais.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;



/**
 * This class corresponds to the administrators page frame
 * that contains the menu
 */
public class MainContainerUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container_user);

        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_user);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_user_frag, new MainUserActivity()).commit();
    }



    /**
     * According to selected option , this method opens the corresponding activity
     * Req. 2 - Interface com utilizadores
     */
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
            case R.id.nav_signout:
               mAuth.signOut();
                selectedActivity = new AboutActivity();
               startActivity(new Intent(this, LoginActivity.class));
               finish();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_user_frag, selectedActivity).commit();
        return true;
    };
}