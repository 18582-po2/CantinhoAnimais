package com.example.cantinhodosanimais.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cantinhodosanimais.Model.Animals;
import com.example.cantinhodosanimais.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * This class allows the user to see the animal's profile alone and adopt it if wanted.
 */
public class SeeAnimalActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_adopt_animal;
    private TextView tv_gender, tv_age, tv_race, tv_name, tv_deficiency, tv_personality, tv_story;
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

        imageView_see_animal = findViewById(R.id.img_see_animal_user);
        tv_gender = findViewById(R.id.tv_gender);
        tv_name = findViewById(R.id.tv_name);
        tv_race = findViewById(R.id.tv_race);
        tv_age = findViewById(R.id.tv_age);
        tv_deficiency = findViewById(R.id.tv_deficiency);
        tv_personality = findViewById(R.id.tv_personality);
        tv_story = findViewById(R.id.tv_history);
        btn_adopt_animal = findViewById(R.id.btn_adotar_animal);
        btn_adopt_animal.setOnClickListener(this);


        /**
         * Fills the RecyclerView with data saved in the animal's list
         */
        loadAnimalData(new FireStoreCallback() {
            @Override
            public void onCallBack(Animals animalsObj) {
                animalsList.add(animalsObj);

                for (int i = 0; i < animalsList.size() ; i++) {
                    tv_name.setText(String.valueOf(animalsList.get(i).getAnimal_name()));
                    tv_gender.setText(String.valueOf(animalsList.get(i).getAnimal_gender()));
                    tv_race.setText(String.valueOf(animalsList.get(i).getAnimal_race()));
                    tv_age.setText(String.valueOf(animalsList.get(i).getAnimal_age())+ " ano(s) de idade");
                    tv_deficiency.setText(String.valueOf(animalsList.get(i).getAnimal_deficiency()));
                    tv_personality.setText(String.valueOf(animalsList.get(i).getAnimal_personality()));
                    tv_story.setText(String.valueOf(animalsList.get(i).getAnimal_story()));
                    Picasso.get().load(animalsList.get(i).getImgURI()).into(imageView_see_animal);
                }
            }
        });
    }

    /**
     * Interface used to implement onCallBack method
     */
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

    /**
     * This method fetches data from animals (collection) in database and adds in the animals list
     * @param fireStoreCallback
     */
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

                                });
                            }
                        }

                    }
                });
    }
}