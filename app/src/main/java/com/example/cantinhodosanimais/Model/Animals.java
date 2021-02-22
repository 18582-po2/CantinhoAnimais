package com.example.cantinhodosanimais.Model;

public class Animals {

    private String animal_id;
    private String animal_name;
    private String animal_age;
    private String animal_gender;
    private String animal_race;
    private String animal_personality;
    private String animal_deficiency;
    private String animal_story;
    private String imgURI;

    public Animals(String animal_id, String animal_gender, String animal_deficiency, String animal_personality, String animal_name, String animal_age, String animal_race, String animal_story, String imgURI) {
        this.animal_id = animal_id;
        this.animal_name = animal_name;
        this.animal_age = animal_age;
        this.animal_race = animal_race;
        this.animal_gender = animal_gender;
        this.animal_personality = animal_personality;
        this.animal_deficiency = animal_deficiency;
        this.animal_story = animal_story;
        this.imgURI = imgURI;
    }

    public String getAnimal_gender() {
        return animal_gender;
    }


    public String getAnimal_personality() {
        return animal_personality;
    }


    public String getAnimal_deficiency() {
        return animal_deficiency;
    }


    public String getAnimal_story() {
        return animal_story;
    }


    public String getAnimal_id() {
        return animal_id;
    }


    public String getAnimal_name() {
        return animal_name;
    }


    public String getAnimal_age() {
        return animal_age;
    }


    public String getAnimal_race() {
        return animal_race;
    }


    public String getImgURI() {
        return imgURI;
    }


}
