package com.example.levv.model.tela;

import com.example.levv.controller.endereco.EnderecoController;
import com.example.levv.controller.tela.TelaCadastrarEnderecoController;
import com.example.levv.support.Validar;
import com.example.levv.model.bo.endereco.Endereco;

public class TelaCadastrarEnderecoModel {

    private TelaCadastrarEnderecoController telaCadastrarEnderecoController;

    public static String ENDERECO_CADASTRADO = "endereco_cadastrado";


    public TelaCadastrarEnderecoModel(TelaCadastrarEnderecoController telaCadastrarEnderecoController) {
        this.telaCadastrarEnderecoController = telaCadastrarEnderecoController;
    }

    public boolean validarLogradouro(String logradouro) {
        return Validar.logradouro(logradouro);
    }

    public boolean validarNumeroLogradouro(String numeroLogradouro) {
        return Validar.numeroLogradouro(numeroLogradouro);
    }

    public boolean validarCep(String cep) {
        return Validar.cep(cep);
    }

    public boolean validarBairro(String bairro) {
        return Validar.bairro(bairro);
    }

    public boolean validarCidade(String cidade) {
        return Validar.cidade(cidade);
    }

    public boolean validarEstado(String estado) {
        return Validar.estado(estado);
    }

    public void inserirEndereco(Endereco endereco) {
        EnderecoController enderecoController = new EnderecoController();
        enderecoController.create(endereco);
    }
}
