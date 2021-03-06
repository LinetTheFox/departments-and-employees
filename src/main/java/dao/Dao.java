package dao;

import java.util.List;

/**
 * Dao interface to work with the database
 * @author linet
 */
public interface Dao<T> {
    T get(long id);
    List<T> getAll();
    void save(T t);
    void update(Long id, T t);
    void delete(Long id);
}