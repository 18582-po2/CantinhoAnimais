package com.example.cantinhodosanimais;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class Animals {

    private String animal_id;
    private String animal_nome;
    private String animal_idade;
    private String animal_genero;
    private String animal_raca;
    private String animal_personalidade;
    private String animal_deficiencia;
    private String animal_historia;
    private String imgURI;

    public Animals(String animal_id, String animal_genero, String animal_deficiencia, String animal_personalidade, String animal_nome, String animal_idade, String animal_raca, String animal_historia, String imgURI) {
        this.animal_id = animal_id;
        this.animal_nome = animal_nome;
        this.animal_idade = animal_idade;
        this.animal_raca = animal_raca;
        this.animal_genero = animal_genero;
        this.animal_personalidade = animal_personalidade;
        this.animal_deficiencia = animal_deficiencia;
        this.animal_historia = animal_historia;
        this.imgURI = imgURI;
    }

    public String getAnimal_genero() {
        return animal_genero;
    }

    public void setAnimal_genero(String animal_genero) {
        this.animal_genero = animal_genero;
    }

    public String getAnimal_personalidade() {
        return animal_personalidade;
    }

    public void setAnimal_personalidade(String animal_personalidade) {
        this.animal_personalidade = animal_personalidade;
    }

    public String getAnimal_deficiencia() {
        return animal_deficiencia;
    }

    public void setAnimal_deficiencia(String animal_deficiencia) {
        this.animal_deficiencia = animal_deficiencia;
    }

    public String getAnimal_historia() {
        return animal_historia;
    }

    public void setAnimal_historia(String animal_historia) {
        this.animal_historia = animal_historia;
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
