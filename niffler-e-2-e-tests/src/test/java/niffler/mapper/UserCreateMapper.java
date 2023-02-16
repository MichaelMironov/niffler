package niffler.mapper;

import niffler.database.dto.UserCreateDto;
import niffler.database.entity.user.User;

public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    @Override
    public User mapFrom(UserCreateDto object) {
        return User.builder()
                .credentials(object.credentials())
                .accountStatus(object.accountStatus())
                .authorities(object.authorities())
                .build();
    }
}
