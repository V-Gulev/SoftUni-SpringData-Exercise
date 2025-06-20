import java.sql.*;
import java.util.Scanner;

public class GetMinionsNames {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int villainId = Integer.parseInt(sc.nextLine());
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "SoftUni_Vili");
        PreparedStatement preparedStatement = connection.prepareStatement("select m.name, m.age\n" +
                "from minions m\n" +
                "join minions_villains mv on m.id = mv.minion_id\n" +
                "where mv.villain_id = ?;");

        preparedStatement.setInt(1, villainId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String minionName = resultSet.getString("name");
            int minionAge = resultSet.getInt("age");
            System.out.printf("%s %d%n", minionName, minionAge);
            while (resultSet.next()) {
                minionName = resultSet.getString("name");
                minionAge = resultSet.getInt("age");
                System.out.printf("%s %d%n", minionName, minionAge);
            }
        } else {
            System.out.println("No villain with ID " + villainId + " exists in the database.");
        }


    }
}
