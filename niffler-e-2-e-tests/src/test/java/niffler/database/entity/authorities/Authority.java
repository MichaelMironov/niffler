package niffler.database.entity.authorities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {
    READ("read"), WRITE("write");
    private final String text;
}
