package niffler.database.entity.user;

import jakarta.persistence.*;
import lombok.*;
import niffler.database.entity.BaseEntity;
import niffler.database.entity.authorities.Authorities;

import java.util.UUID;

@NamedEntityGraph(
        name = "WithAuthorities",
        attributeNodes = {@NamedAttributeNode("authorities")}
)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"authorities"})
@Entity
@Table(name = "users", schema = "public")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements BaseEntity<UUID> {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Credentials credentials;
    private AccountStatus accountStatus;
    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false)
    private Authorities authorities;

}
