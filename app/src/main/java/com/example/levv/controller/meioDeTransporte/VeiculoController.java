package com.example.levv.controller.meioDeTransporte;

import com.example.levv.controller.ICrudController;
import com.example.levv.model.DAO.meioDeTransporte.VeiculoDAO;
import com.example.levv.model.DAO.usuario.ArquivoDAO;
import com.example.levv.model.bo.meioDeTransporte.Veiculo;

import java.util.List;

public class VeiculoController implements ICrudController<Veiculo> {

    private VeiculoDAO veiculoDAO;

    public VeiculoController() {
        this.veiculoDAO = new VeiculoDAO();
    }

    @Override
    public void create(Veiculo obj) {

        ArquivoDAO arquivoDAO = new ArquivoDAO();
        arquivoDAO.create(obj.getDocumentoDeCrlv());

        veiculoDAO.create(obj);
    }

    @Override
    public void update(Veiculo obj) {
        veiculoDAO.update(obj);
    }

    @Override
    public void delete(Veiculo obj) {
        veiculoDAO.delete(obj);
    }

    @Override
    public List<Veiculo> list() {
        return veiculoDAO.list();
    }


}
