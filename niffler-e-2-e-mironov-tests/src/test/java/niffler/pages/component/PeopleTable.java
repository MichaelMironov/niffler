package niffler.pages.component;

import com.codeborne.selenide.*;
import niffler.data.json.FriendJson;
import niffler.data.json.UserJson;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class PeopleTable extends BaseComponent<PeopleTable>{

    private final ElementsCollection row = self.$$("tr");
    public PeopleTable() {
        super($("table[class*='abstract-table'] tbody"));
    }

    public PeopleTable checkPeople(String username){
        removeFooter();
        row.filter(Condition.text(username)).get(0).shouldBe(Condition.exist);
        return this;
    }

    public PeopleTable addFriend(String username){
        removeFooter();
        row.filter(Condition.text(username)).get(0).$$("td")
                .last().find(By.tagName("button")).shouldBe(Condition.visible).click();
        return this;
    }

    private void removeFooter() {
        executeJavaScript("const element = document.getElementsByTagName('footer')[0];\n" +
                         "element.remove();");
    }
}
