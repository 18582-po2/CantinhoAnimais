package com.example.cantinhodosanimais;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SeeAdoptionAdmin extends AppCompatActivity {

    private String adoption_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_adoption_admin);

        Bundle bundle = getIntent().getExtras();
        adoption_ID = bundle.getString("adoption_ID");
    }
}