package com.example.levv.support;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfiguracoesSharedPreferences {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor dados;
    private final Context context;

    public static final String CELULAR_CADASTRADO = "celular_cadastrado";
    public static final String TIPO_DE_USUARIO_CADASTRADO = "tipo_usuario";
    public static final String ENDERECO_CADASTRADO = "endereco_cadastrado";

    public ConfiguracoesSharedPreferences(Context context) {
        this.context = context;
        this.inicializarSharedPreferences();
        editarSharedPreferences();

    }

    private synchronized void inicializarSharedPreferences() {
        this.sharedPreferences =
                context.getSharedPreferences(
                        AppUtil.SHARED_PREFERENCES_NAME_APP,
                        Context.MODE_PRIVATE
                );
    }

    private void editarSharedPreferences() {
        dados = sharedPreferences.edit();
        dados.commit();
    }

    synchronized public void gravarDados() {
        dados.apply();
    }

    public SharedPreferences.Editor inserirDados() {
        return dados;
    }

    public SharedPreferences buscarDados(){
        return sharedPreferences;
    }

}
