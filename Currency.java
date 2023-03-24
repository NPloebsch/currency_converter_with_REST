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