package com.example.cantinhodosanimais;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AnimalsAdapterAdmin extends RecyclerView.Adapter<AnimalsAdapterAdmin.ViewHolder> {

    private ArrayList<Animals> animalsList;
    MainAdminActivity mainAdminActivity;

    public AnimalsAdapterAdmin( MainAdminActivity mainAdminActivity,ArrayList<Animals> animalsList) {
        this.animalsList = animalsList;
        this.mainAdminActivity = mainAdminActivity;
    }

    @NonNull
    @Override
    public AnimalsAdapterAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mainAdminActivity.getContext());
        View view = layoutInflater.inflate(R.layout.item_animals_list_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView_animal_nome.setText(String.valueOf(animalsList.get(position).getAnimal_nome()));
        holder.textView_animal_idade.setText(String.valueOf(animalsList.get(position).getAnimal_idade())+ " ano(s) de idade");
        holder.textView_animal_raca.setText(String.valueOf(animalsList.get(position).getAnimal_raca()));
        Picasso.get().load(animalsList.get(position).getImgURI()).into(holder.imageView_animal_foto);

    }

    @Override
    public int getItemCount() {
        return animalsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_animal_id;
        TextView textView_animal_nome;
        TextView textView_animal_idade;
        TextView textView_animal_raca;
        ImageView imageView_animal_foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_animal_nome = itemView.findViewById(R.id.tv_animal_name_admin);
            textView_animal_idade = itemView.findViewById(R.id.tv_animal_age_admin);
            textView_animal_raca = itemView.findViewById(R.id.tv_animal_raca_admin);
            imageView_animal_foto = itemView.findViewById(R.id.im_animal_photo_admin);
        }
    }
}
