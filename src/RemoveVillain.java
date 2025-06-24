import java.sql.*;
import java.util.Scanner;

public class RemoveVillain {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int villainId = Integer.parseInt(sc.nextLine());
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "SoftUni_Vili");

        String villainName = findVillain(connection, villainId);
        if (villainName == null) {
            System.out.println("No such villain was found");
        } else {
            connection.setAutoCommit(false);
            try {
                int minionsCount = deleteConnectedMinions(connection, villainId);
                deleteVillain(connection, villainId);
                connection.commit();
                System.out.printf("%s was deleted%n", villainName);
                System.out.printf("%d minions released%n", minionsCount);
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }

    }

    private static String findVillain(Connection connection, int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT v.name FROM villains as v WHERE v.id = ?;");
        preparedStatement.setInt(1, villainId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            return null;
        }

       return resultSet.getString("name");
    }

    private static int deleteConnectedMinions(Connection connection, int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""
                DELETE FROM minions_villains WHERE villain_id = ?;""");
        preparedStatement.setInt(1, villainId);
        return preparedStatement.executeUpdate();
    }

    private static void deleteVillain(Connection connection, int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""
                DELETE FROM villains WHERE id = ?;""");
        preparedStatement.setInt(1, villainId);
        preparedStatement.executeUpdate();
    }
}
