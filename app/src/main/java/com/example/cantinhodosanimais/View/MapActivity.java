package com.example.cantinhodosanimais.View;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.cantinhodosanimais.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This class creates the map used in the About's page
 * to show the organization's location
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("estado") != null) {
                Toast.makeText(getApplicationContext(), "estado: " + bundle.getString("estado"), Toast.LENGTH_SHORT).show();

            }
        }
    }

    /**
     * This method pins the placepicker in the location inserted of the map
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;

        LatLng cantinhoDosAnimais = new LatLng(38.02819259139145, -7.864677978571929);

        map.addMarker(new MarkerOptions().position(cantinhoDosAnimais).title("CantinhoDosAnimais"));
        map.moveCamera(CameraUpdateFactory.newLatLng(cantinhoDosAnimais));

    }
    }