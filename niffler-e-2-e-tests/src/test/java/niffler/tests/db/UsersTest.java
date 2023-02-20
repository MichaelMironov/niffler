package niffler.tests.db;

import niffler.database.dto.UserCreateDto;
import niffler.database.service.UserService;
import niffler.jupiter.di.auth.AuthUser;
import niffler.jupiter.di.auth.AuthoriseExtension;
import niffler.jupiter.di.services.DatabaseSession;
import niffler.jupiter.di.services.WithService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static niffler.database.entity.user.Authorities.read;
import static niffler.database.entity.user.Authorities.write;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DatabaseSession
@ExtendWith(AuthoriseExtension.class)
class UsersTest {

    @WithService
    UserService userService;

    @Test
    void createUserTest(@AuthUser(username = "Auth1", password = "1234", authorities = {write, read}) UserCreateDto expectedUser) {

        var createdIdUser = userService.create(expectedUser);
        var probableUser = userService.findById(createdIdUser);
        var actualUser = probableUser.orElseThrow();

        assertEquals(expectedUser.credentials().getUsername(), actualUser.credentials().getUsername());
        assertEquals(expectedUser.authorities(), actualUser.authorities());

    }
}

