package niffler.database;

import niffler.database.dao.AuthoritiesRepository;
import niffler.database.dao.UserRepository;
import niffler.database.dto.UserCreateDto;
import niffler.database.entity.authorities.Authorities;
import niffler.database.entity.authorities.Authority;
import niffler.database.entity.user.AccountStatus;
import niffler.database.entity.user.Credentials;
import niffler.database.entity.user.User;
import niffler.database.service.UserService;
import niffler.mapper.AuthoritiesReadMapper;
import niffler.mapper.UserCreateMapper;
import niffler.mapper.UserReadMapper;
import niffler.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.Proxy;
import java.util.UUID;

import static niffler.database.entity.authorities.Authority.WRITE;

public class HibernateRunner {
    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            final Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

            session.beginTransaction();

            final AuthoritiesReadMapper authoritiesReadMapper = new AuthoritiesReadMapper();
            final UserReadMapper userReadMapper = new UserReadMapper(authoritiesReadMapper);
            final UserCreateMapper mapper = new UserCreateMapper();
            final AuthoritiesRepository authoritiesRepository = new AuthoritiesRepository(session);
            final UserRepository userRepository = new UserRepository(session);
            final UserService userService = new UserService(userRepository, userReadMapper, mapper);

            UserCreateDto userDto = new UserCreateDto(Credentials.builder().username("auto1").password("test").build(),
                    AccountStatus.builder().enabled(true).credentialsNonExpired(true).accountNonExpired(true).accountNonLocked(true).build(),
                    Authorities.builder().authority(WRITE).build());

//            userService.create(userDto)

//            final UserReadDto userReadDto = userService.findById(UUID.fromString("eb3427af-b8cf-4029-8119-dc22ed5598d8")).get();
//            System.out.println(userReadDto.authorities().authority().getText());

            session.getTransaction().commit();

//        final User userWithReadAndWriteAuthority = new UserRepository().createUserWithAuthority(user, WRITE);

//        assertEquals("write", userWithReadAndWriteAuthority.getAuthorities().getAuthority().getText());
//        assertEquals(user.getCredentials().getUsername(), userWithReadAndWriteAuthority.getCredentials().getUsername());
//        Authorities authorities = Authorities.builder()
//                .id(UUID.fromString("6aee7b8a-aca0-11ed-a1f0-0242ac110002"))
//                .authority(WRITE)
//                .userId(UUID.fromString("258aa614-4f48-4ffc-98e7-c15103cc8957")).build();
//
//        System.out.println(getAuthorities(authorities));
//        createUserWithReadAndWriteAuthority(user, WRITE);

//        delete(getUser());
        }
    }

    public static void createRead() {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = User.builder().credentials(Credentials.builder()
                            .username("yy7")
                            .password("1111").build())
                    .accountStatus(AccountStatus.builder()
                            .credentialsNonExpired(true)
                            .accountNonLocked(true)
                            .accountNonExpired(true)
                            .enabled(true).build()).build();


            Authorities authorities = Authorities.builder()
                    .user(user)
                    .id(user.getId())
                    .authority(WRITE).build();

            authorities.setUser(user);

            session.persist(user);


            session.getTransaction().commit();

        }

    }

    public static User getUser() {
        return User.builder()
                .credentials(Credentials.builder()
                        .username("Te234")
                        .password("1234").build())
                .accountStatus(AccountStatus.builder()
                        .enabled(true)
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .build()).build();
    }

    public static void delete(User user1) {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(Authority.class);
        configuration.addAnnotatedClass(User.class);
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            deleteAuthority();
            session.delete(user1);
            session.getTransaction().commit();
        }
    }

    public static void deleteAuthority() {
//
//        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//             Session session = sessionFactory.openSession()) {
//
//            session.beginTransaction();
//
//            Authorities authorities = Authorities.builder()
//                    .id(UUID.fromString("6aee7b8a-aca0-11ed-a1f0-0242ac110002"))
//                    .authority(WRITE)
////                    .userId(UUID.fromString("258aa614-4f48-4ffc-98e7-c15103cc8957")).build();
//            session.delete(authorities);
//            session.flush();
//        }
    }

    public static User createUserWithReadAndWriteAuthority(User user1, Authority authority) {

//        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//            final Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            session.persist(user);
//
//            Authorities authorities = Authorities.builder().authority(authority).userId(user.getId()).build();
//            session.persist(authorities);
//
//            session.getTransaction().commit();
//
//        }

        return user1;
    }

    public static User get(User user1, String id) {

        User found;
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            found = session.get(User.class, UUID.fromString(id));
            session.getTransaction().commit();
        }
        return found;
    }

    public static void addAuthorities(Authority authority, User user1) {

        Authorities authorities = new Authorities();
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();
//            authorities.setUserId(user.getId());
            authorities.setAuthority(authority);
            session.persist(authorities);

            session.getTransaction().commit();
        }
    }

    public static Authorities getAuthorities(Authorities authorities) {

        Authorities found;
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            found = session.get(Authorities.class, authorities.getId());

            session.getTransaction().commit();
        }
        return found;
    }

    public static User createUser(User user1) {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.persist(user1);

            session.getTransaction().commit();
        }

        return user1;
    }


}
