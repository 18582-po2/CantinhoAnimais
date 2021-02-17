package com.example.cantinhodosanimais;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class Animals {

    private String animal_id;
    private String animal_nome;
    private String animal_idade;
    private String animal_raca;
    private String imgURI;

    public Animals(String animal_id, String animal_nome, String animal_idade, String animal_raca, String imgURI) {
        this.animal_id = animal_id;
        this.animal_nome = animal_nome;
        this.animal_idade = animal_idade;
        this.animal_raca = animal_raca;
        this.imgURI = imgURI;
    }

    public String getAnimal_id() {
        return animal_id;
    }

    public void setAnimal_id(String animal_id) {
        this.animal_id = animal_id;
    }

    public String getAnimal_nome() {
        return animal_nome;
    }

    public void setAnimal_nome(String animal_nome) {
        this.animal_nome = animal_nome;
    }

    public String getAnimal_idade() {
        return animal_idade;
    }

    public void setAnimal_idade(String animal_idade) {
        this.animal_idade = animal_idade;
    }

    public String getAnimal_raca() {
        return animal_raca;
    }

    public void setAnimal_raca(String animal_raca) {
        this.animal_raca = animal_raca;
    }

    public String getImgURI() {
        return imgURI;
    }

    public void setImgURI(String imgURI) {
        this.imgURI = imgURI;
    }




}
