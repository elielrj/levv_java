package com.example.levv.controller.tela;

import android.content.SharedPreferences;

import com.example.levv.R;
import com.example.levv.model.tela.TelaMainActivityModel;
import com.example.levv.view.funcional.TelaMainActivity;
import com.example.levv.view.pedido.acompanhar.TelaProdutoAcompanharActivity;
import com.example.levv.view.pedido.entregar.TelaProdutoEntregarActivity;
import com.example.levv.view.pedido.enviar.TelaProdutoEnviarActivity;

public class TelaMainActivityController {

    private TelaMainActivity activity;
    private TelaMainActivityModel model;

    public TelaMainActivityController(TelaMainActivity activity) {
        this.activity = activity;
        inicializaComponentes();
        this.model = new TelaMainActivityModel(this);
    }


    public void entregarProduto() {
        model.trocarTela(TelaProdutoEntregarActivity.class, activity);
    }

    public void entregarEnviarProduto() {
        model.trocarTela(TelaProdutoEnviarActivity.class, activity);
    }

    public void entregarAcompanhar() {
        model.trocarTela(TelaProdutoAcompanharActivity.class, activity);
    }

    public void deslogarUsuario() {
        activity.dados.clear();
        activity.dados.commit();
        model.deslogarUsuario(activity);
    }

    private void inicializaComponentes() {
        //ID
        activity.btnEntregarProduto = activity.findViewById(R.id.btnEntregarProduto);
        activity.btnEnviarProduto = activity.findViewById(R.id.btnEnviarProduto);
        activity.btnAcompanharProduto = activity.findViewById(R.id.btnAcompanharProduto);
        //OUVIR
        activity.btnEntregarProduto.setOnClickListener(activity);
        activity.btnEnviarProduto.setOnClickListener(activity);
        activity.btnAcompanharProduto.setOnClickListener(activity);
    }

}
