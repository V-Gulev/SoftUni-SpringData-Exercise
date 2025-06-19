import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class DiabloStats {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "SoftUni_Vili");

        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", props);

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT u.first_name, u.last_name, COUNT(ug.id) AS 'total'" +
                        " FROM users u" +
                        " JOIN users_games ug ON u.id = ug.user_id" +
                        " WHERE u.user_name = ?");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        String firstName = resultSet.getString("first_name");

        if (firstName == null) {
            System.out.println("No such user exists");
        } else {
            String lastName = resultSet.getString("last_name");
            int totalGames = resultSet.getInt("total");
            System.out.printf("%s %s has played %d games%n", firstName, lastName, totalGames);
        }
    }
}
