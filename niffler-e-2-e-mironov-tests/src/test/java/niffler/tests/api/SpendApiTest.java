package niffler.tests.api;

import niffler.api.spend.SpendClient;
import niffler.api.user.UserClient;
import niffler.data.json.SpendJson;
import niffler.data.json.UserJson;
import niffler.jupiter.converters.spend.Spend;
import niffler.jupiter.converters.user.User;
import niffler.jupiter.di.client.Client;
import niffler.jupiter.di.client.ClientSupplier;
import niffler.tests.ui.steps.LoginSteps;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ClientSupplier.class)
public class SpendApiTest {

    private @Client
    SpendClient spendClient;
    private @Client
    UserClient userClient;

    @ValueSource(strings = {"data/spend.json"})
    @ParameterizedTest
    void addSpend(@Spend SpendJson spend) throws IOException {
        final SpendJson created = spendClient.createSpendJson(spend);
        assertNotNull(created.getId());
    }

    @ValueSource(strings = "data/user.json")
    @ParameterizedTest
    void updateUserProfileTest(@User UserJson user) throws IOException {
        final UserJson created = userClient.updateUser(user);
        assertEquals("Jordan", created.getSurname());
    }
}
