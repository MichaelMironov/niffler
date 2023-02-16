package niffler.database.dao;

import jakarta.persistence.EntityManager;
import niffler.database.entity.authorities.Authorities;

import java.util.UUID;

public class AuthoritiesRepository extends RepositoryBase<UUID, Authorities> {
    public AuthoritiesRepository(EntityManager entityManager) {
        super(Authorities.class, entityManager);
    }
}
