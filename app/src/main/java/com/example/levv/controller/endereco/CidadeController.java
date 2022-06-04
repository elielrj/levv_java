package com.example.levv.controller.endereco;

import com.example.levv.controller.ICrudController;
import com.example.levv.model.DAO.endereco.CidadeDAO;
import com.example.levv.model.bo.endereco.Cidade;

import java.util.List;

public class CidadeController implements ICrudController<Cidade> {

    private CidadeDAO cidadeDAO;

    public CidadeController() {
        this.cidadeDAO = new CidadeDAO();
    }

    @Override
    public void create(Cidade obj) {

        EstadoController estadoController = new EstadoController();
        estadoController.create(obj.getEstado());

        cidadeDAO.create(obj);
    }

    @Override
    public void update(Cidade obj) {
        cidadeDAO.update(obj);
    }

    @Override
    public void delete(Cidade obj) {
        cidadeDAO.delete(obj);
    }

    @Override
    public List<Cidade> list() {
        return cidadeDAO.list();
    }


}
