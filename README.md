# currency_converter_with_REST
currency converter in Java using MySQL and REST 


Establishes a connection to the database and initializes a CurrencyDao and a CurrencyConverter instance for all tests. Adds a new currency, retrieves it from the database, and checks if all fields are correct. Updates the currency, retrieves it from the database, and checks if all fields have been updated correctly. Deletes the currency from the database and checks if it is no longer found. Retrieves all currencies from the database and checks if the number of returned currencies is correct. Converts an amount from USD to EUR and checks if the conversion result is correct.


Test_ Unit-Test:
 -   Establishes a connection to the database and initializes a CurrencyDao and a CurrencyConverter instance for all tests.
 -   Adds a new currency, retrieves it from the database, and checks if all fields are correct.
 -   Updates the currency, retrieves it from the database, and checks if all fields have been updated correctly.
 -   Deletes the currency from the database and checks if it is no longer found.
 -   Retrieves all currencies from the database and checks if the number of returned currencies is correct.
 -   Converts an amount from USD to EUR and checks if the conversion result is correct.

Test 2:
- Test that an IllegalArgumentException is thrown when converting an invalid currency
- The purpose of the test is to ensure that an exception is thrown when attempting to convert an amount between two currencies that do not exist in the database.
- The test sets up a connection to the database and initializes instances of CurrencyDao and CurrencyConverter for all tests.
- The test creates an invalid currency name by concatenating two valid currency names.
- The test attempts to convert an amount of USD to the invalid currency name and expects an IllegalArgumentException to be thrown.
- The test asserts that the correct exception message is returned by checking if the message contains the expected substring "Invalid currency".
- Finally, the test cleans up by deleting the invalid currency from the database so that it does not affect subsequent tests.


How to start the Docker Container:
docker build -t currency_converter_with_rest_main .
docker run -d -p 3000:3000 currency_converter_with_rest_main:latest 
http://localhost:3000/
