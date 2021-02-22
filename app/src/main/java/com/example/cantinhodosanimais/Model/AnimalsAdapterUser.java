package com.example.cantinhodosanimais.Model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cantinhodosanimais.View.MainUserActivity;
import com.example.cantinhodosanimais.R;
import com.example.cantinhodosanimais.View.SeeAnimalActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AnimalsAdapterUser extends RecyclerView.Adapter<AnimalsAdapterUser.ViewHolder> {

    private ArrayList<Animals> animalsList;
    MainUserActivity mainUserActivity;


    /**
     * Classe's Constructor
     * To create this classe's objects
     * @param mainUserActivity
     * @param animalsList
     */
    public AnimalsAdapterUser(MainUserActivity mainUserActivity, ArrayList<Animals> animalsList) {
        this.animalsList = animalsList;
        this.mainUserActivity = mainUserActivity;
    }


    /**
     * To indicate the item's layouts of the RecylerView
     * @param parent
     * @param viewType
     * @return View with RecyclerView's item
     */
    @NonNull
    @Override
    public AnimalsAdapterUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mainUserActivity.getContext());
        View view = layoutInflater.inflate(R.layout.item_animals_list_user, parent, false);
        return new ViewHolder(view);
    }


    /**
     *  Fill the boxes with list's data
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull AnimalsAdapterUser.ViewHolder holder, int position) {

        String animal_ID = String.valueOf(animalsList.get(position).getAnimal_id());
        holder.textView_animal_name.setText(String.valueOf(animalsList.get(position).getAnimal_name()));
        holder.textView_animal_age.setText(String.valueOf(animalsList.get(position).getAnimal_age())+ " ano(s) de idade");
        holder.textView_animal_race.setText(String.valueOf(animalsList.get(position).getAnimal_race()));
        Picasso.get().load(animalsList.get(position).getImgURI()).into(holder.imageView_animal_photo);
        holder.btn_see_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SeeAnimalActivity.class);
                intent.putExtra("animal_ID", animal_ID);
                v.getContext().startActivity(intent);
            }
        });
    }


    /**
     * returns the list's size to know how many animals we have
     * @return  animals size
     */
    @Override
    public int getItemCount() {
        return animalsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btn_see_animal;
        TextView textView_animal_name;
        TextView textView_animal_age;
        TextView textView_animal_race;
        ImageView imageView_animal_photo;


        /**
         * Link the field boxes with the respective layout elements
         * @param itemView (layout)
         * Req. 2 - Interface com utilizador
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_see_animal = itemView.findViewById(R.id.btn_see_animal_user);

            textView_animal_name = itemView.findViewById(R.id.tv_animal_name_user);
            textView_animal_age = itemView.findViewById(R.id.tv_animal_age_user);
            textView_animal_race = itemView.findViewById(R.id.tv_animal_raca_user);
            imageView_animal_photo = itemView.findViewById(R.id.im_animal_photo_user);
        }
    }
}
