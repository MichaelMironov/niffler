package niffler.mapper;

import niffler.database.dto.AuthoritiesReadDto;
import niffler.database.entity.authorities.Authorities;

public class AuthoritiesReadMapper implements Mapper<Authorities, AuthoritiesReadDto> {
    @Override
    public AuthoritiesReadDto mapFrom(Authorities object) {
        return new AuthoritiesReadDto(
                object.getId(),
                object.getUser(),
                object.getAuthority()
        );
    }
}
