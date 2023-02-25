package niffler.database.entity.userdata;

import jakarta.persistence.*;
import lombok.*;
import niffler.database.entity.BaseEntity;

import java.util.*;

@NamedQuery(name ="findByUsername" , query = "select u from Users u where u.username = :username")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "friends")
@Table(name = "users", schema = "public", catalog = "niffler-userdata")
public class Users implements BaseEntity<UUID> {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    private String username;

    private String currency;

    private String firstname;

    private String surname;

    private byte[] photo;

//    @Transient
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "friends",
              joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<Users> friends = new ArrayList<>();

    public void addFriends(Users... friend){
       this.friends.addAll(Arrays.asList(friend));
    }
}