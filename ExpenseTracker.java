import java.sql.*;
import java.util.Scanner;

public class ExpenseTracker {

    static final String DB_URL = "jdbc:mysql://localhost:3306/expense_tracker";
    static final String DB_USER = "root";
    static final String DB_PASS = "12345";

    static Scanner sc = new Scanner(System.in);
    static int loggedInUserId = -1;

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            while (true) {
                System.out.println("\n--- EXPENSE TRACKER ---");
                System.out.println("1. Login");
                System.out.println("2. Create Account");
                System.out.println("3. Exit");
                System.out.print("Choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) login();
                else if (choice == 2) createAccount();
                else System.exit(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void login() throws Exception {
        Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();

        PreparedStatement ps = con.prepareStatement(
                "SELECT user_id FROM users WHERE username=? AND password=?");
        ps.setString(1, u);
        ps.setString(2, p);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            loggedInUserId = rs.getInt("user_id");
            System.out.println("Login successful!");
            userMenu();
        } else {
            System.out.println("Invalid credentials.");
        }

        con.close();
    }

    static void createAccount() throws Exception {
        Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Contact: ");
        String contact = sc.nextLine();
        System.out.print("Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(username,password,name,email,contact,age) VALUES (?,?,?,?,?,?)");

        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, name);
        ps.setString(4, email);
        ps.setString(5, contact);
        ps.setInt(6, age);

        ps.executeUpdate();
        System.out.println("Account created successfully.");

        con.close();
    }

    static void userMenu() throws Exception {
        while (true) {
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expense History");
            System.out.println("3. Edit Expense");
            System.out.println("4. Logout");
            System.out.print("Choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            if (ch == 1) addExpense();
            else if (ch == 2) viewExpenses();
            else if (ch == 3) editExpense();
            else {
                loggedInUserId = -1;
                break;
            }
        }
    }

    static void addExpense() throws Exception {
        Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        System.out.print("Expense Name: ");
        String name = sc.nextLine();
        System.out.print("Amount: ");
        double amt = sc.nextDouble();
        sc.nextLine();
        System.out.print("Date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO expenses(user_id,expense_name,amount,expense_date) VALUES (?,?,?,?)");

        ps.setInt(1, loggedInUserId);
        ps.setString(2, name);
        ps.setDouble(3, amt);
        ps.setDate(4, Date.valueOf(date));

        ps.executeUpdate();
        System.out.println("Expense added.");

        con.close();
    }

    static void viewExpenses() throws Exception {
        Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM expenses WHERE user_id=?");
        ps.setInt(1, loggedInUserId);

        ResultSet rs = ps.executeQuery();

        System.out.println("\nID | Name | Amount | Date");
        while (rs.next()) {
            System.out.println(
                    rs.getInt("expense_id") + " | " +
                    rs.getString("expense_name") + " | " +
                    rs.getDouble("amount") + " | " +
                    rs.getDate("expense_date")
            );
        }

        con.close();
    }

    static void editExpense() throws Exception {
        Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        System.out.print("Enter Expense ID to edit: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("New Name: ");
        String name = sc.nextLine();
        System.out.print("New Amount: ");
        double amt = sc.nextDouble();
        sc.nextLine();

        PreparedStatement ps = con.prepareStatement(
                "UPDATE expenses SET expense_name=?, amount=? WHERE expense_id=? AND user_id=?");

        ps.setString(1, name);
        ps.setDouble(2, amt);
        ps.setInt(3, id);
        ps.setInt(4, loggedInUserId);

        int rows = ps.executeUpdate();

        if (rows > 0) System.out.println("Expense updated.");
        else System.out.println("Invalid expense ID.");

        con.close();
    }
}
