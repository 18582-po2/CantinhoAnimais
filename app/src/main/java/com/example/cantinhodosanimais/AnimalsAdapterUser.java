package com.example.cantinhodosanimais;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        //String imgUri = animalsList.get(position).getImgURI();
        //Drawable image = Drawable.createFromPath(imgUri);


        //Uri imageUri = Uri.parse(String.valueOf(animalsList.get(position).getImgURI()));



        holder.textView_animal_nome.setText(String.valueOf(animalsList.get(position).getAnimal_nome()));
        holder.textView_animal_idade.setText(String.valueOf(animalsList.get(position).getAnimal_idade())+ " anos de idade");
        holder.textView_animal_raca.setText(String.valueOf(animalsList.get(position).getAnimal_raca()));
        //holder.imageView_animal_foto.setImageDrawable(image);
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

            textView_animal_nome = itemView.findViewById(R.id.tv_animal_name_user);
            textView_animal_idade = itemView.findViewById(R.id.tv_animal_age_user);
            textView_animal_raca = itemView.findViewById(R.id.tv_animal_raca_user);
            imageView_animal_foto = itemView.findViewById(R.id.im_animal_photo_user);
        }
    }
}
