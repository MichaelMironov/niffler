package niffler.database.dao;

import niffler.database.DataBase;
import niffler.database.entity.userdata.ProfileEntity;
import niffler.database.exception.DatabaseException;
import niffler.database.jdbc.DataSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

public class PostgresJdbcUsersDAO implements UsersDAO {

    private static final Logger LOG = LoggerFactory.getLogger(PostgresJdbcUsersDAO.class);
    private final DataSource dataSource = DataSourceContext.INSTANCE.getDatatSource(DataBase.USERDATA);

    public int addUsers(ProfileEntity... users) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            int count = 0;
            for (ProfileEntity user : users) {
                statement.addBatch("""
            INSERT INTO public.users (username, currency, firstname, surname, photo)
            VALUES ('%s', '%s', '%s', '%s', '%s');""".formatted(
                        user.getUsername(),
                        user.getCurrency(),
                        user.getFirstname(),
                        user.getSurname(),
                        user.getPhoto()));
            }
            count += statement.executeBatch().length;
            connection.commit();
            return count;

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public ProfileEntity addUser(ProfileEntity users) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement("""
            INSERT INTO public.users (username, currency, firstname, surname, photo)
            VALUES (?, ?, ?, ?, ?) RETURNING *;""")) {
            preparedStatement.setString(1, users.getUsername());
            preparedStatement.setString(2, users.getCurrency());
            preparedStatement.setString(3, users.getFirstname());
            preparedStatement.setString(4, users.getSurname());
            preparedStatement.setBytes(5, users.getPhoto());
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                users = getUsersDataEntity(resultSet);
            }
            return users;
        } catch (SQLException e) {
            LOG.error("Database error while adding user ", e);
            throw new DatabaseException(e);
        }
    }

    public int updateUsers(ProfileEntity... users) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            int count = 0;
            for (ProfileEntity user : users) {
                statement.addBatch("""
            UPDATE users SET currency = '%s', firstname = '%s', surname = '%s', photo = '%s'
            WHERE username = '%s';""".formatted(
                        user.getCurrency(),
                        user.getFirstname(),
                        user.getSurname(),
                        user.getPhoto(),
                        user.getUsername()));
            }
            count += statement.executeBatch().length;
            connection.commit();
            return count;

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void updateUser(ProfileEntity user) {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            String sql = "UPDATE users SET currency = '" + user.getCurrency() + "' WHERE username = '" + user.getUsername() + "';";
            st.executeUpdate(sql);
        } catch (SQLException e) {
            LOG.error("Error while database operation", e);
            throw new DatabaseException(e);
        }
    }

    @Override
    public void remove(ProfileEntity user) {

    }

    @Override
    public ProfileEntity getByUsername(String username) {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            String sql = "SELECT * FROM users WHERE username = '" + username + "';";
            ResultSet resultSet = st.executeQuery(sql);
            if (resultSet.next()) {
                ProfileEntity result = new ProfileEntity();
                result.setId(UUID.fromString(resultSet.getString(1)));
                result.setUsername(resultSet.getString(2));
                result.setCurrency(resultSet.getString(3));
                result.setFirstname(resultSet.getString("firstname"));
                result.setSurname(resultSet.getString("surname"));
                result.setPhoto(resultSet.getBytes("photo"));
                return result;
            } else {
                String msg = "Can`t find user by username: " + username;
                LOG.error(msg);
                throw new RuntimeException(msg);
            }

        } catch (SQLException e) {
            LOG.error("Error while database operation", e);
            throw new DatabaseException(e);
        }
    }

    private ProfileEntity getUsersDataEntity(ResultSet resultSet) throws SQLException {
        return ProfileEntity.builder()
                .id((UUID) resultSet.getObject("id"))
                .username(resultSet.getString("username"))
                .currency(resultSet.getString("currency"))
                .firstname(resultSet.getString("firstname"))
                .surname(resultSet.getString("surname"))
                .photo(resultSet.getBytes("photo"))
                .build();
    }
}
