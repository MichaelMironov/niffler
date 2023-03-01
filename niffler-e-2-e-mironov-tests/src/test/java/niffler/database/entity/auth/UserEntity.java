package niffler.database.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import niffler.database.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "authorities")
@ToString(exclude = "authorities")
@Table(name = "users", schema = "public", catalog = "niffler-auth")
public class UserEntity implements BaseEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(name = "account_non_expired", nullable = false)
    private Boolean accountNonExpired;

    @Column(name = "account_non_locked", nullable = false)
    private Boolean accountNonLocked;

    @Column(name = "credentials_non_expired", nullable = false)
    private Boolean credentialsNonExpired;

    @OneToMany(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Set<AuthorityEntity> authorities = new HashSet<>();
}
