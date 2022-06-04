package com.example.levv.model.DAO;

import java.util.List;

public interface ICrudDAO<T> {

    void create(T objeto);

    void update(T objeto);

    void delete(T objeto);

    List<T> list();
}