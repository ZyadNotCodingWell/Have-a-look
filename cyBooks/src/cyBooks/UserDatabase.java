package cyBooks;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static List<User> users = new ArrayList<>();
    private static int nextId = 1;  // Ceci sera utilisé pour générer un nouvel ID unique.

    public static void addUser(User user) {
        if (user.getId() == null || user.getId().isEmpty()) {
            user.setId(String.valueOf(nextId++));
        }
        users.add(user);
    }

    public static User getUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null; // Renvoie null si aucun utilisateur avec l'ID donné n'est trouvé
    }

    public static void deleteUser(String id) throws SQLException {
        User user = getUserById(id);
        if (user != null) {
            users.remove(user);

            String query = "DELETE FROM Users WHERE id = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, id);
                preparedStatement.executeUpdate();
            }
        }
    }
}
