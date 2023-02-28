package niffler.database.dao;

import lombok.NonNull;
import niffler.database.DataBase;
import niffler.database.entity.userdata.ProfileEntity;
import niffler.database.jdbc.DataSourceContext;
import niffler.database.mapper.UsersDataRowMapper;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class PostgresSpringJdbcUsersDAO implements UsersDAO {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceContext.INSTANCE.getDatatSource(DataBase.USERDATA));

    private static final String ADD_USER = """
            INSERT INTO public.users (username, currency, firstname, surname, photo)
            VALUES (?, ?, ?, ?, ?)
            RETURNING *;""";

    private static final String FIND_BY_USERNAME = """
            SELECT * FROM public.users
            WHERE username = ?;
            """;

    @Override
    public ProfileEntity addUser(ProfileEntity users) {

        return jdbcTemplate.queryForObject(ADD_USER, new UsersDataRowMapper(),
                users.getUsername(),
                users.getCurrency(),
                users.getFirstname(),
                users.getSurname(),
                users.getPhoto()
        );
    }

    @Override
    public void updateUser(ProfileEntity user) {
        jdbcTemplate.update("UPDATE users SET currency = ?,firstname = ?, surname = ?  WHERE username = ?",
                user.getCurrency(),
                user.getFirstname(),
                user.getSurname(),
                user.getUsername());
    }

    @Override
    public void remove(ProfileEntity user) {
        jdbcTemplate.update("DELETE FROM public.users WHERE username = ?", user.getUsername());
    }

    @Override
    public ProfileEntity getByUsername(String username) {
        return jdbcTemplate.queryForObject(FIND_BY_USERNAME, new UsersDataRowMapper(), username);
    }

    public void addUsers(ProfileEntity... users) {

        final ArrayList<ProfileEntity> temp = new ArrayList<>(Arrays.asList(users));

        jdbcTemplate.batchUpdate("""
                INSERT INTO public.users (username, currency, firstname, surname, photo)
                VALUES (?, ?, ?, ?, ?);""", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NonNull PreparedStatement preparedStatement, int i) throws SQLException {
                ProfileEntity user = temp.get(i);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getCurrency());
                preparedStatement.setString(3, user.getFirstname());
                preparedStatement.setString(4, user.getSurname());
                preparedStatement.setBytes(5, user.getPhoto());
            }

            @Override
            public int getBatchSize() {
                return temp.size();
            }
        });
    }

    public void updateUsers(ProfileEntity... users) {

        final ArrayList<ProfileEntity> temp = new ArrayList<>(Arrays.asList(users));

        jdbcTemplate.batchUpdate("""
                     UPDATE users SET currency = ?, firstname = ?, surname = ?, photo = ?
                     WHERE username = ?;""", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NonNull PreparedStatement preparedStatement, int i) throws SQLException {
                ProfileEntity user = temp.get(i);
                preparedStatement.setString(1, user.getCurrency());
                preparedStatement.setString(2, user.getFirstname());
                preparedStatement.setString(3, user.getSurname());
                preparedStatement.setBytes(4, user.getPhoto());
                preparedStatement.setString(5, user.getUsername());
            }

            @Override
            public int getBatchSize() {
                return temp.size();
            }
        });
    }
}
