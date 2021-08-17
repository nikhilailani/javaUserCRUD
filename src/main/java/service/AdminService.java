package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

import com.mysql.jdbc.PreparedStatement;
import models.User;
import com.mysql.jdbc.Connection;



public class AdminService  extends UserService {
    private Connection con;
    private int id;
    public AdminService(Connection con, int id) {
        this.con = con;
        this.id = id;
    }

    @Override
    public void run () {
        int choice;
        boolean exit = true;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Choose the option below\n" +
                "--------------------------\n" +
                "1. Add User\n" +
                "2. Edit User\n" +
                "3. Delete User\n" +
                "4. List Users\n" +
                "5. Exit\n"+
                "--------------------------\n");
            choice = sc.nextInt();
            switch(choice) {
                case 1: this.addUser();
                break;
                case 2: this.editUser();
                break;
                case 3: this.removeUser();
                break;
                case 4: this.listUsers();
                break;
                case 5: exit = false;
                break;
                default: System.out.println("Choose correct options from the above given options!");
                break;
            }
        } while(exit);
    }

    private void addUser() {
        String username, firstName, lastName, dateOfBirth, role, password;
        System.out.print("Enter username: ");
        Scanner sc = new Scanner(System.in);
        username = sc.next();
        System.out.print("Enter First Name: ");
        firstName = sc.next();
        System.out.print("Enter Last Name: ");
        lastName = sc.next();
        System.out.print("Enter Date of Birth (Format: DD-MM-YYYY): ");
        dateOfBirth = sc.next();
        System.out.print("Enter role:\n--------------------------\n1. Admin\n2. Developer\n3. Tester\n--------------------------\n");
        switch(sc.nextInt()) {
            case 1: role = "admin";
                break;
            case 2: role = "developer";
                break;
            default: role = "tester";
        }
        System.out.print("Enter password: ");
        password = sc.next();

        User user = new User(username, firstName, lastName, dateOfBirth, role, password);

        System.out.print(user.getName());

        PreparedStatement stmt;
        try {
            stmt = (PreparedStatement) this.con.prepareStatement("insert into users ( username, firstname,lastname, password, role) values (?, ?, ?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, password);
            stmt.setString(5, role);
            stmt.execute();

        } catch (SQLException exc) {
            exc.printStackTrace();
        }

    }

    private void removeUser() {
        try {
            PreparedStatement ps = (PreparedStatement) this.con.prepareStatement("Select id, firstname, lastname, role from users");
            ResultSet rs = ps.executeQuery();
            System.out.println("Choose a user to edit details\n"
                    + "--------------------------\n");
            System.out.println("ID\tFirstName\tLastName\tRole");
            while (rs.next()) {
                System.out.println(rs.getString("id") + "\t" + rs.getString("firstname") + "\t" + rs.getString("lastname") + "\t" + rs.getString("role"));
            }

            Scanner sc = new Scanner(System.in);
            int id = sc.nextInt();
            ps = (PreparedStatement) this.con.prepareStatement("DELETE FROM users WHERE id = ?");
            ps.setString(1, String.valueOf(id));
            ps.execute();
        } catch (SQLException sqe) {
            System.out.print(sqe.getMessage());
        }

    }

    private void editUser() {
        try {
            PreparedStatement ps = (PreparedStatement) this.con.prepareStatement("Select id, firstname, lastname, role from users");
            ResultSet rs = ps.executeQuery();
            System.out.println("Choose a user to edit details\n"
                    + "--------------------------\n");
            System.out.println("ID\tFirstName\tLastName\tRole");
            while (rs.next()) {
                System.out.println(rs.getString("id") + "\t" + rs.getString("firstname") + "\t" + rs.getString("lastname") + "\t" + rs.getString("role"));
            }

            Scanner sc = new Scanner(System.in);
            int id = sc.nextInt();
            String updated = null;
            System.out.println("Select what do you want to change\n" +
                    "--------------------------\n" +
                    "1. First Name\n" +
                    "2. Last Name\n" +
                    "3. Role\n" +
                    "--------------------------\n");
            switch (sc.nextInt()) {
                case 1:
                    System.out.print("Enter new first name: ");
                    updated = sc.next();
                    ps = (PreparedStatement) this.con.prepareStatement("UPDATE users SET firstname = ? WHERE id = ?");
                    ps.setString(1, updated);
                    ps.setString(2, String.valueOf(id));
                    ps.executeUpdate();
                    break;
                case 2:
                    System.out.print("Enter new last name: ");
                    updated = sc.next();
                    ps = (PreparedStatement) this.con.prepareStatement("UPDATE users SET lastname = ? WHERE id = ?");
                    ps.setString(1, updated);
                    ps.setString(2, String.valueOf(id));
                    ps.executeUpdate();
                    break;
                case 3:
                    System.out.print("Choose the new role from the following:\n" +
                            "--------------------------\n" +
                            "1. Admin\n" +
                            "2. Developer\n" +
                            "3. Tester\n" +
                            "--------------------------\n");
                    switch (sc.nextInt()) {
                        case 1: updated = "admin";
                        case 2: updated = "developer";
                        case 3: updated = "tester";
                        default:
                            System.out.println("Please choose one of the given roles");
                    }
                    if(!updated.isEmpty()) {
                        ps = (PreparedStatement) this.con.prepareStatement("UPDATE users SET role = ? WHERE id = ?");
                        ps.setString(1, updated);
                        ps.setString(2, String.valueOf(id));
                        ps.executeUpdate();
                    }
                    break;
                default:
                    System.out.println("Please choose the correct option from the above given options");
            }
        } catch (SQLException sqe) {
            System.out.print(sqe.getMessage());
        }
    }

    private void listUsers() {
        try {
            PreparedStatement ps = (PreparedStatement) this.con.prepareStatement("Select id, firstname, lastname, role from users");
            ResultSet rs = ps.executeQuery();
            System.out.println("Choose a user to edit details\n"
                    + "--------------------------\n");
            System.out.println("ID\tFirstName\tLastName\tRole");
            while (rs.next()) {
                System.out.println(rs.getString("id") + "\t" + rs.getString("firstname") + "\t" + rs.getString("lastname") + "\t" + rs.getString("role"));
            }
        } catch (SQLException sqe) {
            System.out.println(sqe.getMessage());
        }
    }
}