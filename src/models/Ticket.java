package models;

public class Ticket {
    private int ticketId;
    private int concertId;
    private int clientId;
    private int price;
    private TicketType ticketType;
    private String transactionDate;

    public Ticket(int ticketId, int concertId, int clientId, int price, String transactionDate) {
        this.ticketId = ticketId;
        this.concertId = concertId;
        this.clientId = clientId;
        this.price = price;
        this.transactionDate = transactionDate;
    }

    public Ticket(int ticketId, int concertId, int price, String transactionDate) {
        this.ticketId = ticketId;
        this.concertId = concertId;
        this.price = price;
        this.transactionDate = transactionDate;
    }

    public Ticket(int ticketId, int price, TicketType ticketType, String transactionDate) {
        this.ticketId = ticketId;
        this.price = price;
        this.ticketType = ticketType;
        this.transactionDate = transactionDate;
    }

    public Ticket(int ticketId, int concertId, int clientId, int price, TicketType ticketType, String transactionDate) {
        this.ticketId = ticketId;
        this.concertId = concertId;
        this.clientId = clientId;
        this.price = price;
        this.ticketType = ticketType;
        this.transactionDate = transactionDate;
    }

    public Ticket(int concertId, int clientId, int price, TicketType ticketType, String transactionDate) {
        this.concertId = concertId;
        this.clientId = clientId;
        this.price = price;
        this.ticketType = ticketType;
        this.transactionDate = transactionDate;
    }

    public Ticket(int ticketId,  int price, TicketType ticketType) {
        this.ticketId = ticketId;
        this.price = price;
        this.ticketType = ticketType;
    }


    public int getTicketId() {return ticketId;}
    public int getConcertId() {return concertId;}
    public int getClientId() {return clientId;}
    public int getPrice() {return price;}
    public TicketType getTicketType() {return ticketType;}
    public String getTransactionDate() {return transactionDate;}

    public void setConcertId(int concertId) {this.concertId = concertId;}
    public void setClientId(int clientId) {this.clientId = clientId;}
    public void setPrice(int price) {this.price = price;}
    public void setTicketType(TicketType ticketType) {this.ticketType = ticketType;}
    public void setTransactionDate(String transactionDate) {this.transactionDate = transactionDate;}

    @Override
    public String toString() {
        return "Ticket{" +
                "concertId=" + concertId +
                ", clientId=" + clientId +
                ", price=" + price +
                ", ticketType='" + ticketType + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                '}';
    }
}
