package niffler.condition;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.impl.CollectionSource;
import niffler.data.enums.CurrencyValues;
import niffler.data.json.SpendJson;
import niffler.utils.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SpendCondition {

    public static CollectionCondition spends(SpendJson... expectedSpends) {
        return new CollectionCondition() {
            @Override
            public void fail(CollectionSource collection, @Nullable List<WebElement> elements, @Nullable Exception lastError, long timeoutMs) {
                if (elements == null || elements.isEmpty()) {
                    ElementNotFound elementNotFound = new ElementNotFound(collection, toString(), lastError);
                    elementNotFound.timeoutMs = timeoutMs;
                    throw elementNotFound;
                } else if (elements.size() != expectedSpends.length) {
                    throw new SpendsSizeMismatch(collection, Arrays.asList(expectedSpends), bindElementsToSpends(elements), explanation, timeoutMs);
                } else {
                    throw new SpendsMismatch(collection, Arrays.asList(expectedSpends), bindElementsToSpends(elements), explanation, timeoutMs);
                }
            }

            @Override
            public boolean missingElementSatisfiesCondition() {
                return false;
            }

            @Override
            public boolean test(List<WebElement> elements) {
                if (elements.size() != expectedSpends.length)
                    return false;
                for (int i = 0; i < expectedSpends.length; i++) {
                    WebElement row = elements.get(i);
                    SpendJson expectedSpend = expectedSpends[i];
                    final List<WebElement> cells = row.findElements(By.cssSelector("td"));

                    if (!cells.get(1).getText().equals(DateUtils.getDateAsString(expectedSpend.getSpendDate())))
                        return false;

                    if (!Double.valueOf(cells.get(2).getText()).equals(expectedSpend.getAmount()))
                        return false;

                    if (!cells.get(3).getText().equals(expectedSpend.getCurrency().name()))
                        return false;

                    if (!cells.get(4).getText().equals(expectedSpend.getCategory()))
                        return false;

                    if (!cells.get(5).getText().equals(expectedSpend.getDescription()))
                        return false;

                }
                return true;
            }

            private List<SpendJson> bindElementsToSpends(List<WebElement> elements) {
                return elements.stream()
                        .map(e -> {
                            List<WebElement> cells = e.findElements(By.cssSelector("td"));
                            SpendJson actual = new SpendJson();
                            actual.setSpendDate(DateUtils.fromString(cells.get(1).getText()));
                            actual.setAmount(Double.valueOf(cells.get(2).getText()));
                            actual.setCurrency(CurrencyValues.valueOf(cells.get(3).getText()));
                            actual.setCategory(cells.get(4).getText());
                            actual.setDescription(cells.get(5).getText());
                            return actual;
                        })
                        .collect(Collectors.toList());
            }

        };
    }
}
