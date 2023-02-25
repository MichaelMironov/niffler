package niffler.database.dao;

import jakarta.persistence.EntityManager;
import niffler.database.entity.userdata.Users;

import java.util.*;

public class UsersDataRepository extends RepositoryBase<UUID, Users> {
    public UsersDataRepository(EntityManager entityManager) {
        super(Users.class, entityManager);
    }

    public void addAll(Users... users) {
        for (Users user : users) {
            entityManager.persist(user);
        }
    }

    public void delete(Users... users) {
        for (Users user : users) {
            entityManager.remove(entityManager.createNamedQuery("findByUsername", Users.class)
                    .setParameter("username", user.getUsername())
                    .getSingleResult());
        }
    }

    public void addFriendsToUser(List<Users> friends, Users user) {

        final List<Users> allUsers = new ArrayList<>(friends);
        allUsers.add(user);

        //Checking for adding a non-existent user
        final List<Users> usersList = allUsers.stream().map(this::checkUsersExisting).toList();

        //Adding existing users
        List<Users> existUsers = new ArrayList<>();
        for (Users users : usersList) {
            if (users != null) {
                existUsers.add(users);
                if (Objects.equals(users.getUsername(), user.getUsername()))
                    user = users;
            }
        }

        //Adding existing users into friends table
        for (Users existUser : existUsers) {
            entityManager.createNativeQuery("""
                            insert into friends (user_id, friend_id)
                            values ('%s', '%s')
                            """.formatted(user.getId(), existUser.getId()))
                    .executeUpdate();
        }
    }

    /**
     * @param checkingUsers all received users
     * @return null if user not found in db, else return persisted entity
     * @throws NoSuchElementException if no one user exists on the database
     */
    private Users checkUsersExisting(Users checkingUsers) {

        final List<Users> usersList = entityManager.createNamedQuery("findByUsername", Users.class)
                .setParameter("username", checkingUsers.getUsername())
                .getResultStream().map(Objects::requireNonNull).toList();

        for (Users users : usersList) {
            if (!users.getUsername().isEmpty()) return users;
            else throw new NoSuchElementException("Friends not exist in database");
        }
        return null;
    }
}
