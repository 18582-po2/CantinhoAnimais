package com.example.cantinhodosanimais;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AnimalsAdapterUser extends RecyclerView.Adapter<AnimalsAdapterUser.ViewHolder> {

    private ArrayList<Animals> animalsList;
    MainUserActivity mainUserActivity;
   // private LayoutInflater layoutInflater;

    public AnimalsAdapterUser(MainUserActivity mainUserActivity, ArrayList<Animals> animalsList) {
        this.animalsList = animalsList;
        this.mainUserActivity = mainUserActivity;
    }

    @NonNull
    @Override
    public AnimalsAdapterUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mainUserActivity.getContext());
        View view = layoutInflater.inflate(R.layout.item_animals_list_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalsAdapterUser.ViewHolder holder, int position) {

        String animal_ID = String.valueOf(animalsList.get(position).getAnimal_id());
        holder.textView_animal_nome.setText(String.valueOf(animalsList.get(position).getAnimal_nome()));
        holder.textView_animal_idade.setText(String.valueOf(animalsList.get(position).getAnimal_idade())+ " ano(s) de idade");
        holder.textView_animal_raca.setText(String.valueOf(animalsList.get(position).getAnimal_raca()));
        Picasso.get().load(animalsList.get(position).getImgURI()).into(holder.imageView_animal_foto);
        holder.btn_see_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SeeAnimalActivity.class);
                intent.putExtra("animal_ID", animal_ID);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btn_see_animal;
        TextView textView_animal_nome;
        TextView textView_animal_idade;
        TextView textView_animal_raca;
        ImageView imageView_animal_foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_see_animal = itemView.findViewById(R.id.btn_see_animal_user);

            textView_animal_nome = itemView.findViewById(R.id.tv_animal_name_user);
            textView_animal_idade = itemView.findViewById(R.id.tv_animal_age_user);
            textView_animal_raca = itemView.findViewById(R.id.tv_animal_raca_user);
            imageView_animal_foto = itemView.findViewById(R.id.im_animal_photo_user);
        }
    }
}
