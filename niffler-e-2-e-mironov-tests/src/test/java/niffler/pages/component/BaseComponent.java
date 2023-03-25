package niffler.pages.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Condition.*;

public abstract class BaseComponent<T extends BaseComponent> {

    protected final SelenideElement self;

    public BaseComponent(SelenideElement self) {
        this.self = self;
    }

    public SelenideElement getSelf() {
        return self;
    }
}
