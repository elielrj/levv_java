package com.example.levv.controller.usuario;

import com.example.levv.controller.ICrudController;
import com.example.levv.controller.meioDeTransporte.VeiculoController;
import com.example.levv.model.DAO.usuario.ArquivoDAO;
import com.example.levv.model.DAO.usuario.TransportadorDAO;
import com.example.levv.model.bo.usuario.Arquivo;
import com.example.levv.model.bo.usuario.Transportador;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class TransportadorController implements ICrudController<Transportador> {

    private TransportadorDAO transportadorDAO;

    public TransportadorController() {
        this.transportadorDAO = new TransportadorDAO();
    }

    @Override
    public void create(Transportador obj) {

        VeiculoController veiculoController = new VeiculoController();
        veiculoController.create(obj.getVeiculo());

        ArquivoDAO arquivoDAO = new ArquivoDAO();
        arquivoDAO.create(obj.getDocumentoDeIdentificacao());

        transportadorDAO.create(obj);
    }

    @Override
    public void update(Transportador obj) {
        transportadorDAO.update(obj);
    }

    public void updateGeoPoint(GeoPoint geoPoint) {
        transportadorDAO.updateGeoPoint(geoPoint);
    }


    @Override
    public void delete(Transportador obj) {
        transportadorDAO.delete(obj);
    }


    @Override
    public List<Transportador> list() {
        return transportadorDAO.list();
    }


}
