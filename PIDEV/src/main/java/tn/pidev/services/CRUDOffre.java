package tn.pidev.services;

import java.sql.SQLException;
import java.util.List;

public interface CRUDOffre<T,S,I> {
    int insert(T t,S s) throws SQLException;
    int update(T t,I i) throws SQLException;
    int delete(int id) throws SQLException;
    List<T> showAll(S s) throws SQLException;
}