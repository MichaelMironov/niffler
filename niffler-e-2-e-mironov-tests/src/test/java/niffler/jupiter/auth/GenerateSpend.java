package niffler.jupiter.auth;

import niffler.data.enums.CurrencyValues;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static niffler.data.enums.CurrencyValues.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface GenerateSpend {

    double amount();
    CurrencyValues currency() default USD;
    String category();
    String date() default "";
    String description() default "";
}
