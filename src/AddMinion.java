import java.sql.*;
import java.util.Scanner;

public class AddMinion {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String[] tokens = sc.nextLine().split("\\s+");
        String minionName = tokens[1];
        int minionAge = Integer.parseInt(tokens[2]);
        String townName = tokens[3];

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "SoftUni_Vili");
        ensureTownExists(connection, townName);
        tokens = sc.nextLine().split("\\s+");
        String villainName = tokens[1];
        addVillain(connection, villainName);
        addMinion(connection, minionName, minionAge, townName);

    }

    private static void addMinion(Connection connection, String minionName, int minionAge, String townName) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO minions (name, age, town_id)
                VALUES (?, ?, (SELECT id FROM towns WHERE name = ?));""");
        statement.setString(1, minionName);
        statement.setInt(2, minionAge);
        statement.setString(3, townName);
        statement.executeUpdate();

        System.out.printf("Successfully added %s to be minion of %s%n", minionName, townName);
    }

    private static void addTown(Connection connection, String townName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO towns (name)
                VALUES (?);""");
        statement.setString(1, townName);
        statement.executeUpdate();
        System.out.printf("Town %s was added to the database.%n", townName);
    }


    private static void ensureTownExists(Connection connection, String townName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM towns WHERE name = ?;""");
        statement.setString(1, townName);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            addTown(connection, townName);
        }
    }

    private static void addVillain(Connection connection, String villainName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO villains (name, evilness_factor)
                VALUES (?, 'evil');""");
        statement.setString(1, villainName);
        statement.executeUpdate();
        System.out.printf("Villain %s was added to the database.%n", villainName);
    }


}
