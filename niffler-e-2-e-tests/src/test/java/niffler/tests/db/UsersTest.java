package niffler.tests.db;

import niffler.database.dto.UserCreateDto;
import niffler.database.dto.UserReadDto;
import niffler.database.service.UserService;
import niffler.jupiter.di.auth.AuthUser;
import niffler.jupiter.di.auth.AuthoriseExtension;
import niffler.jupiter.di.services.DatabaseSession;
import niffler.jupiter.di.services.WithUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;
import java.util.UUID;

import static niffler.database.entity.authorities.Authority.WRITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DatabaseSession
@ExtendWith(AuthoriseExtension.class)
class UsersTest {

    @AuthUser(username = "9", password = "1234", authority = WRITE) UserCreateDto expectedUser;

    @Test
    void createUserTest(@WithUserService UserService userService) {

        final UUID createdIdUser = userService.create(expectedUser);

        final Optional<UserReadDto> probableUser = userService.findById(createdIdUser);
        final UserReadDto actualUser = probableUser.orElseThrow();

        assertEquals(expectedUser.credentials().getUsername(), actualUser.credentials().getUsername());
        assertEquals(WRITE, actualUser.authority());
        System.out.println("DELETING");

//        System.out.println(userService.delete(UUID.fromString("d014f321-99ad-4b0a-ba22-f54083a6cd23")));

        System.out.println(userService.findById(createdIdUser));
    }
}

