package com.example.levv.model.tela;

import com.example.levv.support.AppUtil;
import com.example.levv.support.Validar;
import java.util.List;

public class TelaCadastrarCelularModel {

    private String nome;
    private String sobrenome;
    private String celularComDdd;
    private String email;
    private String senha;
    private String senhaDeConfirmacao;


    public boolean validarEmail(String email) {
        return Validar.validarEmail(email);
    }

    public boolean validarNomeCompleto(String nomeCompleto) {
        return Validar.nome(nomeCompleto);
    }

    public void nomeComSobrenome(String nomeComSobrenome) {
        List<String> lista = AppUtil.extrairNomeESobrenome(nomeComSobrenome);
        setNome(lista.get(0));
        setSobrenome(lista.get(1));
    }

    public boolean validarCelular(String celular) {
        return Validar.celular(celular);
    }

    public boolean validarSenhas(String senha1, String senha2) {
        return Validar.senhas(senha1, senha2);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setSenhaDeConfirmacao(String senhaDeConfirmacao) {
        this.senhaDeConfirmacao = senhaDeConfirmacao;
    }

    public String getCelularComDdd() {
        return celularComDdd;
    }

    public void setCelularComDdd(String celularComDdd) {
        this.celularComDdd = celularComDdd;
    }

    public boolean validarSenha(String valor) {
        return Validar.senha(valor.toString());
    }
}
