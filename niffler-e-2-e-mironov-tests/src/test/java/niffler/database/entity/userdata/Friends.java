package niffler.database.entity.userdata;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friends", schema = "public", catalog = "niffler-userdata")
public class Friends {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Users friend;
}

