package niffler.pages.component;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static niffler.condition.PhotoCondition.photo;

public class Header extends BaseComponent<Header> {

    private final SelenideElement avatar = self.$(".header__avatar");

    public Header() {
        super($(".header"));
    }

    @Step("Check header avatar")
    public Header checkProfilePhoto(String expectedPhoto){
        avatar.shouldHave(photo(expectedPhoto));
        return this;
    }
}
