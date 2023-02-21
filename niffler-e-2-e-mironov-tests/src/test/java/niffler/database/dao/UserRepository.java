package niffler.database.dao;

import jakarta.persistence.EntityManager;
import niffler.database.entity.users.User;

import java.util.UUID;

public class UserRepository extends RepositoryBase<UUID, User> {
    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}
