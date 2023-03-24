import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Connect to the database
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/currency_converter", "root", "password");

            // Create DAO and converter objects
            CurrencyDao currencyDao = new CurrencyDao(connection);
            CurrencyConverter converter = new CurrencyConverter(currencyDao);

            // Convert some currencies
            double amount = 100;
            String fromCurrency = "USD";
            String toCurrency = "EUR";
            double result = converter.convertCurrency(amount, fromCurrency, toCurrency);
            System.out.println(amount + " " + fromCurrency + " = " + result + " " + toCurrency);

            amount = 50;
            fromCurrency = "GBP";
            toCurrency = "USD";
            result = converter.convertCurrency(amount, fromCurrency, toCurrency);
            System.out.println(amount + " " + fromCurrency + " = " + result + " " + toCurrency);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
