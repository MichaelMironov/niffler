package niffler.database.entity.authorities;

import jakarta.persistence.*;
import lombok.*;
import niffler.database.entity.BaseEntity;
import niffler.database.entity.user.User;

import java.util.UUID;

@NamedEntityGraph(
        name = "WithUser",
        attributeNodes = {@NamedAttributeNode("user")}
)

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
@Builder
@Entity
public class Authorities implements BaseEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void setUser(User user){
        user.setAuthorities(this);
        this.user = user;
    }
}
