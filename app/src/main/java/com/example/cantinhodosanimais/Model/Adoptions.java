package com.example.cantinhodosanimais.Model;

public class Adoptions {

    private String ID_animal, adoption_ID, fullname, idade, profissao, temCriancas, idadeCriancas, morada,
            telefone, bilheteIdentidade, tipoCasa, temOutrosAnimais, outrosAnimais, motivoAdocao;

    public Adoptions(String ID_animal,String adoption_ID,String input_fullname, String input_idade, String input_profissao, String input_temCriancas, String input_idadeCriancas, String input_morada, String input_telefone, String input_bilheteIdentidade, String input_tipoCasa, String input_temOutrosAnimais, String input_outrosAnimais, String input_motivoAdocao) {

        this.ID_animal = ID_animal;
        this.adoption_ID = adoption_ID;
        this.fullname = input_fullname;
        this.idade = input_idade;
        this.profissao = input_profissao;
        this.temCriancas = input_temCriancas;
        this.idadeCriancas = input_idadeCriancas;
        this.morada = input_morada;
        this.telefone = input_telefone;
        this.bilheteIdentidade = input_bilheteIdentidade;
        this.tipoCasa = input_tipoCasa;
        this.temOutrosAnimais = input_temOutrosAnimais;
        this.outrosAnimais = input_outrosAnimais;
        this.motivoAdocao = input_motivoAdocao;
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

    public String getIdade() {
        return idade;
    }

    public String getProfissao() {
        return profissao;
    }

    public String getTemCriancas() {
        return temCriancas;
    }

    public String getIdadeCriancas() {
        return idadeCriancas;
    }

    public String getMorada() {
        return morada;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getBilheteIdentidade() {
        return bilheteIdentidade;
    }

    public String getTipoCasa() {
        return tipoCasa;
    }

    public String getTemOutrosAnimais() {
        return temOutrosAnimais;
    }

    public String getOutrosAnimais() {
        return outrosAnimais;
    }

    public String getMotivoAdocao() {
        return motivoAdocao;
    }
}
