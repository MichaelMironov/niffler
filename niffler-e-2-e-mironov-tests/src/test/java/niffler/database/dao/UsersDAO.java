package niffler.database.dao;

import niffler.database.entity.userdata.ProfileEntity;

public interface UsersDAO {

    ProfileEntity addUser(ProfileEntity users);

    void updateUser(ProfileEntity users);

    void remove(ProfileEntity user);

    ProfileEntity getByUsername(String username);

}
