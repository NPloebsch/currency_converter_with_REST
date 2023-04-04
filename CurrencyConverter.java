// Import necessary libraries

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



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
module.exports = CurrencyConverter;