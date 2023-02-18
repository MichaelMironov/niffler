package niffler.mapper;

import lombok.RequiredArgsConstructor;
import niffler.database.dto.UserCreateDto;
import niffler.database.entity.authorities.Authorities;
import niffler.database.entity.user.User;

@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    @Override
    public User mapFrom(UserCreateDto object) {
        final Authorities authorities = object.authorities();
        final User user = User.builder()
                .credentials(object.credentials())
                .accountStatus(object.accountStatus())
                .authorities(object.authorities())
                .build();
        authorities.setUser(user);
        return user;
    }
}
