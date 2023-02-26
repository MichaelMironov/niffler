package niffler.tests.api;

import niffler.api.spend.SpendClient;
import niffler.api.user.UserClient;
import niffler.data.json.SpendJson;
import niffler.data.json.UserJson;
import niffler.jupiter.converters.spend.Spend;
import niffler.jupiter.converters.user.User;
import niffler.jupiter.di.client.ClientSupplier;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ClientSupplier.class)
public class ApiTests {

    SpendClient spendClient = new SpendClient();
    UserClient userClient = new UserClient();

    @ValueSource(strings = {"data/spend.json", "data/spend1.json"})
    @ParameterizedTest
    void addSpend(@Spend SpendJson spend) throws Exception {
        final SpendJson created = spendClient.createSpend(spend);
        assertNotNull(created.getId());
    }

    @ValueSource(strings = "data/user.json")
    @ParameterizedTest
    void updateUserProfileTest(@User UserJson user) throws IOException {
        final UserJson created = userClient.updateUser(user);
        assertEquals("Jordan", created.getSurname());
    }
}
