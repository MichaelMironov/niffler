package niffler.tests.db;

import niffler.database.dto.UserCreateDto;
import niffler.database.dto.UserReadDto;
import niffler.database.service.UserService;
import niffler.jupiter.di.auth.AuthUser;
import niffler.jupiter.di.auth.AuthoriseExtension;
import niffler.jupiter.di.services.DatabaseSession;
import niffler.jupiter.di.services.WithService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;
import java.util.UUID;

import static niffler.database.entity.authorities.Authority.WRITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DatabaseSession
@ExtendWith(AuthoriseExtension.class)
class UsersTest {

    @WithService
    UserService userService;

    @Test
    void createUserTest(@AuthUser(username = "1439", password = "1234", authority = WRITE) UserCreateDto expectedUser) {

        System.out.println(expectedUser);

        final UUID createdIdUser = userService.create(expectedUser);

        final Optional<UserReadDto> probableUser = userService.findById(createdIdUser);
        final UserReadDto actualUser = probableUser.orElseThrow();

        assertEquals(expectedUser.credentials().getUsername(), actualUser.credentials().getUsername());
        assertEquals(WRITE, actualUser.authority());
        System.out.println("DELETING");

//        System.out.println(userService.delete(UUID.fromString("3b85e6ce-25fb-44cd-b5b5-e4bead9641d8")));
//        System.out.println(userService.delete(UUID.fromString("18531a78-ac43-11ed-be7a-0242ac110002")));

        System.out.println(userService.findById(createdIdUser));
    }
}

