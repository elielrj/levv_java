package com.example.levv.controller.tela;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.levv.R;
import com.example.levv.model.tela.TelaCadastrarEnderecoModel;
import com.example.levv.model.tela.TelaCadastrarUsuarioModel;
import com.example.levv.model.bo.usuario.Login;
import com.example.levv.model.tela.TelaCadastrarCelularModel;
import com.example.levv.view.cadastro.celular.TelaCadastrarCelularActivity;
import com.example.levv.view.cadastro.celular.CadastrarCelularFragment;
import com.example.levv.view.cadastro.celular.CadastrarCelularLoginFragment;
import com.example.levv.view.cadastro.usuario.TelaCadastrarUsuarioActivity;
import com.example.levv.view.funcional.TelaMainActivity;


public class TelaCadastrarCelularController {

    private TelaCadastrarCelularActivity activity;
    private TelaCadastrarCelularModel model;

    private Login login;


    public TelaCadastrarCelularController(TelaCadastrarCelularActivity activity) {
        this.activity = activity;
        this.model = new TelaCadastrarCelularModel();
        activity.bundle = new Bundle();
        displayFragmentCadastrarUser();
    }

    public void enviar() {
        //Frgment Login
        if (activity.isLoginFragment) {

            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            CadastrarCelularLoginFragment cadastrarCelularLoginFragment = (CadastrarCelularLoginFragment) fragmentManager.findFragmentById(R.id.fragmentCadastroOuLogin);

            cadastrarCelularLoginFragment.cadastrar(model);

            //trocarDeTela(TelaMainActivity.class,activity.bundle);


            //Fragment Cadastro
        } else if (activity.isCadastrarFragment) {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            CadastrarCelularFragment cadastrarCelularFragment = (CadastrarCelularFragment) fragmentManager.findFragmentById(R.id.fragmentCadastroOuLogin);
            cadastrarCelularFragment.cadastrar(model);
            trocarDeTela(TelaCadastrarUsuarioActivity.class, activity.bundle);
        }
    }

    public void liparComponentes() {

        if (activity.isCadastrarFragment) {

            limparComponentesFragmentCadastrar();

            Toast.makeText(activity, "Dados de cadastro limpos!", LENGTH_SHORT).show();

        } else if (activity.isLoginFragment) {

            limparComponentesFragmentLogin();

            Toast.makeText(activity, "Dados de login limpos!", LENGTH_SHORT).show();

        }
    }

    public void displayFragment() {

        if (activity.isCadastrarFragment) {
            closeFragmentCadastrarUser();
            displayFragmentLogin();
            activity.btnCadastrarLogar.setText("LOGAR");
            activity.txtCadastrarLogar.setText("Ainda não é cadastrado? Cadastre-se aqui!");
        } else if (activity.isLoginFragment) {
            closeFragmentLogin();
            displayFragmentCadastrarUser();
            activity.btnCadastrarLogar.setText("CADASTRAR");
            activity.txtCadastrarLogar.setText("Já é cadastrado? Clique aqui!");
        }

/*
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        if (activity.isCadastrarFragment) {
            //pega CadastroFragmento
            CadastrarCelularFragment cadastrarCelularFragment = (CadastrarCelularFragment) fragmentManager.findFragmentById(R.id.fragmentCadastroOuLogin);
            //Abre transação
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //remove CadatsroFragment
            fragmentTransaction.remove(cadastrarCelularFragment).commit();
            //muda texto
            activity.txtCadastrarLogar.setText("Ainda não é cadastrado? Cadastre-se aqui!");
            //cria fragment Login
            CadastrarCelularLoginFragment fragment = CadastrarCelularLoginFragment.newInstance();
            //abrir transação de novo
            fragmentTransaction = fragmentManager.beginTransaction();
            //add novo fragment
            fragmentTransaction.add(R.id.fragmentCadastroOuLogin, fragment).addToBackStack(null).commit();
            //muda texto
            activity.btnCadastrarLogar.setText("LOGAR");
            //STATUS DO FRAGMENT
            activity.isCadastrarFragment = false;
            activity.isLoginFragment = true;
        } else if (activity.isLoginFragment) {
            //pega LoginFragmento
            CadastrarCelularLoginFragment cadastrarCelularLoginFragment = (CadastrarCelularLoginFragment) fragmentManager.findFragmentById(R.id.fragmentCadastroOuLogin);
            //Abre transação
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //remove LoginFragment
            fragmentTransaction.remove(cadastrarCelularLoginFragment).commit();
            //muda texto
            activity.txtCadastrarLogar.setText("Já é cadastrado? Clique aqui!");
            //cria fragment Login
            CadastrarCelularFragment fragment = CadastrarCelularFragment.newInstance();
            //abrir transação de novo
            fragmentTransaction = fragmentManager.beginTransaction();
            //add novo fragment
            fragmentTransaction.add(R.id.fragmentCadastroOuLogin, fragment).addToBackStack(null).commit();
            //muda texto
            activity.btnCadastrarLogar.setText("CADASTRAR");
            //STATUS DO FRAGMENT
            activity.isCadastrarFragment = true;
            activity.isLoginFragment = false;
        } else {
            //Abre transação
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            //cria fragment cadastro
            CadastrarCelularFragment fragment = CadastrarCelularFragment.newInstance();
            //add novo fragment
            fragmentTransaction.add(R.id.fragmentCadastroOuLogin, fragment).addToBackStack(null).commit();
            //muda texto
            activity.btnCadastrarLogar.setText("CADASTRAR");
            //STATUS DO FRAGMENT
            activity.isCadastrarFragment = true;
            activity.isLoginFragment = false;
        }*/
    }

    private void displayFragmentCadastrarUser() {

        CadastrarCelularFragment fragment = CadastrarCelularFragment.newInstance();

        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragmentCadastroOuLogin, fragment).addToBackStack(null).commit();

        activity.isCadastrarFragment = true;
    }

    private void displayFragmentLogin() {

        CadastrarCelularLoginFragment fragment = CadastrarCelularLoginFragment.newInstance();

        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragmentCadastroOuLogin, fragment).addToBackStack(null).commit();

        activity.isLoginFragment = true;
    }

    private void closeFragmentLogin() {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        CadastrarCelularLoginFragment fragment = (CadastrarCelularLoginFragment) fragmentManager.findFragmentById(R.id.fragmentCadastroOuLogin);

        if (fragment != null) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.remove(fragment).commit();

            activity.isLoginFragment = false;
        }
    }

    private void closeFragmentCadastrarUser() {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        CadastrarCelularFragment fragment = (CadastrarCelularFragment) fragmentManager.findFragmentById(R.id.fragmentCadastroOuLogin);

        if (fragment != null) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.remove(fragment).commit();

            activity.isCadastrarFragment = false;
        }
    }

    private boolean validarEmail(String email) {
        return model.validarEmail(email);
    }


    private boolean validarCelular(String celular) {
        return model.validarCelular(celular);
    }

    private boolean validarSenhas(String senha1, String senha2) {
        return model.validarSenhas(senha1, senha2);
    }

    private void limparComponentesFragmentLogin() {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        CadastrarCelularLoginFragment cadastrarCelularLoginFragment = (CadastrarCelularLoginFragment) fragmentManager.findFragmentById(R.id.fragmentCadastroOuLogin);
        cadastrarCelularLoginFragment.limparComponentes();
    }

    private void limparComponentesFragmentCadastrar() {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        CadastrarCelularFragment cadastrarCelularFragment = (CadastrarCelularFragment) fragmentManager.findFragmentById(R.id.fragmentCadastroOuLogin);
        cadastrarCelularFragment.limparComponentes();
    }

    public void buscarTipoDeUsuario() {
        activity.buscarTipoDeUsuario();
    }



    public void tipoDeUsuarioCadastrado() {

        activity.dados.putBoolean(TelaCadastrarUsuarioModel.TIPO_DE_USUARIO_CADASTRADO, true);
        activity.dados.apply();
    }

    public void trocarDeTela(Class<?> tela, Bundle bundle) {
        Intent intent = new Intent(activity, TelaCadastrarUsuarioActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.finish();
    }

}
