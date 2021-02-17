package com.example.cantinhodosanimais;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
            if (bundle.getString("some") != null) {
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("some"), Toast.LENGTH_SHORT).show();

            }
        }




    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;

        LatLng cantinhoDosAnimais = new LatLng(38.02819259139145, -7.864677978571929);

       /* CameraUpdate camera = CameraUpdateFactory.newLatLng(Maharashtra);
        googleMap.animateCamera(camera);*/

        map.addMarker(new MarkerOptions().position(cantinhoDosAnimais).title("CantinhoDosAnimais"));
        map.moveCamera(CameraUpdateFactory.newLatLng(cantinhoDosAnimais));

    }

   /* public static void start(Context context) {
        Intent starter = new Intent(context, MapActivity.class);
        context.startActivity(starter);
    }*/

    }