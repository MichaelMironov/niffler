package niffler.jupiter.di;

import niffler.database.entity.Currency;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ExtendWith(SpendInjector.class)
public @interface WithSpend {

    double amount();
    Currency currency();
    String category();
    String description();
}
