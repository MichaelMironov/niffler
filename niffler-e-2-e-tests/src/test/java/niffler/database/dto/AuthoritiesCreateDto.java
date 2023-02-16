package niffler.database.dto;

import niffler.database.entity.authorities.Authority;
import niffler.database.entity.user.User;

public record AuthoritiesCreateDto(User user, Authority authority) {
}
