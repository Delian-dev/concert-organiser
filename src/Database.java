import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String connectionString = "jdbc:sqlite:data/javaProject.sqlite";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(connectionString);
    }
}
