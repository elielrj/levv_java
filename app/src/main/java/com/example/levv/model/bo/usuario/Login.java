package com.example.levv.model.bo.usuario;

import com.example.levv.support.Validar;
import com.google.firebase.firestore.DocumentReference;

public class Login {

    private String email;
    private String ddd;
    private String numeroCelular;
    private String senha;

    private DocumentReference referenciaDaPessoa;

    public Login(String email, String numeroCelular, String senha) {
        this.email = email;
        this.ddd = Validar.celularBuscarDdd(numeroCelular);
        this.numeroCelular = Validar.celularBuscarNumero(numeroCelular);
        this.senha = senha;
    }

    public Login(String email, String ddd, String numeroSemDdd, String senha) {
        this.email = email;
        this.ddd = ddd;
        this.numeroCelular = numeroSemDdd;
        this.senha = senha;
    }

    public Login() {
    }

    private Login(LoginBuilder loginBuilder) {
        this.email = loginBuilder.email;
        this.ddd = loginBuilder.ddd;
        this.numeroCelular = loginBuilder.numeroCelular;
        this.senha = loginBuilder.senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public DocumentReference getReferenciaDaPessoa() {
        return referenciaDaPessoa;
    }

    public void setReferenciaDaPessoa(DocumentReference referenciaDaPessoa) {
        this.referenciaDaPessoa = referenciaDaPessoa;
    }

    public static class LoginBuilder{

        private String email;
        private String ddd;
        private String numeroCelular;
        private String senha;

        private DocumentReference referenciaDaPessoa;

        public LoginBuilder email(String email) {
            this.email = email;
            return this;
        }

        public LoginBuilder ddd(String ddd) {
            this.ddd = ddd;
            return this;
        }

        public LoginBuilder celular(String numeroCelular) {
            this.numeroCelular = numeroCelular;
            return this;
        }

        public LoginBuilder celularComDdd(String numeroCelularComDdd) {
            this.ddd = Validar.celularBuscarDdd(numeroCelularComDdd);
            this.numeroCelular = Validar.celularBuscarNumero(numeroCelularComDdd);
            return this;
        }


        public LoginBuilder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public LoginBuilder referencia(DocumentReference referencia) {
            this.referenciaDaPessoa = referencia;
            return this;
        }

        public Login create() {
            return new Login(this);
        }
    }
}
