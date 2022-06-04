package com.example.levv.controller.usuario;

import com.example.levv.controller.ICrudController;
import com.example.levv.model.DAO.usuario.LoginDAO;
import com.example.levv.model.bo.usuario.Login;

import java.util.List;

public class LoginController implements ICrudController<Login> {

    private LoginDAO loginDAO;

    public LoginController() {
        this.loginDAO = new LoginDAO();
    }

    @Override
    public void create(Login obj) {
        loginDAO.create(obj);
    }

    @Override
    public void update(Login obj) {
        loginDAO.update(obj);
    }

    @Override
    public void delete(Login obj) {
        loginDAO.delete(obj);
    }


    @Override
    public List<Login> list() {
        return loginDAO.list();
    }




}
