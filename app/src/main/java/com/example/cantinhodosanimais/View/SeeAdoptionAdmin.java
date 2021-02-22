package com.example.cantinhodosanimais.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cantinhodosanimais.Model.Adoptions;
import com.example.cantinhodosanimais.Model.GeoLocation;
import com.example.cantinhodosanimais.R;
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


/**
 * This class allows for the administrator too see all the adoptions made
 * by the users. Having full access to the user´s information.
 */
public class SeeAdoptionAdmin extends FragmentActivity implements OnMapReadyCallback { //CHECK IF IT WORKS W/O "extends Fragment"

    private String adoption_ID;
    private double latitude, longitude;
    private GoogleMap map; //MAP
    private TextView tv_fullname, tv_ID_Animal, tv_age, tv_profession, tv_hasChildren, tv_ChildrensAge, tv_address, tv_telephone, tv_bi, tv_houseType, tv_hasOtherAnimals, tv_otherAnimals, tv_adoptionreasons;
    private ArrayList<Adoptions> adoptionsList;
    private FirebaseFirestore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_adoption_admin);

        Bundle bundle = getIntent().getExtras();
        adoption_ID = bundle.getString("adoption_ID");

        adoptionsList = new ArrayList<>();
        mStore = FirebaseFirestore.getInstance();
        tv_fullname = findViewById(R.id.tv_adopter_name_adocao);
        tv_age = findViewById(R.id.tv_adopter_age_adoption);
        tv_profession = findViewById(R.id.tv_adopter_profession_adoption);
        tv_hasChildren = findViewById(R.id.tv_adopter_hasChildren_adoption);
        tv_ChildrensAge = findViewById(R.id.tv_adopter_childrensAge_adoption);
        tv_address = findViewById(R.id.tv_adopter_address_adoption);
        tv_telephone = findViewById(R.id.tv_adopter_telephone_adoption);
        tv_bi = findViewById(R.id.tv_adopter_BI_adoption);
        tv_houseType = findViewById(R.id.tv_adopter_houseType_adoption);
        tv_hasOtherAnimals = findViewById(R.id.tv_adopter_hasOtherAnimals_adoption);
        tv_otherAnimals = findViewById(R.id.tv_adopter_otherAnimals_adoption);
        tv_adoptionreasons = findViewById(R.id.tv_adopter_adoptionReasons_adoption);
        tv_ID_Animal = findViewById(R.id.tv_ID_animal_adoption);



        /**
         * Fills the RecyclerView with data saved in the adoptions's list
         * Req. 2 - Interface com utilizadores
         */
        loadAdoptionData(new FireStoreCallback() {

            @Override
            public void onCallBack(Adoptions adoptionsObj) {
                adoptionsList.add(adoptionsObj);

                for (int i = 0; i < adoptionsList.size(); i++) {
                    tv_fullname.setText(String.valueOf(adoptionsList.get(i).getFullname()));
                    tv_ID_Animal.setText(String.valueOf(adoptionsList.get(i).getID_animal()));
                    tv_age.setText(String.valueOf(adoptionsList.get(i).getAge()));
                    tv_profession.setText(String.valueOf(adoptionsList.get(i).getProfession()));
                    tv_hasChildren.setText(String.valueOf(adoptionsList.get(i).getHasChildren()));
                    tv_ChildrensAge.setText(String.valueOf(adoptionsList.get(i).getChildrensAge()));
                    tv_address.setText(String.valueOf(adoptionsList.get(i).getAddress()));
                    tv_telephone.setText(String.valueOf(adoptionsList.get(i).getTelephone()));
                    tv_bi.setText(String.valueOf(adoptionsList.get(i).getBi()));
                    tv_houseType.setText(String.valueOf(adoptionsList.get(i).getHouseType()));
                    tv_otherAnimals.setText(String.valueOf(adoptionsList.get(i).getOtherAnimals()));
                    tv_hasOtherAnimals.setText(String.valueOf(adoptionsList.get(i).getHasOtherAnimals()));
                    tv_adoptionreasons.setText(String.valueOf(adoptionsList.get(i).getAdoptionReasons()));

                    /**
                     * This allows the address conversion to latitude and longitude, using
                     * the address given by the user
                     * Req. 3 - Mapa
                     */
                    GeoLocation geoLocation = new GeoLocation(latitude, longitude);
                    geoLocation.getAddress(tv_address.getText().toString().trim(), getApplicationContext());

                    latitude = geoLocation.getLatitude();
                    longitude = geoLocation.getLongitude();
                    Log.i("COORDS ", "latitude "+latitude+ " longitude "+longitude);

                  Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
                    try {
                        List<Address> addresses =
                        geocoder.getFromLocationName(tv_address.getText().toString(),1);
                        if(addresses.size() > 0){
                            Address a =addresses.get(0);
                            latitude = a.getLatitude();
                            longitude = a.getLongitude();
                         //   Log.i("COORDS ", "latitude "+latitude+ " longitude "+longitude+ "enderesso "+addresses.size()+ "LAT "+a.getLatitude()+ "LONG "+a.getLongitude());

                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }

        });

        /**
         * To make the map catch the longitude and latitude
         * and show it on each adoption (on the administrator's adoptions page).
         */
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        //MAP
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this); //Finish Map
    }



    /**
     * Interface used to implement onCallBack method
     */
    public interface FireStoreCallback {
        void onCallBack(Adoptions adoptionsObj);
    }

    /**
     * This method fetches data from adoptions (collection) in database and adds in the adoptions list
     * @param fireStoreCallback
     * Req. 6 - Fonte de dados remota
     */
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


    /**
     * This method places the placepicker on the received coordinates (adopter's address)
     * @param googleMap
     * Req. 3 - Mapa
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng adopterAdress = new LatLng(latitude, longitude);
        //Toast.makeText(this, "latitude "+latitude+ "longitude "+longitude, Toast.LENGTH_LONG).show();
       Log.i("COORDS ", "latitude "+latitude+ " longitude "+longitude);

        map.addMarker(new MarkerOptions().position(adopterAdress).title(tv_address.getText().toString()));
        map.moveCamera(CameraUpdateFactory.newLatLng(adopterAdress));

    }


}