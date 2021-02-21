package com.example.cantinhodosanimais;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SeeAdoptionAdmin extends FragmentActivity implements OnMapReadyCallback { //CHECK IF IT WORKS W/O "extends Fragment"

    private String adoption_ID;
    private double latitude, longitude;
    private GoogleMap map; //MAP
    private TextView tv_fullname, tv_ID_Animal, tv_idade, tv_profissao, tv_temCriancas, tv_idadeCriancas, tv_morada, tv_telefone, tv_bilheteIdentidade, tv_tipoCasa, tv_temOutrosAnimais, tv_outrosAnimais, tv_motivoAdocao;
    private ArrayList<Adoptions> adoptionsList;
    private FirebaseFirestore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_adoption_admin);

        //MAP
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this); //Finish Map

        Bundle bundle = getIntent().getExtras();
        adoption_ID = bundle.getString("adoption_ID");

        adoptionsList = new ArrayList<>();
        mStore = FirebaseFirestore.getInstance();
        tv_fullname = findViewById(R.id.tv_adopter_name_adocao);
        tv_idade = findViewById(R.id.tv_adopter_idade_adocao);
        tv_profissao = findViewById(R.id.tv_adopter_profissao_adocao);
        tv_temCriancas = findViewById(R.id.tv_adopter_temCriancas_adocao);
        tv_idadeCriancas = findViewById(R.id.tv_adopter_idadeCriancas_adocao);
        tv_morada = findViewById(R.id.tv_adopter_morada_adocao);
        tv_telefone = findViewById(R.id.tv_adopter_telefone_adocao);
        tv_bilheteIdentidade = findViewById(R.id.tv_adopter_BI_adocao);
        tv_tipoCasa = findViewById(R.id.tv_adopter_tipoCasa_adocao);
        tv_temOutrosAnimais = findViewById(R.id.tv_adopter_temOutrosAnimais_adocao);
        tv_outrosAnimais = findViewById(R.id.tv_adopter_outrosAnimais_adocao);
        tv_motivoAdocao = findViewById(R.id.tv_adopter_motivoAdocao_adocao);
        tv_ID_Animal = findViewById(R.id.tv_ID_animal_adocao);


        loadAdoptionData(new SeeAdoptionAdmin.FireStoreCallback() {

            @Override
            public void onCallBack(Adoptions adoptionsObj) {
                adoptionsList.add(adoptionsObj);

                for (int i = 0; i < adoptionsList.size(); i++) {
                    tv_fullname.setText(String.valueOf(adoptionsList.get(i).getFullname()));
                    tv_ID_Animal.setText(String.valueOf(adoptionsList.get(i).getID_animal()));
                    tv_idade.setText(String.valueOf(adoptionsList.get(i).getIdade()));
                    tv_profissao.setText(String.valueOf(adoptionsList.get(i).getProfissao()));
                    tv_temCriancas.setText(String.valueOf(adoptionsList.get(i).getTemCriancas()));
                    tv_idadeCriancas.setText(String.valueOf(adoptionsList.get(i).getIdadeCriancas()));
                    tv_morada.setText(String.valueOf(adoptionsList.get(i).getMorada()));
                    tv_telefone.setText(String.valueOf(adoptionsList.get(i).getTelefone()));
                    tv_bilheteIdentidade.setText(String.valueOf(adoptionsList.get(i).getBilheteIdentidade()));
                    tv_tipoCasa.setText(String.valueOf(adoptionsList.get(i).getTipoCasa()));
                    tv_outrosAnimais.setText(String.valueOf(adoptionsList.get(i).getOutrosAnimais()));
                    tv_temOutrosAnimais.setText(String.valueOf(adoptionsList.get(i).getTemOutrosAnimais()));
                    tv_motivoAdocao.setText(String.valueOf(adoptionsList.get(i).getMotivoAdocao()));

                }

                    GeoLocation geoLocation = new GeoLocation(latitude, longitude);
                    geoLocation.getAddress(tv_morada.getText().toString().trim(), getApplicationContext());

                    latitude = geoLocation.getLatitude();
                    longitude = geoLocation.getLongitude();
                    Log.i("COORDS ", "latitude "+latitude+ " longitude "+longitude);

            }

        });
    }


        private interface FireStoreCallback {
            void onCallBack(Adoptions adoptionsObj);
        }

    private void loadAdoptionData(SeeAdoptionAdmin.FireStoreCallback fireStoreCallback) {

        mStore.collection("adocoes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equals(adoption_ID)) {
                                    Adoptions adoptions = new Adoptions(
                                            document.getString("id_animal"),
                                            document.getId(),
                                            document.getString("nome"),
                                            document.getString("idade"),
                                            document.getString("profissao"),
                                            document.getString("temCriancas"),
                                            document.getString("idadeCriancas"),
                                            document.getString("morada"),
                                            document.getString("telefone"),
                                            document.getString("bilheteIdentidade"),
                                            document.getString("tipoCasa"),
                                            document.getString("temOutrosAnimais"),
                                            document.getString("outrosAnimais"),
                                            document.getString("motivoAdocao"));
                                    fireStoreCallback.onCallBack(adoptions);
                                }
                            }
                        }
                    }
                }).addOnFailureListener(exception -> {
            Toast.makeText(this, "Falha ao carregar os dados da adocao!", Toast.LENGTH_SHORT).show();
        });
    }

    public LatLng getCoordsFromAddress(String address) {
        Geocoder geocoder = new Geocoder(SeeAdoptionAdmin.this);
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocationName(address, 1);
            if (addressList != null) {
                Address singleAddress = addressList.get(0);
                LatLng latLng = new LatLng(singleAddress.getLatitude(), singleAddress.getLongitude());
                return latLng;
            } else return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        //CHANGE THIS TO ADOPTERS ADDRESS!!!!!!!!!!!!!!!!!!!!!!!!!
        LatLng adopterAdress = new LatLng(latitude,longitude);
        //Toast.makeText(this, "latitude "+latitude+ "longitude "+longitude, Toast.LENGTH_LONG).show();
        Log.i("COORDS ", "latitude " + latitude + " longitude " + longitude);

        map.addMarker(new MarkerOptions().position(adopterAdress).title(tv_morada.getText().toString()));
        map.moveCamera(CameraUpdateFactory.newLatLng(adopterAdress));

    }


}