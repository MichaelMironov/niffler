package niffler.jupiter.di.user;

import niffler.jupiter.auth.CreateUserExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface User {

    UserType value() default UserType.COMMON;

    CreateUserExtension.Selector selector() default CreateUserExtension.Selector.NESTED;

    enum UserType {
        ADMIN, COMMON
    }
}
