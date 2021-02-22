package com.example.cantinhodosanimais.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cantinhodosanimais.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for the process where the user-adopter fills the form
 * with their personal data to adopt an animal and can also convert the same form
 * to a file.
 */
public class AdoptionQuestionnaireActivity extends AppCompatActivity implements View.OnClickListener {

    private String animal_ID;
    private FirebaseFirestore mStore;
    private ProgressBar progressBar_saveAdoption;
    private FirebaseAuth mAuth;
    private TextView title;
    private Button btn_save_pfd, btn_finalize_request;
    private TextInputEditText input_fullname, input_age, input_profession, input_hasChildren, input_ChildrensAge, input_address, input_telephone, input_bi, input_houseType, input_hasOtherAnimals, input_otherAnimals, input_adoptionReasons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_questionnaire);

        Bundle bundle = getIntent().getExtras();
        animal_ID = bundle.getString("animal_ID");

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        title = findViewById(R.id.textView3);
        btn_save_pfd = findViewById(R.id.btn_save_pfd);
        btn_finalize_request = findViewById(R.id.btn_finalize_request);
        input_fullname = findViewById(R.id.input_fullname);
        input_age = findViewById(R.id.input_age);
        input_profession = findViewById(R.id.input_profession);
        input_hasChildren = findViewById(R.id.input_hasChildren);
        input_ChildrensAge = findViewById(R.id.input_childrensAge);
        input_address = findViewById(R.id.input_address);
        input_telephone = findViewById(R.id.input_telephone);
        input_bi = findViewById(R.id.input_bi);
        input_houseType = findViewById(R.id.input_houseType);
        input_hasOtherAnimals = findViewById(R.id.input_hasOtherAnimals);
        input_otherAnimals = findViewById(R.id.input_otherAnimals);
        input_adoptionReasons = findViewById(R.id.input_adoptionReasons);

        progressBar_saveAdoption = findViewById(R.id.progressBar_saveAdoption);

        btn_finalize_request.setOnClickListener(this);
        btn_save_pfd.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save_pfd) {
            generatePDF();
        }
        if (v.getId() == R.id.btn_finalize_request) {

            createNewAdoption();
        }
    }

    /**
     * Method to receive and validate user's (user) input.
     * In this mehtod, it's also called the method to save this input as a new adoption.
     * Req. 6 - Fontes de dados remota
     */
    private void createNewAdoption() {

        String titleT= title.getText().toString();
        String name = input_fullname.getText().toString();
        String age = input_age.getText().toString();
        String profession = input_profession.getText().toString();
        String hasChildren = input_hasChildren.getText().toString();
        String ChildrensAge = input_ChildrensAge.getText().toString();
        String address = input_address.getText().toString();
        String telephone = input_telephone.getText().toString();
        String bi = input_bi.getText().toString();
        String houseType = input_houseType.getText().toString();
        String hasOtherAnimals = input_hasOtherAnimals.getText().toString();
        String otherAnimals = input_otherAnimals.getText().toString();
        String adoptionReasons = input_adoptionReasons.getText().toString();


        if (name.isEmpty()) {
            input_fullname.setError("Preencha o seu  nome!");
            input_fullname.requestFocus();
            return;

        }
        if (age.isEmpty()) {
            input_age.setError("Preencha a sua idade!");
            input_age.requestFocus();
            return;

        }
        if (profession.isEmpty()) {
            input_profession.setError("Preencha a sua profissão!");
            input_profession.requestFocus();
            return;

        }
        if (hasChildren.isEmpty()) {
            input_hasChildren.setError("Preencha o campo!");
            input_hasChildren.requestFocus();
            return;

        }
        if (address.isEmpty()) {
            input_address.setError("Preencha a sua morada!");
            input_address.requestFocus();
            return;

        }
        if (telephone.isEmpty()) {
            input_telephone.setError("Preencha o seu número de telemóvel!");
            input_telephone.requestFocus();
            return;

        }
        if (bi.isEmpty()) {
            input_bi.setError("Preencha o campo!");
            input_bi.requestFocus();
            return;

        }
        if (houseType.isEmpty()) {
            input_houseType.setError("Preencha o campo!");
            input_houseType.requestFocus();
            return;

        }
        if (hasOtherAnimals.isEmpty()) {
            input_hasOtherAnimals.setError("Preencha o campo!");
            input_hasOtherAnimals.requestFocus();
            return;

        }
        if (adoptionReasons.isEmpty()) {
            input_adoptionReasons.setError("Preencha o motivo da sua adoção!");
            input_adoptionReasons.requestFocus();
            return;
        }

        progressBar_saveAdoption.setVisibility(View.VISIBLE);

        saveNewAdoptionFirebase(name, age, profession, hasChildren, ChildrensAge, address, telephone, bi,houseType, hasOtherAnimals, otherAnimals, adoptionReasons);
    }


    /**
     * * method that saves data on firebase.
     * @param name
     * @param age
     * @param profession
     * @param hasChildren
     * @param ChildrensAge
     * @param address
     * @param telephone
     * @param bi
     * @param houseType
     * @param hasOtherAnimals
     * @param otherAnimals
     * @param adoptionReasons
     * Req. 6 - Fontes de dados remota
     */
    private void saveNewAdoptionFirebase(String name, String age, String profession, String hasChildren, String ChildrensAge, String address, String telephone, String bi, String houseType, String hasOtherAnimals, String otherAnimals, String adoptionReasons) {

        String id_user = mAuth.getCurrentUser().getUid();
        Map<String, Object> adoption = new HashMap<>();
        adoption.put("id_utilizador", id_user);
        adoption.put("id_animal", animal_ID);
        adoption.put("nome", name);
        adoption.put("idade", age);
        adoption.put("profissoa", profession);
        adoption.put("temCriancas", hasChildren);
        adoption.put("idadeCriancas", ChildrensAge);
        adoption.put("morada", address);
        adoption.put("telepone", telephone);
        adoption.put("bilheteIdentidade", bi);
        adoption.put("tipoCasa", houseType);
        adoption.put("temOutrosAnimais", hasOtherAnimals);
        adoption.put("outrosAnimais", otherAnimals);
        adoption.put("motivoAdocao", adoptionReasons);

        mStore.collection("adocoes").add(adoption).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                progressBar_saveAdoption.setVisibility(View.INVISIBLE);

                Toast.makeText(AdoptionQuestionnaireActivity.this, "Pedido de adoção efetuado com sucesso! \n Aguarde o contacto", Toast.LENGTH_LONG).show();

                startActivity(new Intent(AdoptionQuestionnaireActivity.this, MainContainerUserActivity.class));
                finish();
            }
        });
    }


    /**
     * method that generates the current filled page into a file-pdf document
     * and saves it in the phone's file path
     * Req. 5 - BD e ficheiros
     */
    private void generatePDF() {

        String titleT = title.getText().toString();
        String nameT = input_fullname.getText().toString();
        String ageT = input_age.getText().toString();
        String professionT = input_profession.getText().toString();
        String hasChildrenT = input_hasChildren.getText().toString();
        String childrensAgeT = input_ChildrensAge.getText().toString();
        String addressT = input_address.getText().toString();
        String telephoneT = input_telephone.getText().toString();
        String biT = input_bi.getText().toString();
        String houseTypeT = input_houseType.getText().toString();
        String hasOtherAnimalsT = input_hasOtherAnimals.getText().toString();
        String otherAnimalsT = input_otherAnimals.getText().toString();
        String adoptionReasonsT = input_adoptionReasons.getText().toString();

        String path = getExternalFilesDir(null).toString() + "/" + nameT + ".pdf";

        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        try {
            document.add(new Paragraph(titleT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Nome: " + nameT));
            // document.add(new Paragraph(nameT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Idade: " + ageT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Profissão: " + professionT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Tem crianças: " + hasChildrenT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Idade das crianças: " + childrensAgeT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Morada: " + addressT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Telefone: " + telephoneT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Bilhete de Identidade: " + biT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Tipo de casa: " + houseTypeT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Tem outros animais: " + hasOtherAnimalsT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Quais são os outros animais: " + otherAnimalsT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Motivo de adoção: " + adoptionReasonsT));


        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "FICHEIRO GERADO COM SUCESSO!", Toast.LENGTH_SHORT).show();
        document.close();
    }

}

