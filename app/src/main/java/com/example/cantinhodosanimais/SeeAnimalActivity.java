package com.example.cantinhodosanimais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class SeeAnimalActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_adopt_animal;
    private TextView  tv_genero, tv_idade, tv_raca, tv_nome, tv_deficiencia, tv_personalidade, tv_historia;
    private FirebaseFirestore mStore;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_animal);

        mStore = FirebaseFirestore.getInstance();

        tv_genero = findViewById(R.id.tv_genero);
        tv_nome = findViewById(R.id.tv_nome);
        tv_raca = findViewById(R.id.tv_raca);
        tv_idade = findViewById(R.id.tv_idade);
        tv_deficiencia = findViewById(R.id.tv_deficiencia);
        tv_personalidade = findViewById(R.id.tv_personalidade);
        tv_personalidade = findViewById(R.id.tv_historia);
        btn_adopt_animal = findViewById(R.id.btn_adotar_animal);
        btn_adopt_animal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_adotar_animal){
            startActivity(new Intent(SeeAnimalActivity.this, AdoptionQuestionnaireActivity.class));
        }
    }
}