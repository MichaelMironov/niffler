package niffler.mapper;

import lombok.RequiredArgsConstructor;
import niffler.database.dto.UserReadDto;
import niffler.database.entity.user.User;

@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto mapFrom(User object) {
        return new UserReadDto(
                object.getId(),
                object.getCredentials(),
                object.getAccountStatus(),
                object.getAuthorities());
    }
}

