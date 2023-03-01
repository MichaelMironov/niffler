package niffler.jupiter.di.auth;

import jakarta.persistence.Column;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(AuthoriseExtension.class)
public @interface AuthUser {

    String username();
    String password();


    boolean enabled() default true;


    boolean accountNonExpired() default true;


    boolean accountNonLocked() default true;


    boolean credentialsNonExpired() default true;

}
