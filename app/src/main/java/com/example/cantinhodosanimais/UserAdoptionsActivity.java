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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UserAdoptionsActivity extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private FirebaseFirestore mStore;
    private ArrayList<Animals> animalsList;
    private AdoptionAdapterUser adoptionAdapterUser;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        animalsList = new ArrayList<>();

        loadDataFromFirebase(new FireStoreCallback() {
            @Override
            public void onCallBack(Animals animalsObj) {
                animalsList.add(animalsObj);

                recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_user_adoptions);
                adoptionAdapterUser = new AdoptionAdapterUser(UserAdoptionsActivity.this, animalsList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adoptionAdapterUser);
            }
        });
    }

    private interface FireStoreCallback {
        void onCallBack(Animals animalsObj);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_user_adoptions, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_user_adoptions);
        adoptionAdapterUser = new AdoptionAdapterUser(UserAdoptionsActivity.this, animalsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adoptionAdapterUser);
        return v;
    }


    private void loadDataFromFirebase(UserAdoptionsActivity.FireStoreCallback fireStoreCallback) {
        if (animalsList.size() > 0)
            animalsList.clear();

        mStore.collection("adocoes").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if (document.getString("id_utilizador").equals(mAuth.getCurrentUser().getUid())) {

                                    String animal_ID = document.getString("id_animal");

                                    mStore.collection("animais")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                                            if (documentSnapshot.getId().equals(animal_ID)) {
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
                                                                          //  Log.v("EXISTEM " + animalsList.size(), " ANIMAIS");
                                                                        });
                                                            }
                                                        }

                                                    }
                                                }
                                            });


                                }
                            }
                        }
                    }
                });

        /*mStore.collection("animais")
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
        });*/
    }
}