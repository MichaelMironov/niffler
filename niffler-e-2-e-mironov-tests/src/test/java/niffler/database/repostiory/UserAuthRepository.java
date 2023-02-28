package niffler.database.repostiory;

import niffler.database.dao.PostgresHibernateUsersDAO;
import niffler.database.entity.auth.AuthorityEntity;
import niffler.database.entity.auth.UserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static niffler.database.entity.auth.Authority.read;
import static niffler.database.entity.auth.Authority.write;

public class UserAuthRepository extends PostgresHibernateUsersDAO {

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public void createUserWithReadAuthority(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(Set.of(AuthorityEntity.builder().authority(read).user(user).build()));
        addUser(user);
    }

    public void createUserWithReadAndWriteAuthority(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(Set.of(
                AuthorityEntity.builder().authority(read).user(user).build(),
                AuthorityEntity.builder().authority(write).user(user).build()));
        addUser(user);
    }
}
