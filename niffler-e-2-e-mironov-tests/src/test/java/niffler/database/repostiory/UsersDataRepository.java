package niffler.database.repostiory;

import jakarta.persistence.EntityManager;
import niffler.database.entity.userdata.ProfileEntity;

import java.util.*;

public class UsersDataRepository extends RepositoryBase<UUID, ProfileEntity> {
    public UsersDataRepository(EntityManager entityManager) {
        super(ProfileEntity.class, entityManager);
    }

    public void addAll(ProfileEntity... users) {
        for (ProfileEntity user : users) {
            entityManager.persist(user);
        }
    }

    public void delete(ProfileEntity... users) {
        for (ProfileEntity user : users) {
            entityManager.remove(entityManager.createNamedQuery("findByUsername", ProfileEntity.class)
                    .setParameter("username", user.getUsername())
                    .getSingleResult());
        }
    }

    public void updateUser(ProfileEntity profileEntity){
        entityManager.merge(profileEntity);
    }
}
