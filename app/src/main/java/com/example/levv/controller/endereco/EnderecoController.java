package com.example.levv.controller.endereco;

import com.example.levv.controller.ICrudController;
import com.example.levv.model.DAO.endereco.EnderecoDAO;
import com.example.levv.model.bo.endereco.Endereco;

import java.util.List;

public class EnderecoController implements ICrudController<Endereco> {

    private EnderecoDAO enderecoDAO;

    public EnderecoController() {
        this.enderecoDAO = new EnderecoDAO();
    }

    @Override
    public void create(Endereco obj) {

        BairroController bairroController = new BairroController();
        bairroController.create(obj.getBairro());

        EnderecoDAO enderecoDAO = new EnderecoDAO();
        enderecoDAO.create(obj);
    }

    @Override
    public void update(Endereco obj) {
        enderecoDAO.update(obj);
    }

    @Override
    public void delete(Endereco obj) {
        enderecoDAO.delete(obj);
    }

    @Override
    public List<Endereco> list() {
        return enderecoDAO.list();
    }


}
