package com.example.cantinhodosanimais;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

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

public class AdoptionQuestionnaireActivity extends AppCompatActivity implements View.OnClickListener {

    private String animal_ID;
    private FirebaseFirestore mStore;
    private ProgressBar progressBar_saveAdoption;

    //FOR PDF
    private TextView title;  //COME BACK
    private Button btn_save_pfd, btn_finalizar_pedido;
    private TextInputEditText input_fullname, input_idade, input_profissao, input_temCriancas, input_idadeCriancas, input_morada, input_telefone, input_bilheteIdentidade, input_tipoCasa, input_temOutrosAnimais, input_outrosAnimais, input_motivoAdocao;
    //FOR MAPS
    String latitude;
    String longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_questionnaire);

        Bundle bundle = getIntent().getExtras();
        animal_ID = bundle.getString("animal_ID");

        mStore = FirebaseFirestore.getInstance();

        //PDF---------
        //imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.textView3);
        btn_save_pfd = findViewById(R.id.btn_save_pfd);
        btn_finalizar_pedido = findViewById(R.id.btn_finalizar_pedido);
        input_fullname = findViewById(R.id.input_fullname);
        input_idade = findViewById(R.id.input_idade);
        input_profissao = findViewById(R.id.input_profissao);
        input_temCriancas = findViewById(R.id.input_temCriancas);
        input_idadeCriancas = findViewById(R.id.input_idadeCriancas);
        input_morada = findViewById(R.id.input_morada);
        input_telefone = findViewById(R.id.input_telefone);
        input_bilheteIdentidade = findViewById(R.id.input_bilheteIdentidade);
        input_tipoCasa = findViewById(R.id.input_tipoCasa);
        input_temOutrosAnimais = findViewById(R.id.input_temOutrosAnimais);
        input_outrosAnimais = findViewById(R.id.input_outrosAnimais);
        input_motivoAdocao = findViewById(R.id.input_motivoAdocao);

        progressBar_saveAdoption = findViewById(R.id.progressBar_saveAdoption);

        btn_finalizar_pedido.setOnClickListener(this);
        btn_save_pfd.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save_pfd) {
            generatePDF();
        }
        if (v.getId() == R.id.btn_finalizar_pedido) {

            createNewAdoption();
        }
    }

    private void createNewAdoption() {

        String titleT= title.getText().toString();
        String name = input_fullname.getText().toString();
        String idade = input_idade.getText().toString();
        String profissao = input_profissao.getText().toString();
        String temCriancas = input_temCriancas.getText().toString();
        String idadeCriancas = input_idadeCriancas.getText().toString();
        String morada = input_morada.getText().toString();
        String telefone = input_telefone.getText().toString();
        String bilheteIdentidade = input_bilheteIdentidade.getText().toString();
        String tipoCasa = input_tipoCasa.getText().toString();
        String temOutrosAnimais = input_temOutrosAnimais.getText().toString();
        String outrosAnimais = input_outrosAnimais.getText().toString();
        String motivoAdocao = input_motivoAdocao.getText().toString();


        if (name.isEmpty()) {
            input_fullname.setError("Preencha o seu  nome!");
            input_fullname.requestFocus();
            return;

        }
        if (idade.isEmpty()) {
            input_idade.setError("Preencha a sua idade!");
            input_idade.requestFocus();
            return;

        }
        if (profissao.isEmpty()) {
            input_profissao.setError("Preencha a sua profissão!");
            input_profissao.requestFocus();
            return;

        }
        if (temCriancas.isEmpty()) {
            input_temCriancas.setError("Preencha o campo!");
            input_temCriancas.requestFocus();
            return;

        }
        if (morada.isEmpty()) {
            input_morada.setError("Preencha a sua morada!");
            input_morada.requestFocus();
            return;

        }
        if (telefone.isEmpty()) {
            input_telefone.setError("Preencha o seu número de telemóvel!");
            input_telefone.requestFocus();
            return;

        }
        if (bilheteIdentidade.isEmpty()) {
            input_bilheteIdentidade.setError("Preencha o campo!");
            input_bilheteIdentidade.requestFocus();
            return;

        }
        if (tipoCasa.isEmpty()) {
            input_tipoCasa.setError("Preencha o campo!");
            input_tipoCasa.requestFocus();
            return;

        }
        if (temOutrosAnimais.isEmpty()) {
            input_temOutrosAnimais.setError("Preencha o campo!");
            input_temOutrosAnimais.requestFocus();
            return;

        }
        if (motivoAdocao.isEmpty()) {
            input_motivoAdocao.setError("Preencha o motivo da sua adoção!");
            input_motivoAdocao.requestFocus();
            return;
        }

        onPause(morada);
        onResume(name, idade, profissao, temCriancas, idadeCriancas, morada, telefone, bilheteIdentidade,tipoCasa, temOutrosAnimais, outrosAnimais, motivoAdocao);
       // saveNewAdoptionFirebase(name, idade, profissao, temCriancas, idadeCriancas, morada, telefone, bilheteIdentidade,tipoCasa, temOutrosAnimais, outrosAnimais, motivoAdocao);
    }

    private void saveNewAdoptionFirebase(String name, String idade, String profissao, String temCriancas, String idadeCriancas, String morada, String telefone, String bilheteIdentidade, String tipoCasa, String temOutrosAnimais, String outrosAnimais, String motivoAdocao) {

        Map<String, Object> adoption = new HashMap<>();
        adoption.put("id_animal", animal_ID);
        adoption.put("nome", name);
        adoption.put("idade", idade);
        adoption.put("profissao", profissao);
        adoption.put("temCriancas", temCriancas);
        adoption.put("idadeCriancas", idadeCriancas);
        adoption.put("morada", morada);
        adoption.put("telefone", telefone);
        adoption.put("bilheteIdentidade", bilheteIdentidade);
        adoption.put("tipoCasa", tipoCasa);
        adoption.put("temOutrosAnimais", temOutrosAnimais);
        adoption.put("outrosAnimais", outrosAnimais);
        adoption.put("motivoAdocao", motivoAdocao);
        adoption.put("latitude", latitude);
        adoption.put("longitude", longitude);


        mStore.collection("adocoes").add(adoption).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                progressBar_saveAdoption.setVisibility(View.INVISIBLE);

                Toast.makeText(AdoptionQuestionnaireActivity.this, "Pedido de adoção efetuado com sucesso! \n Aguarde o contacto", Toast.LENGTH_LONG).show();

                startActivity(new Intent(AdoptionQuestionnaireActivity.this, UserAdoptionsActivity.class));
                finish();
            }
        });
    }

    private void createLatLong(String morada) {
        GeoLocation geoLocation = new GeoLocation();
        geoLocation.getAddress(morada,getApplicationContext(),new GeoHandler());
    }

    private class GeoHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            latitude = bundle.getString("latitude");
            longitude = bundle.getString("longitude");

           // Toast.makeText(AdoptionQuestionnaireActivity.this, "latitude "+latitude+ " longitude "+longitude, Toast.LENGTH_SHORT).show();
        }
    }

    private void generatePDF() {

        String titleT = title.getText().toString();
        String nameT = input_fullname.getText().toString();
        String idadeT = input_idade.getText().toString();
        String profissaoT = input_profissao.getText().toString();
        String temCriancasT = input_temCriancas.getText().toString();
        String idadeCriancasT = input_idadeCriancas.getText().toString();
        String moradaT = input_morada.getText().toString();
        String telefoneT = input_telefone.getText().toString();
        String bilheteIdentidadeT = input_bilheteIdentidade.getText().toString();
        String tipoCasaT = input_tipoCasa.getText().toString();
        String temOutrosAnimaisT = input_temOutrosAnimais.getText().toString();
        String outrosAnimaisT = input_outrosAnimais.getText().toString();
        String motivoAdocaoT = input_motivoAdocao.getText().toString();

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
            document.add(new Paragraph("Idade: " + idadeT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Profissão: " + profissaoT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Tem crianças: " + temCriancasT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Idade das crianças: " + idadeCriancasT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Morada: " + moradaT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Telefone: " + telefoneT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Bilhete de Identidade: " + bilheteIdentidadeT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Tipo de casa: " + tipoCasaT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Tem outros animais: " + temOutrosAnimaisT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Quais são os outros animais: " + outrosAnimaisT));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Motivo de adoção: " + motivoAdocaoT));


        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "FICHEIRO GERADO COM SUCESSO!", Toast.LENGTH_SHORT).show();
        document.close();
    }

    //@Override
    protected void onPause(String morada) {
        super.onPause();
        createLatLong(morada);
    }

    //@Override
    protected void onResume(String name, String idade, String profissao, String temCriancas, String idadeCriancas, String morada, String telefone, String bilheteIdentidade, String tipoCasa, String temOutrosAnimais, String outrosAnimais, String motivoAdocao) {
        super.onResume();
        saveNewAdoptionFirebase(name, idade, profissao, temCriancas, idadeCriancas, morada, telefone, bilheteIdentidade,tipoCasa, temOutrosAnimais, outrosAnimais, motivoAdocao);

    }
}

