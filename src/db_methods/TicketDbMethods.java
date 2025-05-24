package db_methods;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Concert;
import models.Ticket;
import models.TicketType;
import utils.Database;

public class TicketDbMethods {
    private static final TicketDbMethods instance = new TicketDbMethods();
    private TicketDbMethods() {}

    public static TicketDbMethods getInstance() {
        return instance;
    }

    public void insertTicket(Ticket ticket) {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("PRAGMA foreign_keys=ON;");
            }
            conn.setAutoCommit(false);
            final String insertTicket = "insert into ticket(id_concert, id_client, price, ticket_type, transaction_date) values(?,?,?,?,?);";

            try(PreparedStatement stmt = conn.prepareStatement(insertTicket)){
                stmt.setInt(1, ticket.getConcertId());
                stmt.setInt(2, ticket.getClientId());
                stmt.setInt(3, ticket.getPrice());
                stmt.setString(4, ticket.getTicketType().name());
                stmt.setString(5, ticket.getTransactionDate());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public void updateTicket(Ticket ticket) {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("PRAGMA foreign_keys=ON;");
            }
            conn.setAutoCommit(false);
            final String updateTicket="update ticket set id_concert=?, id_client=?, price=?, ticket_type=?, transaction_date=? where id_ticket=?";

            try(PreparedStatement stmt = conn.prepareStatement(updateTicket)){
                stmt.setInt(1, ticket.getConcertId());
                stmt.setInt(2, ticket.getClientId());
                stmt.setInt(3, ticket.getPrice());
                stmt.setString(4, ticket.getTicketType().name());
                stmt.setString(5, ticket.getTransactionDate());
                stmt.setInt(6, ticket.getTicketId());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public void deleteTicket(int id) {
        try(Connection conn = Database.getConnection()){
            conn.setAutoCommit(false);
            final String deleteTicket = "delete from ticket where id_ticket=?";
            try(PreparedStatement stmt = conn.prepareStatement(deleteTicket)){
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public List<Ticket> selectAll(){
        List<Ticket> tickets = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            final String selectAll = "select * from ticket";

            try(PreparedStatement stmt = conn.prepareStatement(selectAll)){
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id_ticket");
                    int concertId = rs.getInt("id_concert");
                    int clientId = rs.getInt("id_client");
                    int price = rs.getInt("price");
                    String ticketTypeStr = rs.getString("ticket_type");
                    TicketType ticketType = TicketType.valueOf(ticketTypeStr); //conversion to the enum TicketType
                    String transactionDate = rs.getString("transaction_date");
                    tickets.add(new Ticket(id,concertId,clientId,price,ticketType,transactionDate));
                }
            }
        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return tickets;
    }

    public Ticket selectTicketById(int id) {
        try(Connection conn = Database.getConnection()){
            final String selectTicketById = "select * from ticket where id_ticket=?";
            try(PreparedStatement stmt = conn.prepareStatement(selectTicketById)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    int concertId = rs.getInt("id_concert");
                    int clientId = rs.getInt("id_client");
                    int price = rs.getInt("price");
                    String ticketTypeStr = rs.getString("ticket_type");
                    TicketType ticketType = TicketType.valueOf(ticketTypeStr);
                    String transactionDate = rs.getString("transaction_date");
                    return new Ticket(id,concertId,clientId,price,ticketType,transactionDate);
                }
            }
        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return null;
    }

    public List<Ticket> selectTicketsByConcertId(int concertId){
        List<Ticket> tickets = new ArrayList<>();
        try(Connection conn=Database.getConnection()){
            final String selectTicketByConcertId = "select * from ticket where id_concert=?";
            try(PreparedStatement stmt = conn.prepareStatement(selectTicketByConcertId)){
                stmt.setInt(1, concertId);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int client_id = rs.getInt("id_client");
                    int price = rs.getInt("price");
                    String ticketTypeStr = rs.getString("ticket_type");
                    TicketType ticketType = TicketType.valueOf(ticketTypeStr);
                    String transactionDate = rs.getString("transaction_date");
                    tickets.add(new Ticket(concertId,client_id,price,ticketType,transactionDate));
                }
            }
        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return tickets;
    }

    public List<Ticket> selectTicketsByClientId(int clientId){
        List<Ticket> tickets = new ArrayList<>();
        try(Connection conn=Database.getConnection()){
            final String selectTicketByClientId = "select * from ticket where id_client=?";
            try(PreparedStatement stmt = conn.prepareStatement(selectTicketByClientId)){
                stmt.setInt(1, clientId);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int concert_id = rs.getInt("id_concert");
                    int price = rs.getInt("price");
                    String ticketTypeStr = rs.getString("ticket_type");
                    TicketType ticketType = TicketType.valueOf(ticketTypeStr);
                    String transactionDate = rs.getString("transaction_date");
                    tickets.add(new Ticket(concert_id,clientId,price,ticketType,transactionDate));
                }
            }
        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return tickets;
    }

}
