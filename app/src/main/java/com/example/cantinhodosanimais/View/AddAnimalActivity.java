package com.example.cantinhodosanimais.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cantinhodosanimais.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * This class takes part in the adding animal process.
 * Here, the administrator adds animals for the adopter-users see and adopt afterwards.
 */
public class AddAnimalActivity extends Fragment implements View.OnClickListener {

    View v;
    private static final int CHOOSE_IMAGE_CODE = 1;
    private Button btn_add_animal, btn_add_photo;
    private TextInputEditText text_input_gender, text_input_age, text_input_race, text_input_name,
            text_input_deficiency, text_input_personality, text_input_story;
    private FirebaseFirestore mStore;
    private StorageReference mStorage;
    private ProgressBar add_animal_progressBar;
    private Uri imageURI;


    /**
     * Inicialize variables and link to layout's id's
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_add_animal, container, false);

        mStore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();

        add_animal_progressBar = v.findViewById(R.id.progressBar_add_animal);
        text_input_gender = v.findViewById(R.id.input_gender);
        text_input_age = v.findViewById(R.id.input_age);
        text_input_race = v.findViewById(R.id.input_race);
        text_input_name = v.findViewById(R.id.input_name);
        text_input_deficiency = v.findViewById(R.id.input_deficciency);
        text_input_personality = v.findViewById(R.id.input_personality);
        text_input_story = v.findViewById(R.id.input_story);

        btn_add_photo = v.findViewById(R.id.btn_add_photos);
        btn_add_photo.setOnClickListener(this);
        btn_add_animal = v.findViewById(R.id.btn_add_animal);
        btn_add_animal.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_animal) {
            addNewAnimal();
        }
        if (v.getId() == R.id.btn_add_photos) {
            chooseAnimalPhoto();
        }
    }


    /**
     * Method to receive and validate user's (admin) input.
     * In this mehtod, it's also called the method to save this input as a new animal for adoption.
     * Req. 6 - Fonte de dados remota
     */
    private void addNewAnimal() {

        String genero = text_input_gender.getText().toString();
        String idade = text_input_age.getText().toString();
        String raca = text_input_race.getText().toString();
        String nome = text_input_name.getText().toString();
        String deficiencia = text_input_deficiency.getText().toString();
        String personalidade = text_input_personality.getText().toString();
        String historia = text_input_story.getText().toString();

        if (genero.isEmpty()) {
            text_input_gender.setError("Preencha o gênero do animal!");
            text_input_gender.requestFocus();
            return;
        }
        if (idade.isEmpty()) {
            text_input_age.setError("Preencha a idade do animal!");
            text_input_age.requestFocus();
            return;
        }
        if (raca.isEmpty()) {
            text_input_race.setError("Preencha a raça do animal!");
            text_input_race.requestFocus();
            return;
        }
        if (nome.isEmpty()) {
            text_input_name.setError("Preencha o nome do animal!");
            text_input_name.requestFocus();
            return;
        }
        if (deficiencia.isEmpty()) {
            text_input_deficiency.setError("Preencha a deficiência do animal!");
            text_input_deficiency.requestFocus();
            return;
        }
        if (personalidade.isEmpty()) {
            text_input_personality.setError("Preencha a personalidade do animal!");
            text_input_personality.requestFocus();
            return;
        }
        if (historia.isEmpty()) {
            text_input_story.setError("Preencha a historia do animal!");
            text_input_story.requestFocus();
            return;
        }
        if (historia.length() < 5) {
            text_input_story.setError("Escreva uma história mais detalhada!");
            text_input_story.requestFocus();
            return;
        }

        add_animal_progressBar.setVisibility(View.VISIBLE);

        addNewAnimalIntoFirebase(genero, idade, raca, nome, deficiencia, personalidade, historia);
    }


    /**
     * This method verifies if the user (admin) inserted a photo of the animal, otherwise
     * the app sends a message asking the user to do so.
     * @return true se sim, false se nao
     * Req. 6 - Fonte de dados remota
     * Req. 4 - Fotos e galeria
     */
    private boolean isImageExists() {
        if (imageURI == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Atenção");
            alertDialog.setMessage("Tem de selecionar uma imagem para animal!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            add_animal_progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
            alertDialog.show();
            btn_add_photo.requestFocus();
            return false;
        } else {
            return true;
        }
    }


    /**
     *  method that saves data on firebase.
     * @param gender
     * @param age
     * @param race
     * @param name
     * @param deficiency
     * @param personality
     * @param story
     * Req. 6 - Fonte de dados remota
     */
    private void addNewAnimalIntoFirebase(String gender, String age, String race, String name, String deficiency, String personality, String story) {
        if (isImageExists()) {
            Map<String, Object> animal = new HashMap<>();
            animal.put("genero", gender);
            animal.put("idade", age);
            animal.put("raca", race);
            animal.put("nome", name);
            animal.put("deficiencia", deficiency);
            animal.put("personalidade", personality);
            animal.put("historia", story);
            animal.put("imgURI", imageURI.toString());

            mStore.collection("animais").add(animal).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    add_animal_progressBar.setVisibility(View.INVISIBLE);
                    uploadImageToFirebase();
                    Toast.makeText(getActivity(), "Animal registado com sucesso!", Toast.LENGTH_LONG).show();
                    clearFields();

                    // FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    //transaction.replace(R.id.main_container_admin_frag, new MainAdminActivity()).commit();

                }
            });
        }
    }

    /**
     * Cleans textboxes after an animal is inserted.
     * Req. 2 - Interface com utilizador
     */
    private void clearFields() {
        text_input_gender.setText("");
        text_input_gender.requestFocus();
        text_input_age.setText("");
        text_input_race.setText("");
        text_input_name.setText("");
        text_input_deficiency.setText("");
        text_input_personality.setText("");
        text_input_story.setText("");
    }

    /**
     * Adds photo on storage (database)
     * Req. 6 - Fonte de dados remota
     * Req. 4 - Fotos e galeria
     */
    private void uploadImageToFirebase() {
        StorageReference image = mStorage.child("animalsImages/" + imageURI);
        try {
            image.putFile(imageURI)
                    .addOnFailureListener(e -> Toast.makeText(getActivity(), "Falha ao carregar imagem", Toast.LENGTH_LONG).show());

        } catch (SecurityException e) {
            Toast.makeText(getActivity(), "Erro de segurança ao carregar a imagem do animal", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * To open gallery and deny choosing multiple photos
     * This method always to choose one photo only.
     * Req. 6 - Fonte de dados remota
     * Req. 4 - Fotos e galeria
     */
    private void chooseAnimalPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        startActivityForResult(intent, CHOOSE_IMAGE_CODE);
    }

    /**
     * This method saves the photo's path after it's selected.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * Req. 6 - Fonte de dados remota
     * Req. 4 - Fotos e galeria
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE_CODE) {
            if (resultCode == RESULT_OK) {
                imageURI = data.getData();
                Toast.makeText(getActivity(), "Fotografia do animal carregada com sucesso!", Toast.LENGTH_LONG).show();
            }
        }
    }
}