package com.example.cantinhodosanimais.Model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cantinhodosanimais.R;
import com.example.cantinhodosanimais.View.AdminAdoptionsActivity;
import com.example.cantinhodosanimais.View.SeeAdoptionAdmin;

import java.util.ArrayList;


public class AdoptionAdapterAdmin extends RecyclerView.Adapter<AdoptionAdapterAdmin.ViewHolder> {

    private ArrayList<Adoptions> adoptionsList;
    AdminAdoptionsActivity adminAdoptionsActivity;

    /**
     * Classe's Constructor
     * To create this classe's objects
     * @param adminAdoptionsActivity
     * @param adoptionsList
     */
    public AdoptionAdapterAdmin(AdminAdoptionsActivity adminAdoptionsActivity, ArrayList<Adoptions> adoptionsList) {
        this.adoptionsList = adoptionsList;
        this.adminAdoptionsActivity = adminAdoptionsActivity;
    }

    /**
     * To indicate the item's layouts of the RecylerView
     * @param parent
     * @param viewType
     * @return View with RecyclerView's item
     */
    @NonNull
    @Override
    public AdoptionAdapterAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(adminAdoptionsActivity.getContext());
        View view = layoutInflater.inflate(R.layout.item_adoptions_admin, parent, false);
        return new ViewHolder(view);
    }


    /**
     * Fill the boxes with list's data
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String adoption_ID = String.valueOf(adoptionsList.get(position).getAdoption_ID());
        holder.textView_adoption_ID.setText(String.valueOf(adoptionsList.get(position).getAdoption_ID()));
        holder.textView_adopter_name.setText(String.valueOf(adoptionsList.get(position).getFullname()));
        holder.textView_telephone.setText(String.valueOf(adoptionsList.get(position).getTelephone()));
        holder.btn_see_adoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SeeAdoptionAdmin.class);
                intent.putExtra("adoption_ID", adoption_ID );
                v.getContext().startActivity(intent);
            }
        });
    }

    /**
     * returns the list's size to know how many adoptions we have
     * @return adoptions size
     */
    @Override
    public int getItemCount() {
        return adoptionsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_adoption_ID;
        TextView textView_adopter_name;
        TextView textView_telephone;
        Button btn_see_adoption;

        /**
         * Link the field boxes with the respective layout elements
         * @param itemView (layout)
         * Req. 2 - Interface com utilizador
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

             textView_adoption_ID = itemView.findViewById(R.id.tv_adoption_id_main);
             textView_adopter_name = itemView.findViewById(R.id.tv_adopter_name_admin);
             textView_telephone = itemView.findViewById(R.id.tv_telefone_adotante);
             btn_see_adoption = itemView.findViewById(R.id.btn_see_adoption_admin);
        }
    }
}
