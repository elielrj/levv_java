package com.example.levv.controller.pedido;

import com.example.levv.controller.ICrudController;
import com.example.levv.model.DAO.pedido.PedidoDAO;
import com.example.levv.model.bo.pedido.Pedido;

import java.util.List;

public class PedidoController implements ICrudController<Pedido> {

    private PedidoDAO pedidoDAO;

    public PedidoController() {
        this.pedidoDAO = new PedidoDAO();
    }

    @Override
    public void create(Pedido obj) {
        pedidoDAO.create(obj);
    }

    @Override
    public void update(Pedido obj) {
        pedidoDAO.update(obj);
    }

    @Override
    public void delete(Pedido obj) {

    }

    @Override
    public List<Pedido> list() {
        return pedidoDAO.list();
    }

    public void aceitarPedido(Pedido pedidoSelecionado) {
        pedidoDAO.aceitarPedido(pedidoSelecionado);
    }

    public void pedidoEntregue(Pedido pedidoSelecionado) {
        pedidoDAO.pedidoEntregue(pedidoSelecionado);
    }
}
