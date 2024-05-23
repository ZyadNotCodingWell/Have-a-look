package cyBooks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CYBooks";
    private static final String USER = "root";
    private static final String PASS = "cytech0001";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            if (e.getErrorCode() == 2003) {
                startDatabase();
                try {
                    connection = DriverManager.getConnection(DB_URL, USER, PASS);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static void startDatabase() {
        try {
            // Commande pour redémarrer MySQL sous Windows
            String command = "mysqld --console";
            Process process = Runtime.getRuntime().exec(command);
            // Attendre que le serveur démarre
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Connexion à la base de données réussie!");
        } else {
            System.out.println("Échec de la connexion à la base de données.");
        }
    }
}

