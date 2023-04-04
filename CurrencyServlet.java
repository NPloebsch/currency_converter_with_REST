// Import necessary libraries

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
module.exports = CurrencyServlet;
