package com.example.cantinhodosanimais;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class AddAnimalActivity extends Fragment {

    View v;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return v = inflater.inflate(R.layout.activity_add_animal, container, false);


    }

}