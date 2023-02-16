package niffler.tests.ui.steps;

import niffler.database.dao.SpendsRepository;
import niffler.data.entity.Spend;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.textsInAnyOrder;
import static com.codeborne.selenide.Selenide.$$x;

public class CheckSteps {

    public void checkSpends(Spend spend){
        $$x("//table[@class='table spendings-table']//td")
                .shouldHave(textsInAnyOrder(spend.getSpendDate(), String.valueOf(spend.getAmount().intValue()), spend.getCurrency(), spend.getCategoryName(), spend.getDescription()));
    }
    public static void findAllSpends() {
        System.out.print("Записи в бд: ");
        final List<Spend> spends = SpendsRepository.getInstance().findAll();
        spends.forEach(System.out::println);
    }
}
