package niffler.data.entity;

import niffler.database.entity.BaseEntity;

import java.util.UUID;

public class Categories implements BaseEntity<UUID> {
    private UUID id;
    private String description;

    public Categories(UUID id, String description) {
        this.id = id;
        this.description = description;
    }

    public Categories() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
