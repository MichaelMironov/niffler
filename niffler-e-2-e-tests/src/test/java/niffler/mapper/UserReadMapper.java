package niffler.mapper;

import lombok.RequiredArgsConstructor;
import niffler.database.dto.UserReadDto;
import niffler.database.entity.user.User;

import java.util.Optional;

@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final AuthoritiesReadMapper authoritiesReadMapper;

    @Override
    public UserReadDto mapFrom(User object) {
        return new UserReadDto(
                object.getId(),
                object.getCredentials(),
                object.getAccountStatus(),
                Optional.ofNullable(object.getAuthorities())
                        .map(authoritiesReadMapper::mapFrom)
                        .orElse(null));
    }
}

