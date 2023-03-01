package niffler.database.dao;

import jakarta.persistence.EntityManager;
import niffler.database.DataBase;
import niffler.database.entity.auth.UserEntity;
import niffler.database.jpa.EmfContext;
import niffler.database.jpa.JpaService;

public class PostgresHibernateUsersDAO extends JpaService {
    public PostgresHibernateUsersDAO() {
        super(EmfContext.INSTANCE.getEmf(DataBase.AUTH).createEntityManager());
    }


    public void addUser(UserEntity users) {
        persist(users);
    }

    public void addUsers(UserEntity... userEntities) {
        merge(userEntities);
    }


    public void updateUser(UserEntity userEntity) {
        merge(userEntity);
    }


    public void deleteUser(UserEntity userEntity) {
        remove(userEntity);
    }

    public UserEntity getByUsername(String username) {
        return em.createQuery("select u from UserEntity u where u.username=:username", UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public void deleteByUsername(String username) {
        em.remove(getByUsername(username));
    }
}
