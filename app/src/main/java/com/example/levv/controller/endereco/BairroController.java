package com.example.levv.controller.endereco;

import com.example.levv.controller.ICrudController;
import com.example.levv.model.DAO.endereco.BairroDAO;
import com.example.levv.model.bo.endereco.Bairro;

import java.util.List;

public class BairroController implements ICrudController<Bairro> {

    private BairroDAO bairroDAO;

    public BairroController() {
        this.bairroDAO = new BairroDAO();
    }

    @Override
    public void create(Bairro obj) {

        CidadeController cidadeController = new CidadeController();
        cidadeController.create(obj.getCidade());

        BairroDAO bairroDAO = new BairroDAO();
        bairroDAO.create(obj);
    }

    @Override
    public void update(Bairro obj) {
        bairroDAO.update(obj);
    }

    @Override
    public void delete(Bairro obj) {
        bairroDAO.delete(obj);
    }

    @Override
    public List<Bairro> list() {
        return bairroDAO.list();
    }


}
