package com.example.levv.model.bo.endereco;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;

public class Bairro {

    private String nome;
    private boolean status;
    private Cidade cidade;

    private DocumentReference referencia;

    public Bairro() {
    }

    public Bairro(String nome, Cidade cidade) {
        this.nome = nome;
        this.cidade = cidade;
        this.status = false;
    }

    public Bairro(String nome, boolean status, Cidade cidade) {
        this.nome = nome;
        this.status = status;
        this.cidade = cidade;
    }

    public DocumentReference getReferencia() {
        return referencia;
    }

    public void setReferencia(DocumentReference referencia) {
        this.referencia = referencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    @Exclude
    public Cidade getCidade() {
        return cidade;
    }

    @Exclude
    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return nome + "\n" + cidade.toString();
    }
}
