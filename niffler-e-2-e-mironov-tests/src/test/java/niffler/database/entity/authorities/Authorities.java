package niffler.database.entity.authorities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import niffler.database.entity.users.User;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Authorities {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    User user;
    @Enumerated(EnumType.STRING)
    Authority authority;
}
