package niffler.database.dao;

import niffler.database.entity.categories.Categories;
import niffler.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CategoriesRepository implements Repository<UUID, Categories> {

    private static final String FIND_ALL = "SELECT * FROM categories;";

    private static final CategoriesRepository categoriesDao = new CategoriesRepository();

    private CategoriesRepository() {
    }


    @Override
    public Categories save(Categories spend) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public void update(Categories entity) {

    }

    @Override
    public Optional<Categories> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Categories> findById(UUID uuid, Map<String, Object> properties) {
        return Optional.empty();
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
                resultSet.getString("category"),
                resultSet.getString("username")
        );
    }

    public static CategoriesRepository getInstance() {
        return categoriesDao;
    }
}
