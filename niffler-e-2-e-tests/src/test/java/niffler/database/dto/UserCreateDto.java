package niffler.database.dto;

import niffler.database.entity.authorities.Authorities;
import niffler.database.entity.user.AccountStatus;
import niffler.database.entity.user.Credentials;

public record UserCreateDto(
        Credentials credentials,
                            AccountStatus accountStatus,
                            Authorities authorities) {
}
