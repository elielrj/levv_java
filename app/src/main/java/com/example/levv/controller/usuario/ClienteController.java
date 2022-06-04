package com.example.levv.controller.usuario;

import com.example.levv.controller.ICrudController;
import com.example.levv.model.DAO.usuario.ClienteDAO;
import com.example.levv.model.bo.usuario.Cliente;

import java.util.List;

public class ClienteController implements ICrudController<Cliente> {

    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    @Override
    public void create(Cliente obj) {
        clienteDAO.create(obj);
    }

    @Override
    public void update(Cliente obj) {
        clienteDAO.update(obj);
    }

    @Override
    public void delete(Cliente obj) {
        clienteDAO.delete(obj);
    }

    @Override
    public List<Cliente> list() {
        return clienteDAO.list();
    }

}
