package service;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.SQLException;
import java.util.Scanner;

public class TesterService extends UserService {
    private Connection con;
    private int id;
    public TesterService(Connection con, int id) {
        this.con = con;
        this.id = id;
    }
    @Override
    public void run() {
        int choice;
        boolean exit = true;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Choose the option below\n" +
                    "--------------------------\n" +
                    "1. Edit User\n" +
                    "2. Exit\n"+
                    "--------------------------\n");
            choice = sc.nextInt();
            switch(choice) {
                case 1: this.editUser();
                    break;
                case 2: exit = false;
                    break;
                default: System.out.println("Choose correct options from the above given options!");
                    break;
            }
        } while(exit);
    }

    private void editUser() {
        Scanner sc = new Scanner(System.in);
        try {
            PreparedStatement ps;
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
                    ps.setString(2, String.valueOf(this.id));
                    ps.executeUpdate();
                    break;
                case 2:
                    System.out.print("Enter new last name: ");
                    updated = sc.next();
                    ps = (PreparedStatement) this.con.prepareStatement("UPDATE users SET lastname = ? WHERE id = ?");
                    ps.setString(1, updated);
                    ps.setString(2, String.valueOf(this.id));
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
                        ps.setString(2, String.valueOf(this.id));
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
}
