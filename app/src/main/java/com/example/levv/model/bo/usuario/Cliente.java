package com.example.levv.model.bo.usuario;

import com.example.levv.model.bo.endereco.Endereco;
import com.example.levv.model.bo.pedido.Pedido;
import com.google.firebase.firestore.DocumentReference;
import java.util.List;

public class Cliente extends Pessoa{

    public Cliente() {
    }

    public Cliente(Endereco endereco) {
        this.setEndereco(endereco);
    }

    /*public Cliente(String nome, String sobrenome, String cpf, String tipoDeUsuario, String dataDeNascimento, boolean status, Endereco endereco, List<Pedido> listaDePedidos, DocumentReference referencia) {
        super(nome, sobrenome, cpf, tipoDeUsuario, dataDeNascimento, status, endereco, listaDePedidos, referencia);
    }*/

}
