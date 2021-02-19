package com.example.cantinhodosanimais;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdoptionAdapterAdmin extends RecyclerView.Adapter<AdoptionAdapterAdmin.ViewHolder> {

    private ArrayList<Adoptions> adoptionsList;
    AdminAdoptionsActivity adminAdoptionsActivity;

    public AdoptionAdapterAdmin(AdminAdoptionsActivity adminAdoptionsActivity, ArrayList<Adoptions> adoptionsList) {
        this.adoptionsList = adoptionsList;
        this.adminAdoptionsActivity = adminAdoptionsActivity;
    }

    @NonNull
    @Override
    public AdoptionAdapterAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(adminAdoptionsActivity.getContext());
        View view = layoutInflater.inflate(R.layout.item_adoptions_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String adoption_ID = String.valueOf(adoptionsList.get(position).getAdoption_ID());
        holder.textView_adocao_ID.setText(String.valueOf(adoptionsList.get(position).getAdoption_ID()));
        holder.textView_adotante_nome.setText(String.valueOf(adoptionsList.get(position).getFullname()));
        holder.textView_telefone.setText(String.valueOf(adoptionsList.get(position).getTelefone()));
        holder.btn_see_adoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SeeAdoptionAdmin.class);
                intent.putExtra("adoption_ID", adoption_ID );
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adoptionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_adocao_ID;
        TextView textView_adotante_nome;
        TextView textView_telefone;
        Button btn_see_adoption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

             textView_adocao_ID = itemView.findViewById(R.id.tv_adoption_id_main);
             textView_adotante_nome = itemView.findViewById(R.id.tv_adopter_name_admin);
             textView_telefone = itemView.findViewById(R.id.tv_telefone_adotante);
             btn_see_adoption = itemView.findViewById(R.id.btn_see_adoption_admin);
        }
    }
}
