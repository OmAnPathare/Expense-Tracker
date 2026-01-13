CREATE DATABASE expense_tracker;
USE expense_tracker;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    name VARCHAR(100),
    email VARCHAR(100),
    contact VARCHAR(15),
    age INT
);

CREATE TABLE expenses (
    expense_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    expense_name VARCHAR(100),
    amount DOUBLE,
    expense_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
