package com.example.levv.controller.endereco;

import com.example.levv.controller.ICrudController;
import com.example.levv.model.DAO.endereco.EstadoDAO;
import com.example.levv.model.bo.endereco.Estado;

import java.util.List;

public class EstadoController implements ICrudController<Estado> {

    private EstadoDAO estadoDAO;

    public EstadoController() {
        estadoDAO = new EstadoDAO();
    }

    @Override
    public void create(Estado obj) {
        estadoDAO.create(obj);
    }

    @Override
    public void update(Estado obj) {
        estadoDAO.update(obj);
    }

    @Override
    public void delete(Estado obj) {
        estadoDAO.delete(obj);
    }

    @Override
    public List<Estado> list() {
        return estadoDAO.list();
    }


}
