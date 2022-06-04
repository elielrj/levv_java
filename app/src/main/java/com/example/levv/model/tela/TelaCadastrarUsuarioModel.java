package com.example.levv.model.tela;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.levv.controller.usuario.ArquivoController;
import com.example.levv.support.Validar;
import com.example.levv.controller.tela.TelaCadastrarUsuarioController;
import com.example.levv.controller.usuario.ClienteController;
import com.example.levv.controller.usuario.LojistaController;
import com.example.levv.controller.usuario.TransportadorController;
import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.bo.usuario.Arquivo;
import com.example.levv.model.bo.usuario.Cliente;
import com.example.levv.model.bo.usuario.Lojista;
import com.example.levv.model.bo.usuario.Transportador;

import com.google.firebase.auth.FirebaseAuth;

public class TelaCadastrarUsuarioModel {

    private TelaCadastrarUsuarioController controller;

    private FirebaseAuth autenticacao;
    public static String TIPO_DE_USUARIO_CADASTRADO = "tipo_usuario";


    public TelaCadastrarUsuarioModel(TelaCadastrarUsuarioController controller) {
        this.controller = controller;
        this.autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
    }

    public void incluirLojista(Lojista lojista) {
        LojistaController lojistaController = new LojistaController();
        lojistaController.create(lojista);
    }

    public void incluirCliente(Cliente cliente) {
        ClienteController clienteController = new ClienteController();
        clienteController.create(cliente);
    }


    public void inserirTransportador(Transportador transportador) {
        TransportadorController transportadorController = new TransportadorController();
        transportadorController.create(transportador);
    }


    public boolean validarMarca(String marca) {
        return Validar.validarMarca(marca);
    }

    public boolean validarModelo(String modelo) {
        return Validar.validarModelo(modelo);
    }

    public boolean validarRenavam(String renavam) {
        return Validar.validarRenavam(renavam);
    }

    public boolean validarPlaca(String placa) {
        return Validar.validarPlaca(placa);
    }

    public boolean validarNome(String nome) {
        return Validar.nome(nome);
    }

    public boolean validarSobrenome(String sobrenome) {
        return Validar.nome(sobrenome);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean validarDtNasc(String dataDeNascimento) {
        return Validar.isDateValid(dataDeNascimento);
    }

    public boolean validarCpf(String cpf) {
        return Validar.validarCPF(cpf);
    }

    public boolean validarCnpj(String cnpj) {
        return Validar.isCnpj(cnpj);
    }

    public boolean validarNomeEmpresa(String nomeEmpresa) {
        return Validar.nome(nomeEmpresa);
    }

    public boolean validarNomeFantasia(String nomeFantasia) {
        return Validar.nome(nomeFantasia);
    }

}
