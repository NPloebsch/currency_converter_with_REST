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