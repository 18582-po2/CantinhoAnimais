package com.example.cantinhodosanimais;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AdoptionQuestionnaireActivity extends AppCompatActivity {

    private  String animal_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_questionnaire);

        Bundle bundle = getIntent().getExtras();
        animal_ID = bundle.getString("animal_ID");
    }
}