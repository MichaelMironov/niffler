package niffler.tests.api;

import niffler.api.SpendClient;
import niffler.model.SpendJson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

public class SpendApiTest {

    private final SpendClient spendClient = new SpendClient();

    @ValueSource(strings = {"data/spend.json", "data/spend1.json"})
    @ParameterizedTest
    void addSpend(@Spend SpendJson spend) throws IOException {
        final SpendJson created = spendClient.createSpendJson(spend);

        Assertions.assertNotNull(created);
    }
}
