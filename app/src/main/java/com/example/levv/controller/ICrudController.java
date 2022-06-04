package com.example.levv.controller;

import java.util.List;

public interface ICrudController<T> {

    void create(T obj);

    void update(T obj);

    void delete(T obj);

    List<T> list();

}
