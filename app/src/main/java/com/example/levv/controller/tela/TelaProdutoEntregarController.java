package com.example.levv.controller.tela;


import android.content.Intent;
import android.widget.Toast;

import com.example.levv.controller.pedido.PedidoController;
import com.example.levv.support.Tag;
import com.example.levv.model.bo.pedido.Pedido;
import com.example.levv.model.tela.TelaProdutoEntregarModel;
import com.example.levv.view.localizar.TelaLocalizacaoActivity;
import com.example.levv.view.pedido.entregar.TelaProdutoEntregarActivity;

import java.util.List;

public class TelaProdutoEntregarController {

    private TelaProdutoEntregarActivity activity;
    private TelaProdutoEntregarModel model;



    public TelaProdutoEntregarController(TelaProdutoEntregarActivity activity) {
        this.activity = activity;
        this.model = new TelaProdutoEntregarModel(this);



    }




    private List<Pedido> buscarPedidos() {

        PedidoController pedidoController = new PedidoController();

        return pedidoController.list();
    }

    public void aceitarPedido(int item) {

        try {


            trocarTela();

        } catch (Exception e) {
            Toast.makeText(activity, Tag.PEDIDO_JA_ACEITO, Toast.LENGTH_LONG).show();
        }
    }

    private void trocarTela() {
        Intent intent = new Intent(activity, TelaLocalizacaoActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }






}
