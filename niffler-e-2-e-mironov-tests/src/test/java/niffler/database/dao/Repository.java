package niffler.database.dao;

import niffler.database.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.*;

public interface Repository<K extends Serializable, E extends BaseEntity<K>> {

    List<E> findAll();

    E save(E entity);

    void delete(K uuid);

    void update(E entity);

    default Optional<E> findById(K id){
        return findById(id, emptyMap());
    }

    Optional<E> findById(K uuid, Map<String, Object> properties);

}
