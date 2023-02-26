package niffler.api.spend;

import niffler.api.spec.RestService;
import niffler.data.json.CategoryJson;
import niffler.data.json.SpendJson;

public class SpendClient extends RestService {

    public SpendClient() {
        super(CFG.spendUrl());
    }

    private final SpendApi spendApi = retrofit.create(SpendApi.class);

    public SpendJson createSpend(SpendJson spend) throws Exception {
        return spendApi.addSpend(spend)
                .execute()
                .body();
    }

    public CategoryJson createCategory(CategoryJson category) throws Exception {
        return spendApi.addCategory(category)
                .execute()
                .body();
    }
}
