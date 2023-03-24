// Import necessary libraries

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Create a Currency class to store currency information

public class Currency {
    private int id;
    private String name;
    private double exchangeRate;

    public Currency(int id, String name, double exchangeRate) {
        this.id = id;
        this.name = name;
        this.exchangeRate = exchangeRate;
    }

    // Create getters and setters for currency information

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}

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

// Create a CurrencyConverter class to handle currency conversions

public class CurrencyConverter {
    private CurrencyDao currencyDao;

    public CurrencyConverter(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    // Create a method to convert an amount from one currency to another

    public double convertCurrency(double amount, String fromCurrency, String toCurrency) throws SQLException {
        Currency from = currencyDao.getAllCurrencies().stream().filter(c -> c.getName().equals(fromCurrency)).findFirst().orElse(null);
        Currency to = currencyDao.getAllCurrencies().stream().filter(c -> c.getName().equals(toCurrency)).findFirst().orElse(null);
        if (from == null || to == null) {
            throw new IllegalArgumentException("Invalid currency");
        }
        return amount * from.getExchangeRate() / to.getExchangeRate();
    }
}

// Create a Servlet to handle HTTP requests

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CurrencyServlet extends HttpServlet {
private CurrencyDao currencyDao;
public CurrencyServlet() {
    try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/currency_converter", "root", "password");
        currencyDao = new CurrencyDao(connection);
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
}

// Override doGet method to display a form for currency conversion

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        List<Currency> currencies = currencyDao.getAllCurrencies();
        request.setAttribute("currencies", currencies);
        request.getRequestDispatcher("convert.jsp").forward(request, response);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Override doPost method to handle currency conversion

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        double amount = Double.parseDouble(request.getParameter("amount"));
        String fromCurrency = request.getParameter("from_currency");
        String toCurrency = request.getParameter("to_currency");
        CurrencyConverter converter = new CurrencyConverter(currencyDao);
        double result = converter.convertCurrency(amount, fromCurrency, toCurrency);
        request.setAttribute("result", result);
        doGet(request, response);
    } catch (SQLException | NumberFormatException | IllegalArgumentException e) {
        e.printStackTrace();
    }
}
}

// Create a JSP file to display the form and the result of currency conversion
<!DOCTYPE html>
<html>
<head>
    <title>Currency Converter</title>
</head>
<body>
    <h1>Currency Converter</h1>
    <form method="post">
        <label for="amount">Amount:</label>
        <input type="number" name="amount" id="amount"><br><br>
        <label for="from_currency">From:</label>
        <select name="from_currency" id="from_currency">
            <c:forEach items="${currencies}" var="currency">
                <option value="${currency.name}">${currency.name}</option>
            </c:forEach>
        </select><br><br>
        <label for="to_currency">To:</label>
        <select name="to_currency" id="to_currency">
            <c:forEach items="${currencies}" var="currency">
                <option value="${currency.name}">${currency.name}</option>
            </c:forEach>
        </select><br><br>
        <input type="submit" value="Convert">
    </form>
    <c:if test="${not empty result}">
        <p>${amount} ${from_currency} = ${result} ${to_currency}</p>
    </c:if>
</body>
</html>

// Create a SQL script to create the database and the currencies table

CREATE DATABASE currency_converter;
USE currency_converter;
CREATE TABLE currencies (
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
exchange_rate DOUBLE NOT NULL,
PRIMARY KEY (id)
);
INSERT INTO currencies (name, exchange_rate) VALUES ('USD', 1);
INSERT INTO currencies (name, exchange_rate) VALUES ('EUR', 0.84);
INSERT INTO currencies (name, exchange_rate) VALUES ('GBP', 0.72);
INSERT INTO currencies (name, exchange_rate) VALUES ('JPY', 109.98);
INSERT INTO currencies (name, exchange_rate) VALUES ('AUD', 1.28);

// Create a DAO class to interact with the database using the CRUD standard

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDao {
private Connection connection;

public CurrencyDao(Connection connection) {
    this.connection = connection;
}

public List<Currency> getAllCurrencies() throws SQLException {
    List<Currency> currencies = new ArrayList<>();
    String query = "SELECT name, exchange_rate FROM currencies";
    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            double exchangeRate = resultSet.getDouble("exchange_rate");
            Currency currency = new Currency(name, exchangeRate);
            currencies.add(currency);
        }
    }
    return currencies;
}

public Currency getCurrencyByName(String name) throws SQLException {
    String query = "SELECT exchange_rate FROM currencies WHERE name = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, name);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                double exchangeRate = resultSet.getDouble("exchange_rate");
                return new Currency(name, exchangeRate);
            } else {
                return null;
            }
        }
    }
}

public boolean insertCurrency(Currency currency) throws SQLException {
    String query = "INSERT INTO currencies (name, exchange_rate) VALUES (?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, currency.getName());
        statement.setDouble(2, currency.getExchangeRate());
        int result = statement.executeUpdate();
        return result == 1;
    }
}

public boolean updateCurrency(Currency currency) throws SQLException {
    String query = "UPDATE currencies SET exchange_rate = ? WHERE name = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setDouble(1, currency.getExchangeRate());
        statement.setString(2, currency.getName());
        int result = statement.executeUpdate();
        return result == 1;
    }
}

public boolean deleteCurrency(String name) throws SQLException {
    String query = "DELETE FROM currencies WHERE name = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, name);
        int result = statement.executeUpdate();
        return result == 1;
    }
}
}

// Create a Currency class to represent a currency

public class Currency {
    private String name;
    private double exchangeRate;
    public Currency(String name, double exchangeRate) {
        this.name = name;
        this.exchangeRate = exchangeRate;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getExchangeRate() {
        return exchangeRate;
    }
    
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}    

// Create a CurrencyConverter class to convert currencies

public class CurrencyConverter {
    private CurrencyDao currencyDao;
    public CurrencyConverter(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }
    
    public double convertCurrency(double amount, String fromCurrency, String toCurrency) throws SQLException {
        Currency from = currencyDao.getCurrencyByName(fromCurrency);
        Currency to = currencyDao.getCurrencyByName(toCurrency);
        if (from == null || to == null) {
            throw new IllegalArgumentException("Invalid currency");
        }
        double fromExchangeRate = from.getExchangeRate();
        double toExchangeRate = to.getExchangeRate();
        return amount * toExchangeRate / fromExchangeRate;
    }
}    
