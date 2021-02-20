package com.example.cantinhodosanimais;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AnimalsAdapterAdmin extends RecyclerView.Adapter<AnimalsAdapterAdmin.ViewHolder> {

    private ArrayList<Animals> animalsList;
    MainAdminActivity mainAdminActivity;
    MainAdminActivity.FireStoreCallback fireStoreCallback = new MainAdminActivity.FireStoreCallback() {
        @Override
        public void onCallBack(Animals animalsObj) {

        }
    };

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

        holder.imageView_animal_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAnimal(position);
            }
        });

    }

    private void deleteAnimal(int position) {
        mainAdminActivity.mStore.collection("animais").document(animalsList.get(position).getAnimal_id()).
                delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mainAdminActivity.getContext(), "Animal excluido com sucesso!", Toast.LENGTH_LONG).show();

                    }
                });
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
        ImageView imageView_animal_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_animal_nome = itemView.findViewById(R.id.tv_animal_name_admin);
            textView_animal_idade = itemView.findViewById(R.id.tv_animal_age_admin);
            textView_animal_raca = itemView.findViewById(R.id.tv_animal_raca_admin);
            imageView_animal_foto = itemView.findViewById(R.id.im_animal_photo_admin);
            imageView_animal_delete = itemView.findViewById(R.id.img_delete_animal);
        }
    }
}
