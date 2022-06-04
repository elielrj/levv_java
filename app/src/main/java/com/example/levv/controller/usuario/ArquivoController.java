package com.example.levv.controller.usuario;

import com.example.levv.controller.ICrudController;
import com.example.levv.model.DAO.usuario.ArquivoDAO;
import com.example.levv.model.bo.usuario.Arquivo;

import java.util.List;

public class ArquivoController implements ICrudController<Arquivo> {

    private ArquivoDAO arquivoDAO;

    public ArquivoController() {
        this.arquivoDAO = new ArquivoDAO();
    }

    @Override
    public void create(Arquivo obj) {
        arquivoDAO.create(obj);
    }

    @Override
    public void update(Arquivo obj) {
        arquivoDAO.update(obj);
    }

    @Override
    public void delete(Arquivo obj) {
        arquivoDAO.delete(obj);
    }

    @Override
    public List<Arquivo> list() {
        return arquivoDAO.list();
    }


}
