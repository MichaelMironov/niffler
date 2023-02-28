package niffler.database.entity.userdata;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import niffler.database.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@NamedQuery(name = "findByUsername", query = "select u from UsersEntity u where u.username = :username")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "users", schema = "public", catalog = "niffler-userdata")
public class ProfileEntity implements BaseEntity<UUID> {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    private String username;

    private String currency;

    private String firstname;

    private String surname;

    @Column(columnDefinition = "bytea")
    private byte[] photo;

    @Builder.Default
    @OneToMany
    @JoinTable(name = "friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<ProfileEntity> friends = new ArrayList<>();

    public void setFriends(ProfileEntity... friends) {
        this.friends.addAll(Arrays.asList(friends));
    }

}