package niffler.database.dao;

import niffler.database.DataBase;
import niffler.database.entity.userdata.ProfileEntity;
import niffler.database.jpa.EmfContext;
import niffler.database.jpa.JpaService;

public class UserdataRepository extends JpaService {

    public UserdataRepository() {
        super(EmfContext.INSTANCE.getEmf(DataBase.USERDATA).createEntityManager());
    }

    public void addProfiles(ProfileEntity... profileEntities) {
        for (ProfileEntity profileEntity : profileEntities) {
            persist(profileEntity);
        }
    }

    public void updateProfile(ProfileEntity profileEntity) {
        merge(profileEntity);
    }

    public void removeAll(ProfileEntity... profileEntities) {
        for (ProfileEntity profileEntity : profileEntities) {
            remove(profileEntity);
        }
    }
}
