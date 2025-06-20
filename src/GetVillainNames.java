import java.sql.*;

public class GetVillainNames {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "SoftUni_Vili");
        PreparedStatement preparedStatement = connection.prepareStatement("""
                select v.name, count(m.id)
                from villains v
                join minions_villains mv on v.id = mv.villain_id
                join minions m on m.id = mv.minion_id
                group by v.name
                having count(m.id) > 15
                order by count(m.id) desc;""");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String villainName = resultSet.getString("name");
            int minionCount  = resultSet.getInt("count(m.id)");
            System.out.printf("%s %d%n", villainName, minionCount);
        }
    }
}
