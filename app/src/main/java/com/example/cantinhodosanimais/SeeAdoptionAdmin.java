package com.example.cantinhodosanimais;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SeeAdoptionAdmin extends AppCompatActivity implements OnMapReadyCallback { //CHECK IF IT WORKS W/O "extends Fragment"

    private String adoption_ID;
    private GoogleMap map; //MAP

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_adoption_admin);

        //MAP
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //Finish Map

        Bundle bundle = getIntent().getExtras();
        adoption_ID = bundle.getString("adoption_ID");
    }


    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;

        //CHANGE THIS TO ADOPTERS ADDRESS!!!!!!!!!!!!!!!!!!!!!!!!!
        LatLng cantinhoDosAnimais = new LatLng(38.02819259139145, -7.864677978571929);

        map.addMarker(new MarkerOptions().position(cantinhoDosAnimais).title("CantinhoDosAnimais"));
        map.moveCamera(CameraUpdateFactory.newLatLng(cantinhoDosAnimais));

    }
}