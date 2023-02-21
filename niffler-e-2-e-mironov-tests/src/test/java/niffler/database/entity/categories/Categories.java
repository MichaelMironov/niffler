package niffler.database.entity.categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import niffler.database.entity.BaseEntity;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Categories implements BaseEntity<UUID> {
    private UUID id;
    private String category;
    private String description;

    public Categories(UUID id, String category) {
        this.id = id;
        this.category = category;
    }

    public Categories() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", category='" + category + '\'' +
                '}';
    }
}
