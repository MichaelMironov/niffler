package niffler.data.entity;

import niffler.data.enums.CurrencyValues;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class Spend {
    private UUID id;
    private String username;
    private Date spendDate;
    private CurrencyValues currency;
    private Double amount;
    private String description;
    private UUID categoryId;
    private String categoryName;

    public Spend() {
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

    public void setCurrency(CurrencyValues currency) {
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

    public String getDescription() {
        return description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSpendDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yy", new Locale("en"));
        return format.format(spendDate);
    }

    public String getCurrency() {
        return currency.toString();
    }

    public Double getAmount() {
        return amount;
    }

    public UUID getCategoryId() {
        return categoryId;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Spend) obj;
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

    public static class Builder {

        private final Spend spend;

        public Builder() {
            spend = new Spend();
        }

        public Builder setId(UUID uuid){
            spend.id = uuid;
            return this;
        }

        public Builder setUsername(String username) {
            spend.username = username;
            return this;
        }

        public Builder setDate(Date spendDate) {
            spend.spendDate = spendDate;
            return this;
        }

        public Builder setCurrency(CurrencyValues currency) {
            spend.currency = currency;
            return this;
        }

        public Builder setAmount(Double amount) {
            spend.amount = amount;
            return this;
        }

        public Builder setDescription(String description) {
            spend.description = description;
            return this;
        }

        public Builder setCategoryId(UUID categoryId) {
            spend.categoryId = categoryId;
            return this;
        }

        public Builder setCategoryName(String categoryName) {
            spend.categoryName = categoryName;
            return this;
        }

        public Spend build() {
            return spend;
        }
    }
}
