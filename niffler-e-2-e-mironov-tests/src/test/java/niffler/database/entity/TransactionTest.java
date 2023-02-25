package niffler.database.entity;

import jakarta.persistence.FlushModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import niffler.database.dao.UsersDataRepository;
import niffler.database.entity.userdata.Users;
import niffler.jupiter.di.session.SessionExtension;
import niffler.jupiter.di.session.WithRepository;
import niffler.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(SessionExtension.class)
public class TransactionTest {

    @WithRepository
    UsersDataRepository usersDataRepository;

    @Test
    void test2() {

        Users user = Users.builder().username("michael1").currency("KZT").firstname("mir").build();
        Users friend1 = Users.builder().username("galya4").currency("KZT").firstname("mir").build();
        Users friend2 = Users.builder().username("galya2").currency("KZT").firstname("mir").build();
        Users friend3 = Users.builder().username("galya500").currency("KZT").firstname("mir").build();

        user.addFriends(friend1, friend2, friend3);

        usersDataRepository.addFriends(user, List.of(friend1, friend2, friend3));
    }

    @Test
    void hqlTest() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             final Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            Users user = Users.builder().username("michael1").currency("KZT").firstname("mir").build();
            Users friend = Users.builder().username("galya4").currency("KZT").firstname("mir").build();


            var query = session.createNamedQuery("findByUsername", Users.class)
                    .setParameter("username", user.getUsername())
                    .setFlushMode(FlushModeType.COMMIT)
                    .list();

            var query2 = session.createNamedQuery("findByUsername", Users.class)
                    .setParameter("username", friend.getUsername())
                    .list();

            System.out.println();
            System.out.println(query);
            System.out.println(query2);

//            user.addFriend(friend);

//            session.merge(user);

            session.getTransaction().commit();

        }
    }

    @Test
    void test() {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             final Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            Users user = Users.builder().username("michael2").currency("KZT").firstname("mir").build();
            Users friend = Users.builder().username("alex12").currency("KZT").firstname("mir").build();
//            final Users merged = session.merge(user);
//
            final CriteriaQuery<Users> query = session.getCriteriaBuilder().createQuery(Users.class);
            query.from(Users.class);
            final List<Users> usersList = session.createQuery(query).getResultList();

            //TODO: ADD FRIEND METHOD
            final Users users1 = usersList.stream().filter(users -> users.getUsername().equals(user.getUsername())).findFirst().get();
//            final UsersDataPK id = userEntityUserdata1.getId();

            session.persist(friend);

            final CriteriaQuery<Users> query1 = session.getCriteriaBuilder().createQuery(Users.class);
            query.from(Users.class);
            final List<Users> usersList1 = session.createQuery(query).getResultList();

            final Users friend1 = usersList.stream().filter(users -> users.getUsername().equals(user.getUsername())).findFirst().get();


//            final boolean isUserHaveFriend = user.getFriends().stream().noneMatch(friends -> friends.getUsername().equals(friend.getUsername()));
//            if( isUserHaveFriend){
//                user.addFriend(friend);
////                session.createQuery("insert into friends")
////                session.update(Users.class, merged.getId());
//            }

            session.getTransaction().commit();

            System.out.println(friend);

        }
    }

}


//            final boolean isUserExist = usersList.stream().noneMatch(userFromList -> userFromList.getUsername().equals(user.getUsername()));

//            final boolean isUserHaveFriend = usersList.stream().allMatch(users -> users.getFriends().stream().noneMatch(users1 -> users1.getUsername().equals(friend.getUsername())));