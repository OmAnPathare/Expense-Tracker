# Expense Tracker (Java + JDBC + MySQL)

## Project Description

This is a **console-based Expense Tracker application** developed using **Java**, **JDBC**, and **MySQL**.
The project allows multiple users to create profiles and manage their personal expenses securely.
Each user can add, view, and edit expenses through terminal-based commands.

The application demonstrates real-world usage of:

* Java database connectivity (JDBC)
* Relational database design
* Command-line interaction

---

## Features

* User registration and login system
* Profile-based expense storage
* Add new expenses
* View expense history
* Edit existing expense records
* Data persistence using MySQL

---

## Technologies Used

* Java (JDK 17+ recommended)
* MySQL 9.5.0
* JDBC (mysql-connector-j-9.5.0)
* VS Code / Command Line

---

## Project Structure

```
ExpenseTracker/
│── ExpenseTracker.java
│── mysql-connector-j-9.5.0.jar
│── database.sql
│── README.md
```

---

## Database Setup

1. Open MySQL CLI:

```sql
mysql -u root -p
```

2. Run the SQL file:

```sql
SOURCE database.sql;
```

This will create the required database and tables.

---

## How to Compile and Run

### Compile

```cmd
javac -cp .;mysql-connector-j-9.5.0.jar ExpenseTracker.java
```

### Run

```cmd
java -cp .;mysql-connector-j-9.5.0.jar ExpenseTracker
```

---

## Usage Instructions

1. Start the program from the terminal
2. Choose **Login** or **Create Account**
3. Enter user details (name, email, contact, age)
4. Use menu options to:

   * Add expenses
   * View expense history
   * Edit existing expenses
5. Exit safely from the menu

All data is stored permanently in the MySQL database.

---

## Viewing Data in MySQL

```sql
USE expense_tracker;
SELECT * FROM users;
SELECT * FROM expenses;
```
