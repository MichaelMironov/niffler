package niffler.utils;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataUtils {

    private static final Faker faker = new Faker();

    public static String generateRandomUsername() {
        return faker.name().username();
    }

    public static String generateRandomPassword() {
        return faker.bothify("????####");
    }
}
