package niffler.database.entity.user;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Credentials implements Serializable {
    @Serial
    private static final long serialVersionUID = 6591759497968878222L;
    private String username;
    private String password;
}
