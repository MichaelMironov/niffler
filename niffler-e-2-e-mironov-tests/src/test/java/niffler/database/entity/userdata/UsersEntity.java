package niffler.database.entity.userdata;

import jakarta.persistence.*;
import lombok.*;
import niffler.database.entity.BaseEntity;

import java.util.*;

@NamedQuery(name ="findByUsername" , query = "select u from UsersEntity u where u.username = :username")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public", catalog = "niffler-userdata")
public class UsersEntity implements BaseEntity<UUID> {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    private String username;

    private String currency;

    private String firstname;

    private String surname;

    @Column(columnDefinition = "bytea")
    private byte[] photo;

}