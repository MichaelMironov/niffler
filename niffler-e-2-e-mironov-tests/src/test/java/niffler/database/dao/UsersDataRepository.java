package niffler.database.dao;

import jakarta.persistence.EntityManager;
import niffler.database.entity.userdata.Users;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.devtools.v85.runtime.Runtime;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UsersDataRepository extends RepositoryBase<UUID, Users> {
    public UsersDataRepository(EntityManager entityManager) {
        super(Users.class, entityManager);
    }

    public void addFriends(Users user, List<Users> friends) {

        final List<Users> allUsers = new ArrayList<>(friends);
        allUsers.add(user);

        System.out.println(allUsers);
        final List<Users> usersList = allUsers.stream().map(this::checkUsersExisting).toList();
        System.out.println( "AFTER REQUEST" +usersList);


        for (Users users : usersList) {

            System.out.println(users.getId());
        }


        usersList.forEach(friend ->{
            entityManager.createNativeQuery("""
                insert into friends (user_id, friend_id)
                values (%s, %s)
                """.formatted(user.getId(), friend.getId()));
        });
    }

    private Users checkUsersExisting(Users checkingUsers) {

        final List<Users> usersList = entityManager.createNamedQuery("findByUsername", Users.class)
                .setParameter("username", checkingUsers.getUsername())
                .getResultStream().map(Objects::requireNonNull).toList();

        final ArrayList<Users> arrayList = new ArrayList<>();
        for (Users users : usersList) {
            if( !users.toString().isEmpty() && !users.toString().isBlank())
                System.out.println(users);
                arrayList.add(users);
//            if (!users.getUsername().isEmpty()) return users;
//            else throw new NoSuchElementException("Friends not exist in database");
        }
        return arrayList.get(0);
    }
}
