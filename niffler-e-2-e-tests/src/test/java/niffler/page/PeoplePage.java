package niffler.page;

import com.codeborne.selenide.SelenideElement;

public class PeoplePage extends BasePage<PeoplePage>{

    public static final String URL = CFG.frontUrl() + "peolple";

    private final SelenideElement

    @Override
    public PeoplePage waitForPageLoaded() {
        return null;
    }
}
