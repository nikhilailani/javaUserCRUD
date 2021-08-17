import java.util.*;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import service.AdminService;
import service.DeveloperService;
import service.TesterService;
import service.UserService;

public class CRUDManager {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String username = "", validPassword, password, userRole;
        int id;
        System.out.print("Enter username: ");
        if(sc.hasNext()) {
            username = sc.next();
        }
        Connection con = null;
        Statement stmt;
        ResultSet rs;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db", "dummy", "root");
            // here test_db is database name, root is username and password
            stmt = (Statement) con.createStatement();
            rs = stmt.executeQuery("select password, role, id from users where username = '" + username + "'");
            if (rs.next()) {
                System.out.print("Enter password: ");
                userRole = rs.getString("role");
                id = rs.getInt("id");
                password = sc.next();
                validPassword = rs.getString(1);


                if(password.equals(validPassword)) {
                    System.out.println("Login Successful");
                    UserService ms;
                    if(userRole.equals("admin")) {
                        ms = new AdminService(con, id);
                    } else if(userRole.equals("developer")) {
                        ms = new DeveloperService(con, id);
                    } else {
                        ms = new TesterService(con, id);
                    }
                    ms.run();
                } else {
                    System.out.println("Enter correct password");
                }
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            sc.close();
        }
    }
}
