package niffler.tests.db;

import niffler.database.repostiory.UserAuthRepository;
import niffler.database.entity.auth.UserEntity;
import org.junit.jupiter.api.Test;

class AuthTest {
    ////TODO: create genereated user in paramter resolver AUTH
//    @Test
//    void test(@UserEntity(username = "Final", password = "123456", authorities = {read, write}) User user) {
//
//        new LoginSteps().login(user.getUsername(), user.getPassword());
//
//    }

    @Test
    void createUserWithReadAuthorityTest() {
        UserEntity userEntity = UserEntity.builder()
                .username("AUTHTes4")
                .password("1234")
                .CredentialsNonExpired(true)
                .AccountNonLocked(true)
                .accountNonExpired(true)
                .enabled(true)
                .build();
        new UserAuthRepository().createUserWithReadAuthority(userEntity);
    }

    @Test
    void createUserWithReadAndWriteAuthorityTest() {
        UserEntity userEntity = UserEntity.builder()
                .username("AUTHTest5")
                .password("1234")
                .CredentialsNonExpired(true)
                .AccountNonLocked(true)
                .accountNonExpired(true)
                .enabled(true)
                .build();
        new UserAuthRepository().createUserWithReadAndWriteAuthority(userEntity);
    }

}

