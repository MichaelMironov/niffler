package niffler.data.entity;

import java.util.UUID;

public class Categories {
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
