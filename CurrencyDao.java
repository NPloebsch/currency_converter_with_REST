// Import necessary libraries

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



// Create a CurrencyDao class to handle CRUD operations on the database

public class CurrencyDao {
    private Connection connection;

    public CurrencyDao(Connection connection) {
        this.connection = connection;
    }

    // Create methods to add, update, delete, and retrieve currencies from the database

    public void addCurrency(Currency currency) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO currencies (name, exchange_rate) VALUES (?, ?)");
        preparedStatement.setString(1, currency.getName());
        preparedStatement.setDouble(2, currency.getExchangeRate());
        preparedStatement.executeUpdate();
    }

    public void updateCurrency(Currency currency) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE currencies SET name=?, exchange_rate=? WHERE id=?");
        preparedStatement.setString(1, currency.getName());
        preparedStatement.setDouble(2, currency.getExchangeRate());
        preparedStatement.setInt(3, currency.getId());
        preparedStatement.executeUpdate();
    }

    public void deleteCurrency(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM currencies WHERE id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public Currency getCurrencyById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM currencies WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Currency(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getDouble("exchange_rate"));
        }
        return null;
    }

    public List<Currency> getAllCurrencies() throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM currencies");
        while (resultSet.next()) {
            currencies.add(new Currency(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getDouble("exchange_rate")));
        }
        return currencies;
    }
}
module.exports = CurrencyDao;