package niffler.database.repository;

import jakarta.persistence.Column;
import niffler.data.entity.User;
import niffler.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRepository {

    private UUID id;
    private String username;
    private String password;
    private Boolean enabled;
    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;
    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    private static final UserRepository userRepository = new UserRepository();

    private static final UserRepository INSTANCE = new UserRepository();

    public static UserRepository getInstance() {
        return INSTANCE;
    }

    private static final String CREATE_USER = "INSERT INTO users (username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired)" +
            " VALUES (?, ?, ?, ?, ?, ?) RETURNING *;";

    private static final String ADD_AUTHORITY= "INSERT INTO authorities (user_id, authority) VALUES (?, ?);";

    public void createUserWithReadAuthority(User user, String authority) {
        try (Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.getEnabled());
            preparedStatement.setBoolean(4, user.getAccountNonExpired());
            preparedStatement.setBoolean(5, user.getAccountNonLocked());
            preparedStatement.setBoolean(6, user.getCredentialsNonExpired());
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                user = getUser(resultSet);

            preparedStatement.executeQuery();
//            addAuthority(user, authority);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAuthority(User user, String authority){
        try (Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(ADD_AUTHORITY)) {
            preparedStatement.setObject(1, user.getId());
            preparedStatement.setObject(2, authority);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        return new User(
                (UUID) resultSet.getObject("id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getBoolean("enabled"),
                resultSet.getBoolean("account_non_expired"),
                resultSet.getBoolean("account_non_locked"),
                resultSet.getBoolean("credentials_non_expired")
        );
    }

}
