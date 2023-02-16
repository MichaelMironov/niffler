package niffler.database.entity.authorities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Builder
@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Authorities implements BaseEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void setUser(User user){
        user.setAuthorities(this);
        this.user = user;
    }
}
