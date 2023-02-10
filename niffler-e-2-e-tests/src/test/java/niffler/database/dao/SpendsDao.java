package niffler.database.dao;

import niffler.database.entity.Currency;
import niffler.database.entity.Spend;
import niffler.utils.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpendsDao implements Dao<UUID, Spend> {

    private static final String FIND_ALL = "SELECT * FROM public.spends;";
    private static final String ADD_SPEND = """
            INSERT INTO spends (username, spend_date, currency, amount, description, category_id)
            VALUES (?, ?, ?, ?, ?, ?) RETURNING *;""";
    private static final String DELETE_ALL = "DELETE FROM spends WHERE amount > 0;";

    private static final SpendsDao INSTANCE = new SpendsDao();

    public static SpendsDao getInstance() {
        return INSTANCE;
    }

    private SpendsDao() {
    }

    @Override
    public List<Spend> findAll() {
        try (Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            List<Spend> spends = new ArrayList<>();
            while (resultSet.next()) {
                spends.add(getSpends(resultSet));
            }
            return spends;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Spend create(Spend spend) {
        try (Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(ADD_SPEND)) {
            preparedStatement.setString(1, spend.getUsername());
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(3, spend.getCurrency().toString());
            preparedStatement.setDouble(4, spend.getAmount());
            preparedStatement.setString(5, spend.getDescription());
            preparedStatement.setObject(6, spend.getCategoryId());
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                spend = getSpends(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spend;
    }

    @Override
    public boolean delete(UUID uuid) {
        //TODO: add deleting by uuid
        try (Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL)) {
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean clear() {
        try (Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL)) {
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Spend getSpends(ResultSet resultSet) throws SQLException {
        return new Spend.Builder()
                .setId((UUID) resultSet.getObject("id"))
                .setUsername(resultSet.getString("username"))
                .setDate(resultSet.getDate("spend_date"))
                .setCurrency(Currency.valueOf(resultSet.getString("currency")))
                .setAmount(resultSet.getDouble("amount"))
                .setDescription(resultSet.getString("description"))
                .setCategoryId((UUID) resultSet.getObject("category_id"))
                .build();
    }
}
