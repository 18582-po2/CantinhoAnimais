package com.example.cantinhodosanimais;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainUserActivity extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private FirebaseFirestore mStore;
    private ArrayList<Animals> animalsList;
    private AnimalsAdapterUser animalsAdapterUser;
    private StorageReference mStorage;

    /**
     * para carregar os dados antes da view/recyclerView ser iniciada
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        animalsList = new ArrayList<>();

        loadDataFromFirebase(new FireStoreCallback() {
            @Override
            public void onCallBack(Animals animalsObj) {
                animalsList.add(animalsObj);

                recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_animals_to_adopt_user);
                animalsAdapterUser = new AnimalsAdapterUser(MainUserActivity.this, animalsList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(animalsAdapterUser);
            }
        });
    }

    private interface FireStoreCallback {
        void onCallBack(Animals animalsObj);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_main_user, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_animals_to_adopt_user);
        animalsAdapterUser = new AnimalsAdapterUser(MainUserActivity.this, animalsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(animalsAdapterUser);
        return v;
    }

    private void loadDataFromFirebase(MainUserActivity.FireStoreCallback fireStoreCallback) {
        if (animalsList.size() > 0)
            animalsList.clear();

        mStore.collection("animais")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                mStorage.child("animalsImages/" + documentSnapshot.getString("imgURI")).getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            Animals animals = new Animals(
                                                    documentSnapshot.getId(),
                                                    documentSnapshot.getString("genero"),
                                                    documentSnapshot.getString("deficiencia"),
                                                    documentSnapshot.getString("personalidade"),
                                                    documentSnapshot.getString("nome"),
                                                    documentSnapshot.getString("idade"),
                                                    documentSnapshot.getString("raca"),
                                                    documentSnapshot.getString("historia"),
                                                    uri.toString());

                                            //Log.v("CAMINHO DAS IMAGENS: " + uri.toString(), "");

                                            fireStoreCallback.onCallBack(animals);
                                            Log.v("EXISTEM " + animalsList.size(), " ANIMAIS");
                                        });
                            }

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getActivity(), "Problema ao carregar a lista dos animais", Toast.LENGTH_LONG).show();
                Log.v("FALHA A CARREGAR LISTA", e.getMessage());
            }
        });
    }
}