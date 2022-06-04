package com.example.levv.controller.tela;

import com.example.levv.model.tela.TelaProdutoAcompanharModel;
import com.example.levv.view.pedido.acompanhar.TelaProdutoAcompanharActivity;

public class TelaProdutoAcompanharController {

    private TelaProdutoAcompanharActivity activity;
    private TelaProdutoAcompanharModel model;

    public TelaProdutoAcompanharController(TelaProdutoAcompanharActivity activity) {
        this.activity = activity;
        this.model = new TelaProdutoAcompanharModel(this);
    }
}
