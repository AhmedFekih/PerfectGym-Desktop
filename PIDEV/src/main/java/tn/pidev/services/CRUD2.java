package tn.pidev.services;

import java.sql.SQLException;
import java.util.List;

public interface CRUD2<T> {
    int insert(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(int id) throws SQLException;
    List<T> afficher(int idSalle);
}
