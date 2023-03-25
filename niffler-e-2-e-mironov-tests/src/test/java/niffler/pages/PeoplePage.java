package niffler.pages;

import com.codeborne.selenide.SelenideElement;
import niffler.pages.component.PeopleTable;

public class PeoplePage extends BasePage<PeoplePage>{

    public static final String URL = BasePage.CFG.frontUrl() + "peolple";

    PeopleTable table = new PeopleTable();

    public PeopleTable getPeopleTable(){
        return table;
    }
//    @Override
    public PeoplePage waitForPageLoaded() {
        return null;
    }
}
