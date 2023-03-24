const Currency = require('./Currency');
const CurrencyDao = require('./CurrencyDao');
const CurrencyConverter = require('./CurrencyConverter');
const express = require('express');
const bodyParser = require('body-parser');

const app = express();
const port = process.env.PORT || 3000;

// Set up the database connection
const mysql = require('mysql2/promise');
const dbConfig = {
  host: 'localhost',
  user: 'root',
  password: 'password',
  database: 'currency_converter',
};

// Create a pool of connections to the database
const pool = mysql.createPool(dbConfig);

// Create a CurrencyDao instance using the connection pool
const currencyDao = new CurrencyDao(pool);

// Create a CurrencyConverter instance using the CurrencyDao
const currencyConverter = new CurrencyConverter(currencyDao);

// Set up the middleware to parse JSON requests
app.use(bodyParser.json());

// Define the route to handle GET requests for the currency conversion form
app.get('/convert', async (req, res) => {
  try {
    const currencies = await currencyDao.getAllCurrencies();
    res.render('convert', { currencies });
  } catch (err) {
    console.error(err);
    res.status(500).send('Internal server error');
  }
});

// Define the route to handle POST requests for currency conversion
app.post('/convert', async (req, res) => {
  try {
    const { amount, fromCurrency, toCurrency } = req.body;
    const result = await currencyConverter.convertCurrency(
      amount,
      fromCurrency,
      toCurrency
    );
    res.render('result', { amount, fromCurrency, toCurrency, result });
  } catch (err) {
    console.error(err);
    res.status(500).send('Internal server error');
  }
});

// Start the server
app.listen(port, () => {
  console.log(`Server listening on port ${port}`);
});
