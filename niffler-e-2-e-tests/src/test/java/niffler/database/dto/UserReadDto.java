package niffler.database.dto;

import niffler.database.entity.user.AccountStatus;
import niffler.database.entity.user.Credentials;
import niffler.database.entity.user.Authorities;

import java.util.List;
import java.util.UUID;

public record UserReadDto(UUID id,
                          Credentials credentials,
                          AccountStatus accountStatus, List<Authorities> authorities) {
}
