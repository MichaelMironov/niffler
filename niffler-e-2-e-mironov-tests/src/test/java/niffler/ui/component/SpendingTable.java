package niffler.ui.component;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class SpendingTable extends Component<SpendingTable>{

    private final ElementsCollection spendingButtons = self.$$(".spendings__buttons button");

    protected SpendingTable(SelenideElement self) {
        super($(".main-content__section-history"));
    }

    @Step
    public SpendingTable clickByButton(String buttonText){
        spendingButtons.find(text(buttonText))
                .scrollIntoView(false)
                .click();
        return this;
    }
}
