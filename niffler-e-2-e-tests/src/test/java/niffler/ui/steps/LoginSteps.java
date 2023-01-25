package niffler.ui.steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import niffler.utils.PropertiesUtil;
import org.aeonbits.owner.ConfigFactory;

import static com.codeborne.selenide.Selenide.$;

public class LoginSteps {

    private static final PropertiesUtil PROPS = ConfigFactory.create(PropertiesUtil.class);

    public static void login() {

        Selenide.open(PROPS.authUrl());
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(PROPS.login());
        $("input[name='password']").setValue(PROPS.password());
        $("button[type='submit']").click();
        $(".header__title").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Niffler. The coin keeper."));
    }
}
