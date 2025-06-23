import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrintAllMinionNames {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "SoftUni_Vili");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT m.name FROM minions m;", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = preparedStatement.executeQuery();

        int count = 0;
        while (resultSet.next()) count++;

        for (int i = 0; i < count; i++) {
            if (i % 2 == 0) {
                resultSet.absolute(i / 2 + 1);
            }else {
                resultSet.absolute(count - (i - 1) / 2);
            }

            String minionName = resultSet.getString("name");
            System.out.println(minionName);
        }

        System.out.println(count);
    }

    private static void printMinions(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT m.name FROM minions m;");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> minionNames = new ArrayList<>();

        while (resultSet.next()) {
            String minionName = resultSet.getString("name");
            minionNames.add(minionName);
        }

        for (int i = 0; i < minionNames.size(); i++) {
            String minionName;
            if (i % 2 == 0) {
                minionName = minionNames.get(i / 2);
            }else {
                minionName = minionNames.get(minionNames.size() - (i + 2) / 2);
            }

            System.out.println(minionName);
        }

    }
}
