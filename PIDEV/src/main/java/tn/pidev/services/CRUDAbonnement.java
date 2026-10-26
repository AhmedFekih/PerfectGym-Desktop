package tn.pidev.services;

import java.sql.SQLException;
import java.util.List;

public interface CRUDAbonnement<T,U> {
    int insert(T t) throws SQLException;
    int update(T t) throws SQLException;
    int delete(int id) throws SQLException;
    List<T> showAll(U u) throws SQLException;
}