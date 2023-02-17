package niffler.database;

import jakarta.transaction.Transactional;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import niffler.database.dao.AuthoritiesRepository;
import niffler.database.dao.UserRepository;
import niffler.database.dto.AuthoritiesCreateDto;
import niffler.database.dto.UserCreateDto;
import niffler.database.entity.authorities.Authorities;
import niffler.database.entity.authorities.Authority;
import niffler.database.entity.user.AccountStatus;
import niffler.database.entity.user.Credentials;
import niffler.database.entity.user.User;
import niffler.database.interceptor.TransactionInterceptor;
import niffler.database.service.AuthoritiesService;
import niffler.database.service.UserService;
import niffler.mapper.AuthoritiesCreateMapper;
import niffler.mapper.AuthoritiesReadMapper;
import niffler.mapper.UserCreateMapper;
import niffler.mapper.UserReadMapper;
import niffler.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class HibernateRunner {

    @Transactional
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            final Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

//            session.beginTransaction();

            final UserRepository userRepository = new UserRepository(session);
            final AuthoritiesRepository authoritiesRepository = new AuthoritiesRepository(session);
            final AuthoritiesReadMapper authoritiesReadMapper = new AuthoritiesReadMapper();
            final UserReadMapper userReadMapper = new UserReadMapper(authoritiesReadMapper);
            final AuthoritiesCreateMapper authoritiesCreateMapper = new AuthoritiesCreateMapper(userRepository);
            final UserCreateMapper userCreateMapper = new UserCreateMapper();

            final TransactionInterceptor transactionInterceptor = new TransactionInterceptor(sessionFactory);

            final UserService userService = new ByteBuddy().subclass(UserService.class).method(ElementMatchers.any()).intercept(MethodDelegation.to(transactionInterceptor))
                    .make().load(UserService.class.getClassLoader()).getLoaded()
                    .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class)
                    .newInstance(userRepository, userReadMapper, userCreateMapper);

            final AuthoritiesService authoritiesService = new ByteBuddy().subclass(AuthoritiesService.class).method(ElementMatchers.any()).intercept(MethodDelegation.to(transactionInterceptor))
                    .make().load(AuthoritiesService.class.getClassLoader()).getLoaded()
                    .getDeclaredConstructor(AuthoritiesRepository.class, AuthoritiesReadMapper.class, AuthoritiesCreateMapper.class)
                    .newInstance(authoritiesRepository, authoritiesReadMapper, authoritiesCreateMapper);

//            final AuthoritiesService authoritiesService = new AuthoritiesService(authoritiesRepository, authoritiesReadMapper, authoritiesCreateMapper, userRepository);

//            User user = User.builder()
//                    .id(UUID.randomUUID())
//                    .credentials(Credentials.builder()
//                            .username("yy7123")
//                            .password("1111").build())
//                    .accountStatus(AccountStatus.builder()
//                            .credentialsNonExpired(true)
//                            .accountNonLocked(true)
//                            .accountNonExpired(true)
//                            .enabled(true).build()).build();

//            final UserService userService = new UserService(userRepository, userReadMapper, new UserCreateMapper());


            final User at = userService.create(new UserCreateDto(Credentials.builder().username("AT6").password("123").build(),
                    AccountStatus.builder().enabled(true).accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true).build(),
                    Authorities.builder().authority(Authority.WRITE).build()));

            System.out.println(at);

            final AuthoritiesCreateDto authoritiesCreateDto = new AuthoritiesCreateDto(at, Authority.WRITE);

            authoritiesService.createUserWithAuthority(authoritiesCreateDto);


//            authoritiesService.createUserWithAuthority(authoritiesCreateDto);

//            authoritiesService.findById(UUID.fromString("e1db0b4d-b401-45ec-8850-398152676eb7")).ifPresent(System.out::println);




//
//
//            Authorities authorities = Authorities.builder()
//                    .user(user)
//                    .id(user.getId())
//                    .authority(WRITE).build();
//
//            final AuthoritiesCreateDto authoritiesCreateDto = new AuthoritiesCreateDto(user, WRITE);

//            authoritiesService.createUserWithAuthority(authoritiesCreateDto);

//            final UserReadDto userReadDto = userService.findById(UUID.fromString("eb3427af-b8cf-4029-8119-dc22ed5598d8")).get();
//            System.out.println(userReadDto.authorities().authority().getText());

//            session.getTransaction().commit();

        }
    }
}
