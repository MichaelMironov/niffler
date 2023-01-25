package niffler.api.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import niffler.api.utils.DateDeserializer;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Spend {
    private Integer amount;
    private String category;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDateTime spendDate;

    public Spend() {
    }

    public Spend(Integer amount, String category, String description, LocalDateTime spendDate) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.spendDate = spendDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getSpendDate() {
        return spendDate;
    }

    public void setSpendDate(LocalDateTime spendDate) {
        this.spendDate = spendDate;
    }
}
