package com.example.cantinhodosanimais.Model;

public class Adoptions {

    private String ID_animal, adoption_ID, fullname, age, profession, hasChildren, childrensAge, address,
            telephone, bi, houseType, hasOtherAnimals, otherAnimals, adoptionReasons;

    public Adoptions(String ID_animal,String adoption_ID,String fullname, String age, String profession, String hasChildren, String childrensAge, String address, String telephone, String bi, String houseType, String hasOtherAnimals, String otherAnimals, String adoptionReasons) {

        this.ID_animal = ID_animal;
        this.adoption_ID = adoption_ID;
        this.fullname = fullname;
        this.age = age;
        this.profession = profession;
        this.hasChildren = hasChildren;
        this.childrensAge = childrensAge;
        this.address = address;
        this.telephone = telephone;
        this.bi = bi;
        this.houseType = houseType;
        this.hasOtherAnimals = hasOtherAnimals;
        this.otherAnimals = otherAnimals;
        this.adoptionReasons = adoptionReasons;
    }

    public String getID_animal() {
        return ID_animal;
    }

    public String getAdoption_ID() {
        return adoption_ID;
    }

    public String getFullname() {
        return fullname;
    }

    public String getAge() {
        return age;
    }

    public String getProfession() {
        return profession;
    }

    public String getHasChildren() {
        return hasChildren;
    }

    public String getChildrensAge() {
        return childrensAge;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getBi() {
        return bi;
    }

    public String getHouseType() {
        return houseType;
    }

    public String getHasOtherAnimals() {
        return hasOtherAnimals;
    }

    public String getOtherAnimals() {
        return otherAnimals;
    }

    public String getAdoptionReasons() {
        return adoptionReasons;
    }
}
