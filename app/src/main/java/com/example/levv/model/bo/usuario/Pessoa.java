package com.example.levv.model.bo.usuario;

import com.example.levv.model.bo.endereco.Endereco;
import com.example.levv.model.bo.pedido.Pedido;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;

import java.util.List;

public abstract class Pessoa implements Usuario {

    private String nome;
    private String sobrenome;
    private String cpf;
    private String tipoDeUsuario;
    private String dataDeNascimento;
    private boolean status;
    private Endereco endereco;
    private List<Pedido> listaDePedidos;
    private DocumentReference referenciaEndereco;

    public Pessoa(String nome, String sobrenome, String cpf, String tipoDeUsuario, String dataDeNascimento, boolean status, Endereco endereco, List<Pedido> listaDePedidos, DocumentReference referencia) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.tipoDeUsuario = tipoDeUsuario;
        this.dataDeNascimento = dataDeNascimento;
        this.status = status;
        this.endereco = endereco;
        this.listaDePedidos = listaDePedidos;
        this.referenciaEndereco = referencia;
    }

    public Pessoa() {
        this.endereco = new Endereco();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTipoDeUsuario() {
        return tipoDeUsuario;
    }

    public void setTipoDeUsuario(String tipoDeUsuario) {
        this.tipoDeUsuario = tipoDeUsuario;
    }

    public String getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(String dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Exclude
    public Endereco getEndereco() {
        return endereco;
    }

    @Exclude
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Exclude
    public List<Pedido> getListaDePedidos() {
        return listaDePedidos;
    }

    @Exclude
    public void setListaDePedidos(List<Pedido> listaDePedidos) {
        this.listaDePedidos = listaDePedidos;
    }

    @Exclude
    public String listarEnderecoDePessoa() {
        return endereco.toString();
    }

    public DocumentReference getReferenciaEndereco() {
        return referenciaEndereco;
    }

    public void setReferenciaEndereco(DocumentReference referenciaEndereco) {
        this.referenciaEndereco = referenciaEndereco;
    }

    @Override
    public String toString() {
        return endereco.toString();
    }
}
