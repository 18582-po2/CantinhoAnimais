package com.example.cantinhodosanimais;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class AddAnimalActivity extends Fragment implements View.OnClickListener {

    private static final int CHOOSE_IMAGE_CODE = 1;
    View v;
    private Button btn_add_animal, btn_add_photo;
    private TextInputEditText  text_input_genero, text_input_idade, text_input_raca, text_input_nome,
            text_input_deficiencia, text_input_personalidade, text_input_historia;
    private FirebaseFirestore mStore;
    private StorageReference mStorage;
    private ProgressBar add_animal_progressBar;
    private Uri imageURI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_add_animal, container, false);

        mStore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();

        add_animal_progressBar = v.findViewById(R.id.progressBar_add_animal);
        text_input_genero = v.findViewById(R.id.input_genero);
        text_input_idade = v.findViewById(R.id.input_idade);
        text_input_raca = v.findViewById(R.id.input_raca);
        text_input_nome = v.findViewById(R.id.input_nome);
        text_input_deficiencia = v.findViewById(R.id.input_deficiencia);
        text_input_personalidade = v.findViewById(R.id.input_persolidade);
        text_input_historia = v.findViewById(R.id.input_historia);

        btn_add_photo = v.findViewById(R.id.btn_add_fotos);
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
        if (v.getId() == R.id.btn_add_fotos) {
            chooseAnimalPhoto();
        }
    }


    private void addNewAnimal() {

        String genero = text_input_genero.getText().toString();
        String idade = text_input_idade.getText().toString();
        String raca = text_input_raca.getText().toString();
        String nome = text_input_nome.getText().toString();
        String deficiencia = text_input_deficiencia.getText().toString();
        String personalidade = text_input_personalidade.getText().toString();
        String historia = text_input_historia.getText().toString();

        if (genero.isEmpty()) {
            text_input_genero.setError("Preencha o gênero do animal!");
            text_input_genero.requestFocus();
            return;
        }
        if (idade.isEmpty()) {
            text_input_idade.setError("Preencha a idade do animal!");
            text_input_idade.requestFocus();
            return;
        }
        if (raca.isEmpty()) {
            text_input_raca.setError("Preencha a raça do animal!");
            text_input_raca.requestFocus();
            return;
        }
        if (nome.isEmpty()) {
            text_input_nome.setError("Preencha o nome do animal!");
            text_input_nome.requestFocus();
            return;
        }
        if (deficiencia.isEmpty()) {
            text_input_deficiencia.setError("Preencha a deficiência do animal!");
            text_input_deficiencia.requestFocus();
            return;
        }
        if (personalidade.isEmpty()) {
            text_input_personalidade.setError("Preencha a personalidade do animal!");
            text_input_personalidade.requestFocus();
            return;
        }
        if (historia.isEmpty()) {
            text_input_historia.setError("Preencha a historia do animal!");
            text_input_historia.requestFocus();
            return;
        }
        if (historia.length() < 5) {
            text_input_historia.setError("Escreva uma história mais detalhada!");
            text_input_historia.requestFocus();
            return;
        }

        add_animal_progressBar.setVisibility(View.VISIBLE);

        // validateFields(genero, idade, raca, nome, deficiencia, personalidade, historia);
        addNewAnimalIntoFirebase(genero, idade, raca, nome, deficiencia, personalidade, historia);

    }

    /**
     * Pra verficar se a app obteve a foto
     * @return true se sim, false se nao
     */
    private boolean isImageExists() {
        if (imageURI == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Atenção");
            alertDialog.setMessage("Tem de selecionar uma imagem para animal!" + imageURI);
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

    private void addNewAnimalIntoFirebase(String genero, String idade, String raca, String nome, String deficiencia, String personalidade, String historia) {


        if (isImageExists()) {
            Map<String, Object> animal = new HashMap<>();
            animal.put("genero", genero);
            animal.put("idade", idade);
            animal.put("raca", raca);
            animal.put("nome", nome);
            animal.put("deficiencia", deficiencia);
            animal.put("personalidade", personalidade);
            animal.put("historia", historia);
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

    private void clearFields() {
        text_input_genero.setText("");
        text_input_genero.requestFocus();
        text_input_idade.setText("");
        text_input_raca.setText("");
        text_input_nome.setText("");
        text_input_deficiencia.setText("");
        text_input_personalidade.setText("");
        text_input_historia.setText("");
    }

    /**
     * Adicionar a foto em si na storage
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
     * Para abrir a galeria e so permitir que seja selecionada uma foto
     */
    private void chooseAnimalPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        startActivityForResult(intent, CHOOSE_IMAGE_CODE);
    }

    /**
     * Para obter o caminho do ficheiro
     *
     * @param requestCode
     * @param resultCode
     * @param data
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