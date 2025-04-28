import java.sql.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

//        //testare conexiune db
//        try(Connection conn = Database.getConnection()){
//
//            conn.setAutoCommit(false);
//            final String retrieveTest = "Select * from test";
//            final String insertTest = "Insert into test(field_test) values(?)";
//
//            try(PreparedStatement stmt = conn.prepareStatement(retrieveTest)){
//                ResultSet rs = stmt.executeQuery();
//                while(rs.next()){
//                    System.out.println(rs.getString(1) + " " + rs.getString(2));
//                }
//            }
//
//            try(PreparedStatement stmt = conn.prepareStatement(insertTest)){
//                stmt.setString(1,"john cena");
//                stmt.executeUpdate();
//            }
//
//            conn.commit();
//
//        } catch (SQLException ex){
//            System.out.println("Error: " + ex.getMessage());
//        }
//
//        System.out.println("GG");
    }
}
