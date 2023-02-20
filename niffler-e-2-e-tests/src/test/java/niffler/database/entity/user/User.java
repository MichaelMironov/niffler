package niffler.database.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import niffler.database.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NamedEntityGraph(
        name = "WithAuthorities",
        attributeNodes = {@NamedAttributeNode("authorities")}
)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User implements BaseEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Credentials credentials;
    private AccountStatus accountStatus;

    @ElementCollection
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private List<Authorities> authorities = new ArrayList<>();

}
