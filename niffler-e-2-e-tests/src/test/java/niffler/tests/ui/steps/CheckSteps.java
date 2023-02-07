package niffler.tests.ui.steps;

import niffler.database.dao.SpendsDao;
import niffler.database.entity.Spends;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.textsInAnyOrder;
import static com.codeborne.selenide.Selenide.$$x;

public class CheckSteps {

    public static void checkSpends(String spendDate, String amount, String currency, String category, String description){
        $$x("//table[@class='table spendings-table']//td")
                .shouldHave(textsInAnyOrder(spendDate, amount, currency, category, description));
    }
    public static void findAllSpends() {
        System.out.print("Записи в бд: ");
        final List<Spends> spends = SpendsDao.getInstance().findAll();
        spends.forEach(System.out::println);
    }
}
