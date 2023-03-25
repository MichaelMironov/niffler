package niffler.pages;

import com.codeborne.selenide.Condition;
import niffler.pages.component.PeopleTable;

public class FriendsPage extends BasePage<FriendsPage>{
    private final PeopleTable peopleTable = new PeopleTable();

    public static final String URL = BasePage.CFG.frontUrl() + "friends";

    public void checkFriend(String username){
        peopleTable.checkPeople(username);
    }

    @Override
    public FriendsPage waitForPageLoaded() {
        peopleTable.getSelf().shouldBe(Condition.visible);
        return this;
    }
}
