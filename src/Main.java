import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        String jbdc = "jdbc:mysql://localhost:3306/soft_uni";
        String username = "root";
        String password = "SoftUni_Vili";

        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection(jbdc, properties);

        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM employees LIMIT 10");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String first_name = resultSet.getString("first_name");
            String job_title = resultSet.getString("job_title");
            double salary = resultSet.getDouble("salary");
            System.out.printf("%s %s %.2f%n", first_name, job_title, salary);
        }


    }
}
