package com.example.levv.model.bo.usuario;

import com.example.levv.model.bo.endereco.Endereco;
import com.example.levv.model.bo.pedido.Pedido;
import com.google.firebase.firestore.DocumentReference;
import java.util.List;

public class Lojista extends Cliente{

    private String cnpj;
    private String nomeFantasia;
    private String nomeDaEmpresa;

    public Lojista() {
    }

   /* public Lojista(Endereco endereco, String cnpj, String nomeFantasia, String nomeDaEmpresa) {
        super(endereco);
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.nomeDaEmpresa = nomeDaEmpresa;
    }*/

    public Lojista(Endereco endereco) {
        super(endereco);
    }

    /*public Lojista(String nome, String sobrenome, String cpf, String tipoDeUsuario, String dataDeNascimento, boolean status, Endereco endereco, List<Pedido> listaDePedidos, DocumentReference referencia, String cnpj, String nomeFantasia, String nomeDaEmpresa) {
        super(nome, sobrenome, cpf, tipoDeUsuario, dataDeNascimento, status, endereco, listaDePedidos, referencia);
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.nomeDaEmpresa = nomeDaEmpresa;
    }*/

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getNomeDaEmpresa() {
        return nomeDaEmpresa;
    }

    public void setNomeDaEmpresa(String nomeDaEmpresa) {
        this.nomeDaEmpresa = nomeDaEmpresa;
    }
}
