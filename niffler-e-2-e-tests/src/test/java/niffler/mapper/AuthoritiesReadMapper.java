package niffler.mapper;

import lombok.RequiredArgsConstructor;
import niffler.database.dto.AuthoritiesReadDto;
import niffler.database.entity.authorities.Authorities;

@RequiredArgsConstructor
public class AuthoritiesReadMapper implements Mapper<Authorities, AuthoritiesReadDto> {

    @Override
    public AuthoritiesReadDto mapFrom(Authorities object) {
        return new AuthoritiesReadDto(
                object.getId(),
                object.getUser().getId(),
                object.getAuthority());
    }
}
