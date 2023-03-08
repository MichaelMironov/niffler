package niffler.database.dao;

import java.util.List;

public interface Dao<K, E> {

    List<E> findAll();

    E create(E spend);

    boolean delete(K uuid);
}
