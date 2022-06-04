package com.example.levv.model.tela;

public class TelaSplashModel {

    private boolean celularCadastrado;
    private boolean tipoDeUsuarioCadastrado;
    private boolean enderecoCadastrado;

    public boolean isCelularCadastrado() {
        return celularCadastrado;
    }

    public void setCelularCadastrado(boolean celularCadastrado) {
        this.celularCadastrado = celularCadastrado;
    }

    public boolean isTipoDeUsuarioCadastrado() {
        return tipoDeUsuarioCadastrado;
    }

    public void setTipoDeUsuarioCadastrado(boolean tipoDeUsuarioCadastrado) {
        this.tipoDeUsuarioCadastrado = tipoDeUsuarioCadastrado;
    }

    public boolean isEnderecoCadastrado() {
        return enderecoCadastrado;
    }

    public void setEnderecoCadastrado(boolean enderecoCadastrado) {
        this.enderecoCadastrado = enderecoCadastrado;
    }
}