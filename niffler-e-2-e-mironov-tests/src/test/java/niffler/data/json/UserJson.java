package niffler.data.json;

import com.codeborne.selenide.ElementsCollection;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import niffler.data.enums.CurrencyValues;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class UserJson {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("username")
    private String userName;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("currency")
    private CurrencyValues currency;
    @JsonProperty("photo")
    private String photo;
    @JsonProperty("password")
    private String password;
    @JsonProperty("friendState")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FriendState friendState;

    private transient List<CategoryJson> categoryJsons;
    private transient List<SpendJson> spendJsons;

    public static UserJson fromTable(ElementsCollection element) {
        UserJson usr = new UserJson();
        usr.setPhoto(StringUtils.substringAfter(element.get(0).getAttribute("src"), "base64,"));
        usr.setUserName(element.get(1).getText());
        usr.setFirstname(element.get(2).getText());
        usr.setSurname(element.get(3).getText());
        return usr;
    }


    public FriendState getFriendState() {
        return friendState;
    }

    public UserJson() {
    }

    public void setFriendState(FriendState friendState) {
        this.friendState = friendState;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public CurrencyValues getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyValues currency) {
        this.currency = currency;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CategoryJson> getCategoryJsons() {
        return categoryJsons;
    }

    public void setCategoryJsons(List<CategoryJson> categoryJsons) {
        this.categoryJsons = categoryJsons;
    }

    public List<SpendJson> getSpendJsons() {
        return spendJsons;
    }

    public void setSpendJsons(List<SpendJson> spendJsons) {
        this.spendJsons = spendJsons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserJson userJson = (UserJson) o;
        return Objects.equals(id, userJson.id) && Objects.equals(userName, userJson.userName) && Objects.equals(firstname, userJson.firstname) && Objects.equals(surname, userJson.surname) && currency == userJson.currency && Objects.equals(photo, userJson.photo) && Objects.equals(password, userJson.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, firstname, surname, currency, photo, password);
    }

    @Override
    public String toString() {
        return "UserJson{" +
               "id=" + id +
               ", userName='" + userName + '\'' +
               ", firstname='" + firstname + '\'' +
               ", surname='" + surname + '\'' +
               ", currency=" + currency +
               ", photo='" + photo + '\'' +
               ", password='" + password + '\'' +
               ", friendState=" + friendState +
               ", categoryJsons=" + categoryJsons +
               ", spendJsons=" + spendJsons +
               '}';
    }
}
