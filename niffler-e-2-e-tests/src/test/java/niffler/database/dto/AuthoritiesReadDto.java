package niffler.database.dto;

import niffler.database.entity.user.User;

import java.util.UUID;

public record AuthoritiesReadDto(UUID id, User user) {

}
