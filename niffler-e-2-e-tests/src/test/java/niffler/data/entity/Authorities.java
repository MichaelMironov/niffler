package niffler.data.entity;

import jakarta.persistence.*;

import java.util.UUID;

public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private UUID userId;
    private String authority;

    public Authorities(UUID id, UUID userId, String authority) {
        this.id = id;
        this.userId = userId;
        this.authority = authority;
    }

    public Authorities() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                "id=" + id +
                ", userId=" + userId +
                ", authority='" + authority + '\'' +
                '}';
    }
}
