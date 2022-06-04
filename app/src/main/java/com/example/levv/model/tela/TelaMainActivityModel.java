package com.example.levv.model.tela;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.levv.controller.tela.TelaMainActivityController;
import com.example.levv.controller.usuario.AdministradorController;
import com.example.levv.controller.usuario.ClienteController;
import com.example.levv.controller.usuario.LojistaController;
import com.example.levv.controller.usuario.TransportadorController;
import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.bo.usuario.Administrador;
import com.example.levv.model.bo.usuario.Cliente;
import com.example.levv.model.bo.usuario.Lojista;
import com.example.levv.model.bo.usuario.Transportador;
import com.example.levv.view.cadastro.celular.TelaCadastrarCelularActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TelaMainActivityModel {

    private TelaMainActivityController controller;
    private FirebaseAuth autenticacao;


    public TelaMainActivityModel(TelaMainActivityController controller) {
        this.controller = controller;
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
    }

    public void trocarTela(Class<?> classeJava, Activity telaAtual) {
        Intent intent = new Intent(telaAtual, classeJava);
        telaAtual.startActivity(intent);
        //telaAtual.finish();
    }

    public void deslogarUsuario(Activity telaAtual) {
        autenticacao.signOut();
        trocarTela(TelaCadastrarCelularActivity.class, telaAtual);
    }



}
