package niffler.jupiter.di.auth;

import niffler.database.entity.auth.Authority;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(AuthoriseExtension.class)
public @interface UserEntity {

    String username();
    String password();
    Authority[] authorities();
}
