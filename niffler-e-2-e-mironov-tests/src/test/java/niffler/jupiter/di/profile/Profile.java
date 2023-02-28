package niffler.jupiter.di.profile;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@ExtendWith(EntityGenerator.class)
public @interface Profile {

    String username() default "";

    String password() default "";

    String currency() default "USD";

    String firstname() default "test";

//    enum UserEntityType {
//        PROFILE, AUTH
//    }

}
