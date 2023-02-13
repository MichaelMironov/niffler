package niffler.tests.ui;

import com.codeborne.selenide.Condition;
import io.qameta.allure.AllureId;
import lombok.extern.slf4j.Slf4j;
import niffler.jupiter.di.user.User;
import niffler.jupiter.di.user.UsersSupplier;
import niffler.data.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static niffler.jupiter.di.user.User.UserType.ADMIN;
import static niffler.jupiter.di.user.User.UserType.COMMON;

@Slf4j
@ExtendWith(UsersSupplier.class)
class NifflerLoginTest extends BaseTest {

    @AllureId("1")
    @Test
    void test_1(@User(COMMON) UserModel userModel) {
        System.out.println("Test_1. " + userModel);
        step("Check login", () -> {
            open(PROPS.authUrl());
            $("a[href*='redirect']").click();
            $("input[name='username']").setValue(userModel.getUsername());
            $("input[name='password']").setValue(userModel.getPassword());
            $("button[type='submit']").click();
            $(".header__title").shouldBe(Condition.visible)
                    .shouldHave(Condition.text("Niffler. The coin keeper."));
        });
    }

    @AllureId("2")
    @Test
    void test_2(@User(COMMON) UserModel first, @User(ADMIN) UserModel second) {
        System.out.println("Test_2. Users: " + first + "\t" + second);
        step("Check login", () -> {
            open(PROPS.authUrl());
            $("a[href*='redirect']").click();
            $("input[name='username']").setValue(second.getUsername());
            $("input[name='password']").setValue(second.getPassword());
            $("button[type='submit']").click();
            $(".header__title").shouldBe(Condition.visible)
                    .shouldHave(Condition.text("Niffler. The coin keeper."));
        });
    }

    @AllureId("3")
    @Test
    void test_3(@User(COMMON) UserModel userModel) {
        System.out.println("Test_3. " + userModel);
        step("Check login", () -> {
            open(PROPS.authUrl());
            $("a[href*='redirect']").click();
            $("input[name='username']").setValue("test");
            $("input[name='password']").setValue("12345");
            $("button[type='submit']").click();
            $(".header__title").shouldBe(Condition.visible)
                    .shouldHave(Condition.text("Niffler. The coin keeper."));
        });
    }

    @AllureId("4")
    @Test
    void test_4(@User(COMMON) UserModel userModel) {
        System.out.println("Test_4. " + userModel);
        step("Check login", () -> {
            open(PROPS.authUrl());
            $("a[href*='redirect']").click();
            $("input[name='username']").setValue("test");
            $("input[name='password']").setValue("12345");
            $("button[type='submit']").click();
            $(".header__title").shouldBe(Condition.visible)
                    .shouldHave(Condition.text("Niffler. The coin keeper."));
        });
    }

    @AllureId("5")
    @Test
    void test_5(@User(COMMON) UserModel userModel) {
        System.out.println("Test_5. " + userModel);
        step("Check login", () -> {
            open(PROPS.authUrl());
            $("a[href*='redirect']").click();
            $("input[name='username']").setValue("test");
            $("input[name='password']").setValue("12345");
            $("button[type='submit']").click();
            $(".header__title").shouldBe(Condition.visible)
                    .shouldHave(Condition.text("Niffler. The coin keeper."));
        });
    }

    @AllureId("6")
    @Test
    void test_6(@User(COMMON) UserModel userModel) {
        System.out.println("Test_6. " + userModel);
        step("Check login", () -> {
            open(PROPS.authUrl());
            $("a[href*='redirect']").click();
            $("input[name='username']").setValue("test");
            $("input[name='password']").setValue("12345");
            $("button[type='submit']").click();
            $(".header__title").shouldBe(Condition.visible)
                    .shouldHave(Condition.text("Niffler. The coin keeper."));
        });
    }

}
