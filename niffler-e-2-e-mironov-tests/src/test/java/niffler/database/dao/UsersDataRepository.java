package niffler.database.dao;

import jakarta.persistence.EntityManager;
import niffler.database.entity.userdata.UsersEntity;

import java.util.*;

public class UsersDataRepository extends RepositoryBase<UUID, UsersEntity> {
    public UsersDataRepository(EntityManager entityManager) {
        super(UsersEntity.class, entityManager);
    }

    public void addAll(UsersEntity... users) {
        for (UsersEntity user : users) {
            entityManager.persist(user);
        }
    }

    public void delete(UsersEntity... users) {
        for (UsersEntity user : users) {
            entityManager.remove(entityManager.createNamedQuery("findByUsername", UsersEntity.class)
                    .setParameter("username", user.getUsername())
                    .getSingleResult());
        }
    }

    public void updateUser(UsersEntity usersEntity){
        entityManager.merge(usersEntity);
    }
}
