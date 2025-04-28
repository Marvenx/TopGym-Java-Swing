package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnexion {
    private static Connection connection = null;

    public static Connection getConnexion(String url, String userName, String pass) {
        if (connection == null) {
            try {
                Class.forName(Config.DRIVERName);
                System.out.println("Driver Loaded Successfully!");
                connection = DriverManager.getConnection(url, userName, pass);
                System.out.println("Connected to Database Successfully!");
            } catch (ClassNotFoundException e) {
                System.out.println("Driver loading failed: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Database connection failed: " + e.getMessage());
            }
        }
        return connection;
    }

    // ✅ Test connection method
    public static void testConnection() {
        try {
            Connection testConn = getConnexion(Config.url, Config.userName, Config.pass);
            if (testConn != null && !testConn.isClosed()) {
                System.out.println("✅ Database connection is WORKING!");
            } else {
                System.out.println("❌ Database connection FAILED.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error during connection test: " + e.getMessage());
        }
    }
}
