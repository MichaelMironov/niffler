package niffler.mapper;

import lombok.RequiredArgsConstructor;
import niffler.database.dao.UserRepository;
import niffler.database.dto.AuthoritiesCreateDto;
import niffler.database.entity.authorities.Authorities;

@RequiredArgsConstructor
public class AuthoritiesCreateMapper implements Mapper<AuthoritiesCreateDto, Authorities> {

    private final UserRepository userRepository;

    @Override
    public Authorities mapFrom(AuthoritiesCreateDto object) {
        return Authorities.builder()
                .id(object.user().getId())
                .user(object.user())
                .authority(object.authority())
                .build();
    }
}
