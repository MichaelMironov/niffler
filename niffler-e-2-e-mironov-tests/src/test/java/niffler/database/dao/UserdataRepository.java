package niffler.database.dao;

import niffler.database.DataBase;
import niffler.database.jpa.EmfContext;
import niffler.database.jpa.JpaService;

public class UserdataRepository extends JpaService {

    public UserdataRepository() {
        super(EmfContext.INSTANCE.getEmf(DataBase.USERDATA).createEntityManager());
    }
}
