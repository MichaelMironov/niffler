package niffler.tests.db;

import io.qameta.allure.AllureId;
import niffler.database.entity.auth.Authority;
import niffler.database.entity.auth.AuthorityEntity;
import niffler.database.entity.auth.UserEntity;
import niffler.database.repostiory.UserAuthRepository;
import niffler.jupiter.di.auth.AuthUser;
import niffler.jupiter.di.auth.AuthoriseExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;

import static niffler.database.entity.auth.Authority.read;
import static niffler.database.entity.auth.Authority.write;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith(AuthoriseExtension.class)
class AuthorityTest {

    @AllureId("5")
    @Test
    void createUserWithReadAuthorityTest(@AuthUser(username = "OnlyRead", password = "1234") UserEntity userEntity) {
        new UserAuthRepository().createUserWithReadAuthority(userEntity);
        assertEquals(read, new UserAuthRepository().getByUsername(userEntity.getUsername()).getAuthorities().iterator().next().getAuthority());
    }

    @AllureId("6")
    @Test
    void createUserWithReadAndWriteAuthorityTest(@AuthUser(username = "WriteRed", password = "1234") UserEntity userEntity) {
        new UserAuthRepository().createUserWithReadAndWriteAuthority(userEntity);
        final List<Authority> collect = new UserAuthRepository()
                .getByUsername(userEntity.getUsername())
                .getAuthorities().stream()
                .map(AuthorityEntity::getAuthority).toList();

       assertTrue(collect.containsAll(List.of(read, write)));
    }

}

