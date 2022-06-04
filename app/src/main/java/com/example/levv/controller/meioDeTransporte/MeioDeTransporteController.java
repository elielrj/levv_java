package com.example.levv.controller.meioDeTransporte;

import com.example.levv.controller.ICrudController;
import com.example.levv.model.DAO.meioDeTransporte.MeioDeTransporteDAO;
import com.example.levv.model.bo.meioDeTransporte.MeioDeTransporte;
import com.google.firebase.firestore.CollectionReference;

import java.util.List;

public class MeioDeTransporteController implements ICrudController<MeioDeTransporte> {

    private MeioDeTransporteDAO meioDeTransporteDAO;

    public MeioDeTransporteController() {
        this.meioDeTransporteDAO = new MeioDeTransporteDAO();
    }

    @Override
    public void create(MeioDeTransporte obj) {
        meioDeTransporteDAO.create(obj);
    }

    @Override
    public void update(MeioDeTransporte obj) {
        meioDeTransporteDAO.update(obj);
    }

    @Override
    public void delete(MeioDeTransporte obj) {
        meioDeTransporteDAO.delete(obj);
    }

    @Override
    public List<MeioDeTransporte> list() {
        return meioDeTransporteDAO.list();
    }

    public List<MeioDeTransporte> listar(CollectionReference collectionReference) {
        return meioDeTransporteDAO.listar(collectionReference);
    }


}
