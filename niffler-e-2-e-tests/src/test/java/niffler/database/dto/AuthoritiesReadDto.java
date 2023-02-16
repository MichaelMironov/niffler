package niffler.database.dto;

import niffler.database.entity.authorities.Authority;
import niffler.database.entity.user.User;

import java.util.UUID;

public record AuthoritiesReadDto(UUID id, UUID userId, Authority authority) {

}
