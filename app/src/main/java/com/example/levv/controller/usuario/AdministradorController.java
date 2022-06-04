package com.example.levv.controller.usuario;

import com.example.levv.controller.ICrudController;
import com.example.levv.model.DAO.usuario.AdministradorDAO;
import com.example.levv.model.bo.usuario.Administrador;

import java.util.List;

public class AdministradorController implements ICrudController<Administrador> {

    private AdministradorDAO administradorDAO;

    public AdministradorController() {
        this.administradorDAO = new AdministradorDAO();
    }

    @Override
    public void create(Administrador obj) {
        administradorDAO.create(obj);
    }

    @Override
    public void update(Administrador obj) {
        administradorDAO.update(obj);
    }

    @Override
    public void delete(Administrador obj) {
        administradorDAO.delete(obj);
    }

    @Override
    public List<Administrador> list() {
        return administradorDAO.list();
    }

}
