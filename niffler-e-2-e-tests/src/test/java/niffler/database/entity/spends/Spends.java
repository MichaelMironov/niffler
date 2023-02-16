package niffler.database.entity.spends;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class Spends {
    private UUID id;
    private String username;
    private Date spendDate;
    private String currency;
    private Double amount;
    private String description;
    private UUID categoryId;

    public Spends(UUID id, String username, Date spendDate, String currency, Double amount, String description, UUID categoryId) {
        this.id = id;
        this.username = username;
        this.spendDate = spendDate;
        this.currency = currency;
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
    }

    public Spends() {
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSpendDate(Date spendDate) {
        this.spendDate = spendDate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getSpendDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yy", new Locale("en"));
        return format.format(spendDate);
    }

    public String getCurrency() {
        return currency;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescriprion() {
        return description;
    }

    public UUID getCategoryId() {
        return categoryId;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Spends) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.username, that.username) &&
                Objects.equals(this.spendDate, that.spendDate) &&
                Objects.equals(this.currency, that.currency) &&
                Objects.equals(this.amount, that.amount) &&
                Objects.equals(this.description, that.description) &&
                Objects.equals(this.categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, spendDate, currency, amount, description, categoryId);
    }

    @Override
    public String toString() {
        return "Spends[" +
                "id=" + id + ", " +
                "username=" + username + ", " +
                "spendDate=" + spendDate + ", " +
                "currency=" + currency + ", " +
                "amount=" + amount + ", " +
                "description=" + description + ", " +
                "categoryId=" + categoryId + ']';
    }

}
