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

    public void addFriendsToUser(List<UsersEntity> friends, UsersEntity user) {

        final List<UsersEntity> allUsers = new ArrayList<>(friends);
        allUsers.add(user);

        //Checking for adding a non-existent user
        final List<UsersEntity> usersEntityList = allUsers.stream().map(this::checkUsersExisting).toList();

        //Adding existing users
        List<UsersEntity> existUsers = new ArrayList<>();
        for (UsersEntity usersEntity : usersEntityList) {
            if (usersEntity != null) {
                existUsers.add(usersEntity);
                if (Objects.equals(usersEntity.getUsername(), user.getUsername()))
                    user = usersEntity;
            }
        }

        //Adding existing users into friends table
        for (UsersEntity existUser : existUsers) {
            entityManager.createNativeQuery("""
                            insert into friends (user_id, friend_id)
                            values ('%s', '%s')
                            """.formatted(user.getId(), existUser.getId()))
                    .executeUpdate();
        }
    }

    /**
     * @param checkingUsersEntity all received users
     * @return null if user not found in db, else return persisted entity
     * @throws NoSuchElementException if no one user exists on the database
     */
    private UsersEntity checkUsersExisting(UsersEntity checkingUsersEntity) {

        final List<UsersEntity> usersEntityList = entityManager.createNamedQuery("findByUsername", UsersEntity.class)
                .setParameter("username", checkingUsersEntity.getUsername())
                .getResultStream().map(Objects::requireNonNull).toList();

        for (UsersEntity usersEntity : usersEntityList) {
            if (!usersEntity.getUsername().isEmpty()) return usersEntity;
            else throw new NoSuchElementException("Friends not exist in database");
        }
        return null;
    }
}
