package niffler.database.mapper;

import niffler.database.entity.userdata.ProfileEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UsersDataRowMapper implements RowMapper<ProfileEntity> {
    @Override
    public ProfileEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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
