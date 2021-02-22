package com.example.cantinhodosanimais.Model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cantinhodosanimais.View.MainAdminActivity;
import com.example.cantinhodosanimais.View.OnlySeeAnimalActivity;
import com.example.cantinhodosanimais.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AnimalsAdapterAdmin extends RecyclerView.Adapter<AnimalsAdapterAdmin.ViewHolder> {

    private ArrayList<Animals> animalsList;
    MainAdminActivity mainAdminActivity;


    /**
     * Classe's Constructor
     * To create this classe's objects
     * @param mainAdminActivity
     * @param animalsList
     */
    public AnimalsAdapterAdmin(MainAdminActivity mainAdminActivity, ArrayList<Animals> animalsList) {
        this.animalsList = animalsList;
        this.mainAdminActivity = mainAdminActivity;
    }


    /**
     * To indicate the item's layouts of the RecylerView
     * @param parent
     * @param viewType
     * @return View with RecyclerView's item
     */
    @NonNull
    @Override
    public AnimalsAdapterAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mainAdminActivity.getContext());
        View view = layoutInflater.inflate(R.layout.item_animals_list_admin, parent, false);
        return new ViewHolder(view);
    }


    /**
     * Fill the boxes with list's data
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String animal_ID = String.valueOf(animalsList.get(position).getAnimal_id());
        holder.textView_animal_name.setText(String.valueOf(animalsList.get(position).getAnimal_name()));
        holder.textView_animal_age.setText(String.valueOf(animalsList.get(position).getAnimal_age()) + " ano(s) de idade");
        holder.textView_animal_race.setText(String.valueOf(animalsList.get(position).getAnimal_race()));
        Picasso.get().load(animalsList.get(position).getImgURI()).into(holder.imageView_animal_photo);

        //To see each animal from list
        holder.imageView_animal_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OnlySeeAnimalActivity.class);
                intent.putExtra("animal_ID", animal_ID);
                v.getContext().startActivity(intent);
            }
        });

        //To delete animal from list
        holder.imageView_animal_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAnimal(position);
                animalsList.remove(position);
                notifyItemRemoved(position);
            }
        });

    }

    /**
     * method that deletes selected animal from data base,
     * used by the administrator and also seen by the adopter
     * @param position
     */
    private void deleteAnimal(int position) {
        mainAdminActivity.mStore.collection("animais").document(animalsList.get(position).getAnimal_id()).
                delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mainAdminActivity.getContext(), "Animal excluido com sucesso! ", Toast.LENGTH_LONG).show();

                    }
                });
    }


    /**
     * returns the list's size to know how many animals we have
     * @return
     */
    @Override
    public int getItemCount() {
        return animalsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_animal_id;
        TextView textView_animal_name;
        TextView textView_animal_age;
        TextView textView_animal_race;
        ImageView imageView_animal_photo;
        ImageView imageView_animal_delete;
        ImageView imageView_animal_see;


        /**
         * Link the field boxes with the respective layout elements
         * @param itemView (layout)
         * Req. 2 - Interface com utilizador
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_animal_name = itemView.findViewById(R.id.tv_animal_name_admin);
            textView_animal_age = itemView.findViewById(R.id.tv_animal_age_admin);
            textView_animal_race = itemView.findViewById(R.id.tv_animal_raca_admin);
            imageView_animal_photo = itemView.findViewById(R.id.im_animal_photo_admin);
            imageView_animal_delete = itemView.findViewById(R.id.img_delete_animal);
            imageView_animal_see = itemView.findViewById(R.id.img_see_animal_admin);
        }
    }
}
