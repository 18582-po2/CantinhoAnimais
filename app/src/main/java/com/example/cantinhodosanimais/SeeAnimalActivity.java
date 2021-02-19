package com.example.cantinhodosanimais;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SeeAnimalActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_adopt_animal;
    private TextView tv_genero, tv_idade, tv_raca, tv_nome, tv_deficiencia, tv_personalidade, tv_historia;
    private String animal_ID;
    private ImageView imageView_see_animal;
    private ArrayList<Animals> animalsList;
    private FirebaseFirestore mStore;
    private StorageReference mStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_animal);

        mStore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        animalsList = new ArrayList<>();


        Bundle bundle = getIntent().getExtras();
        animal_ID = bundle.getString("animal_ID");
        //Log.i("ID " + animal_ID, "AAAAA");

        imageView_see_animal = findViewById(R.id.img_see_animal_user);
        tv_genero = findViewById(R.id.tv_genero);
        tv_nome = findViewById(R.id.tv_nome);
        tv_raca = findViewById(R.id.tv_raca);
        tv_idade = findViewById(R.id.tv_idade);
        tv_deficiencia = findViewById(R.id.tv_deficiencia);
        tv_personalidade = findViewById(R.id.tv_personalidade);
        tv_historia= findViewById(R.id.tv_historia);
        btn_adopt_animal = findViewById(R.id.btn_adotar_animal);
        btn_adopt_animal.setOnClickListener(this);

        loadAnimalData(new FireStoreCallback() {
            @Override
            public void onCallBack(Animals animalsObj) {
                animalsList.add(animalsObj);

                for (int i = 0; i < animalsList.size() ; i++) {
                    tv_nome.setText(String.valueOf(animalsList.get(i).getAnimal_nome()));
                    tv_genero.setText(String.valueOf(animalsList.get(i).getAnimal_genero()));
                    tv_raca.setText(String.valueOf(animalsList.get(i).getAnimal_raca()));
                    tv_idade.setText(String.valueOf(animalsList.get(i).getAnimal_idade())+ " ano(s) de idade");
                    tv_deficiencia.setText(String.valueOf(animalsList.get(i).getAnimal_deficiencia()));
                    tv_personalidade.setText(String.valueOf(animalsList.get(i).getAnimal_personalidade()));
                    tv_historia.setText(String.valueOf(animalsList.get(i).getAnimal_historia()));
                    Picasso.get().load(animalsList.get(i).getImgURI()).into(imageView_see_animal);
                }
            }
        });
    }

    private interface FireStoreCallback {
        void onCallBack(Animals animalsObj);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_adotar_animal) {
            Intent intent = new Intent(SeeAnimalActivity.this, AdoptionQuestionnaireActivity.class);
            intent.putExtra("animal_ID", animal_ID);
            startActivity(intent);
            finish();
        }
    }

    private void loadAnimalData(SeeAnimalActivity.FireStoreCallback fireStoreCallback) {

        mStore.collection("animais")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                mStorage.child("animalsImages/" + document.getString("imgURI")).getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            if (document.getId().equals( animal_ID)) {
                                          //      Log.i("ID "+ animal_ID, "AAAAA"+document.getId());
                                                Animals animals = new Animals(
                                                        document.getId(),
                                                        document.getString("genero"),
                                                        document.getString("deficiencia"),
                                                        document.getString("personalidade"),
                                                        document.getString("nome"),
                                                        document.getString("idade"),
                                                        document.getString("raca"),
                                                        document.getString("historia"),
                                                        uri.toString());
                                                fireStoreCallback.onCallBack(animals);
                                            }
                                        }).addOnFailureListener(exception ->{
                                    Log.i("ERRRRROOOO ", "AAAAA");

                                });
                            }
                        }

                    }
                });
    }
}