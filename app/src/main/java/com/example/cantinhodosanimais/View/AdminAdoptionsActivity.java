package com.example.cantinhodosanimais.View;

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

import com.example.cantinhodosanimais.Model.AdoptionAdapterAdmin;
import com.example.cantinhodosanimais.Model.Adoptions;
import com.example.cantinhodosanimais.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * Here, the administrator can see all the adoptions made by the users overall.
 */
public class AdminAdoptionsActivity extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private FirebaseFirestore mStore;
    private ArrayList<Adoptions> adoptionsList;
    private AdoptionAdapterAdmin adoptionAdapterAdmin;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStore = FirebaseFirestore.getInstance();
        adoptionsList = new ArrayList<>();


        /**
         * Fills the RecyclerView with data saved in the adoption's list
         * Req. 2 - Interface com utilizadores
         */
        loadDataFromFirebase(new FireStoreCallback() {
            @Override
            public void onCallBack(Adoptions adoptionsObj) {
                adoptionsList.add(adoptionsObj);

                recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_admin_adoptions);
                adoptionAdapterAdmin = new AdoptionAdapterAdmin(AdminAdoptionsActivity.this, adoptionsList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adoptionAdapterAdmin);
            }
        });
    }

    /**
     * Interface used to implement onCallBack method
     */
    private interface FireStoreCallback {
        void onCallBack(Adoptions adoptionsObj);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_admin_adoptions, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_admin_adoptions);
        adoptionAdapterAdmin = new AdoptionAdapterAdmin(AdminAdoptionsActivity.this, adoptionsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adoptionAdapterAdmin);
        return v;

    }

    /**
     * This method fetches data from adoptions (collection) in database and adds in the adoptions list
     * Req. 6 - Fontes de dados remota
     * @param fireStoreCallback
     */
    private void loadDataFromFirebase(AdminAdoptionsActivity.FireStoreCallback fireStoreCallback) {
        if (adoptionsList.size() > 0)
            adoptionsList.clear();

        mStore.collection("adocoes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
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
                                document.getString("motivoAdocao")
                                );

                        fireStoreCallback.onCallBack(adoptions);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getActivity(), "Problema ao carregar a lista dos animais", Toast.LENGTH_LONG).show();
                Log.v("FALHA AO CARREGAR LISTA", e.getMessage());
            }
        });
    }
}