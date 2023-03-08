package niffler.condition;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;

import static com.codeborne.selenide.CheckResult.Verdict.ACCEPT;
import static com.codeborne.selenide.CheckResult.Verdict.REJECT;
import static java.util.Objects.requireNonNull;

public class PhotoCondition {

    public static Condition photo(String expectedPhoto) {
        return new Condition("photo") {
            @SneakyThrows
            @Nonnull
            @Override
            public CheckResult check(Driver driver, WebElement element) {

                final ClassLoader cl = PhotoCondition.class.getClassLoader();

                try (InputStream is = cl.getResourceAsStream(expectedPhoto)) {
                    final byte[] expectedPhoto = Base64.getEncoder()
                            .encode(requireNonNull(is, "Img not found in classpath").readAllBytes());

                    final String src = element.getAttribute("src");
                    final byte[] actualPhoto = StringUtils.substringAfter(src, "base64,").getBytes();

                    final boolean photosIsEqual = Arrays.equals(expectedPhoto, actualPhoto);

                    return new CheckResult(photosIsEqual ? ACCEPT : REJECT, null);
                }
            }
        };
    }
}
