package niffler.database.dao;

import niffler.data.entity.Categories;
import niffler.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoriesDao implements Dao<UUID, Categories> {

    private static final String FIND_ALL = "SELECT * FROM categories;";

    private static final CategoriesDao categoriesDao = new CategoriesDao();

    private CategoriesDao() {
    }


    @Override
    public Categories create(Categories spend) {
        return null;
    }

    @Override
    public boolean delete(UUID uuid) {
        return false;
    }

    @Override
    public List<Categories> findAll() {
        try (Connection connection = ConnectionManager.get()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            final ResultSet resultSet = preparedStatement.executeQuery();
            List<Categories> categories = new ArrayList<>();
            while (resultSet.next()) {
                categories.add(getCategories(resultSet));
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Categories getCategories(ResultSet resultSet) throws SQLException {
        return new Categories(
                (UUID) resultSet.getObject("id"),
                resultSet.getString("description")
        );
    }

    public static CategoriesDao getInstance() {
        return categoriesDao;
    }
}
