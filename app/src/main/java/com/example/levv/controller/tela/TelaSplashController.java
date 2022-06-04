package com.example.levv.controller.tela;

import android.content.Context;
import android.widget.Toast;
import com.example.levv.model.tela.TelaSplashModel;
import com.example.levv.support.ConfiguracoesSharedPreferences;
import com.example.levv.support.Textos;
import com.example.levv.view.cadastro.celular.TelaCadastrarCelularActivity;
import com.example.levv.view.cadastro.endereco.TelaCadastrarEnderecoActivity;
import com.example.levv.view.cadastro.usuario.TelaCadastrarUsuarioActivity;
import com.example.levv.view.funcional.TelaMainActivity;

public class TelaSplashController {

    private final TelaSplashModel model;
    private final ConfiguracoesSharedPreferences preferences;
    private final Context context;

    public TelaSplashController(Context contex) {
        this.context = contex;
        this.preferences = new ConfiguracoesSharedPreferences(context);
        this.model = new TelaSplashModel();
        this.verificarStatusDoApp();
    }


    public Class<?> selecionarTela() {

        if (model.isCelularCadastrado() &
                model.isTipoDeUsuarioCadastrado() &
                model.isEnderecoCadastrado()) {
            Toast.makeText(context, Textos.LOGIN_SUCESS, Toast.LENGTH_LONG).show();
            return TelaMainActivity.class;
        } else if (model.isCelularCadastrado() &
                model.isTipoDeUsuarioCadastrado() &
                !model.isEnderecoCadastrado()) {
            Toast.makeText(context, Textos.INCOMPLETE_ADDRESS, Toast.LENGTH_LONG).show();
            return TelaCadastrarEnderecoActivity.class;
        } else if (model.isCelularCadastrado() &
                !model.isTipoDeUsuarioCadastrado() &
                !model.isEnderecoCadastrado()) {
            Toast.makeText(context, Textos.INCOMPLETE_REGISTRATION, Toast.LENGTH_LONG).show();
            return TelaCadastrarUsuarioActivity.class;
        } else {
            Toast.makeText(context, Textos.WELCOME, Toast.LENGTH_SHORT).show();
            return TelaCadastrarCelularActivity.class;
        }
    }

    private void verificarStatusDoApp() {
        this.model.setCelularCadastrado(celularCadastrado());
        this.model.setTipoDeUsuarioCadastrado(usuarioCadastrado());
        this.model.setEnderecoCadastrado(enderecoCadastrado());
    }

    private boolean celularCadastrado() {
        return this.preferences.buscarDados().getBoolean(
                ConfiguracoesSharedPreferences.CELULAR_CADASTRADO,
                false
        );
    }

    private boolean usuarioCadastrado() {
        return this.preferences.buscarDados().getBoolean(
                ConfiguracoesSharedPreferences.TIPO_DE_USUARIO_CADASTRADO,
                false
        );
    }

    private boolean enderecoCadastrado() {
        return this.preferences.buscarDados().getBoolean(
                ConfiguracoesSharedPreferences.ENDERECO_CADASTRADO,
                false
        );
    }
}
