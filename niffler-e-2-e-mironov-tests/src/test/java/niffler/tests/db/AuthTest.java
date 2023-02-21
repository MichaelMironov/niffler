package niffler.tests.db;

import niffler.database.entity.users.User;
import niffler.jupiter.di.auth.AuthoriseExtension;
import niffler.jupiter.di.auth.UserEntity;
import niffler.jupiter.di.session.SessionExtension;
import niffler.tests.ui.steps.LoginSteps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static niffler.database.entity.authorities.Authority.read;
import static niffler.database.entity.authorities.Authority.write;

@ExtendWith({SessionExtension.class, AuthoriseExtension.class})
public class AuthTest {

    @Test
    void test(@UserEntity(username = "Final", password = "123456", authorities = {read, write}) User user) {

        new LoginSteps().login(user.getUsername(), user.getPassword());

    }

}

