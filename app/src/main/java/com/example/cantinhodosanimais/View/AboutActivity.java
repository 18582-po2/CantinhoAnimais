package com.example.cantinhodosanimais.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cantinhodosanimais.R;

/**
 * This class portraits all the information about the organization
 * "Cantinho dos animais" and shows all their contacts.
 * It also allows the user to see their location on the map.
 */
public class AboutActivity extends Fragment {
    private Button btn_map;
    View v;

    public AboutActivity() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_about, container, false);

        btn_map = v.findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), MapActivity.class);
                in.putExtra("estado", "Mapa Aberto");
                startActivity(in);
            }
        });
        return v;
    }



}