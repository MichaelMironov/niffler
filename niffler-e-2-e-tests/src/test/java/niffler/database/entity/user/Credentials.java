package niffler.database.entity.user;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@Data
@NoArgsConstructor
@Builder
@Embeddable
public class Credentials implements Serializable {
    @Serial
    private static final long serialVersionUID = 6591759497968878222L;
    private String username;
    private String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = createDelegatingPasswordEncoder().encode(password);
    }
}
