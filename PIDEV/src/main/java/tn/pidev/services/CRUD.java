package tn.pidev.services;

import tn.pidev.entities.SalleDeSport;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T,S> {
    int insert1(T t,S s) throws SQLException;
    void update(T t) throws SQLException;
    void delete(int id) throws SQLException;
    List<T> afficher();

    List<SalleDeSport> afficher2();
}
