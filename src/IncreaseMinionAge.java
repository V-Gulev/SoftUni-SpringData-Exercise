import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class IncreaseMinionAge {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "SoftUni_Vili");
        Scanner sc = new Scanner(System.in);
        int[] minionsIds = Arrays.stream(sc.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        increaseMinionAge(connection, minionsIds);
        printMinions(connection, minionsIds);
    }
    
    private static void increaseMinionAge(Connection connection, int[] minionsIds) throws SQLException {
        String idParam = String.join(", ", Collections.nCopies(minionsIds.length, "?"));
        PreparedStatement statement = connection.prepareStatement(String.format("UPDATE minions SET age = age + 1 WHERE id IN (%s);", idParam));

        for (int i = 0; i < minionsIds.length; i++) {
            statement.setInt(i + 1, minionsIds[i]);
        }

        statement.executeUpdate();
    }

    private static void printMinions(Connection connection, int[] minionsIds) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT name,age FROM minions;");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String minionName = resultSet.getString("name");
            int minionAge = resultSet.getInt("age");
            System.out.printf("%s %d%n", minionName, minionAge);
        }
    }
}