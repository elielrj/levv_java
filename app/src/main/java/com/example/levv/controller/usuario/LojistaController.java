package com.example.levv.controller.usuario;

import com.example.levv.controller.ICrudController;
import com.example.levv.model.DAO.usuario.LojistaDAO;
import com.example.levv.model.bo.usuario.Lojista;

import java.util.List;

public class LojistaController implements ICrudController<Lojista> {

    private LojistaDAO lojistaDAO;

    public LojistaController() {
        this.lojistaDAO = new LojistaDAO();
    }

    @Override
    public void create(Lojista obj) {
        lojistaDAO.create(obj);
    }

    @Override
    public void update(Lojista obj) {
        lojistaDAO.update(obj);
    }

    @Override
    public void delete(Lojista obj) {
        lojistaDAO.delete(obj);
    }

    @Override
    public List<Lojista> list() {
        return lojistaDAO.list();
    }

}
