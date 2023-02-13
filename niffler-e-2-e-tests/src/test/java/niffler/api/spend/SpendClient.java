package niffler.api.spend;

import niffler.api.spec.Specification;
import niffler.data.json.SpendJson;

import java.io.IOException;

public class SpendClient {

    private final SpendService spendService = Specification
            .request(SpendService.baseUrl)
            .create(SpendService.class);

    public SpendJson createSpendJson(SpendJson json) throws IOException {
        return spendService.addSpend(json).execute().body();
    }
}
