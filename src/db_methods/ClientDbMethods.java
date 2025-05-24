package db_methods;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Client;
import utils.Database;
//POT FACE O INTERFATA NUMITA DBMETHODS CU FUNCTIILE DE BAZS SI TIP T PT A PUTEA DA OVERWRITE DUPA CU FIECARE TIP DE OBIECT
public class ClientDbMethods {

    private static final ClientDbMethods instance = new ClientDbMethods();
    private ClientDbMethods() {}

    public static ClientDbMethods getInstance() {
        return instance;
    }

    public void insertClient(Client client) throws SQLException {
        try(Connection conn = Database.getConnection()){
            conn.setAutoCommit(false);
            final String insertClient="insert into client(username, age, email, phone) values(?,?,?,?)";

            try(PreparedStatement stmt = conn.prepareStatement(insertClient)){
                stmt.setString(1,client.getUsername());
                stmt.setInt(2,client.getAge());
                stmt.setString(3,client.getEmail());
                stmt.setString(4,client.getPhone());
                stmt.executeUpdate();
            }
            conn.commit();

        }
    }
    
    public void updateClient(Client client){
        try(Connection conn = Database.getConnection()){
            conn.setAutoCommit(false);
            final String updateClient = "update client set username=?, age=?, email=?, phone=? where id_client=?";

            try(PreparedStatement stmt = conn.prepareStatement(updateClient)){
                stmt.setString(1, client.getUsername());
                stmt.setInt(2, client.getAge());
                stmt.setString(3, client.getEmail());
                stmt.setString(4, client.getPhone());
                stmt.setInt(5, client.getclientId());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch(SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void deleteClient(int id){
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("pragma foreign_keys = ON");
            }
            conn.setAutoCommit(false);
            final String deleteClient = "delete from client where id_client=?";

            try(PreparedStatement stmt = conn.prepareStatement(deleteClient)){
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            conn.commit();
            
        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public List<Client> selectAll(){
        List<Client> clients = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            final String selectAll = "select * from client";

            try(PreparedStatement stmt = conn.prepareStatement(selectAll)){
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id_client");
                    String name = rs.getString("username");
                    int age = rs.getInt("age");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    clients.add(new Client(id,name,age,email,phone));
                }
            }
        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return clients;
    }

    public Client selectClientById(int id){
        try(Connection conn = Database.getConnection()){
            final String selectClient = "select * from client where id_client=?";
            try(PreparedStatement stmt = conn.prepareStatement(selectClient)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    String username = rs.getString("username");
                    int age = rs.getInt("age");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    return new Client(id,username,age,email,phone);
                }
            }

        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }
}
