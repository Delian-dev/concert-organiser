package models;
//as putea face un pachet separat unde definesc metode de validare a datelor
//iar in momentul in care vreau sa fac un insert/update in db <=> cand dau set la variabile, dau check cu functiile din pachet si daca nu respecta condiftiile => catch si nu adaug nimic stricat in database
//dupa cand efectiv creez obiecte cu constructori - o fac pe baza ce am deja in db => date corecte
//la setters o sa trebuiasca sa am grija pt ca practic eu apelez o metoda de update in db dupa ce apelez setterul
//sau mai degraba apelez setters doar dupa ce fac update in db idk si atunci e ca in cazul creeari de obiecte
public class Ticket {
    private final int ticketId;
    private int concertId;
    private int clientId;
    private int price;
    private TicketType ticketType; //de stabilit cum tratez validarile + de facut cu ENUMS!!!
    private String transactionDate;

    public Ticket(int ticketId, int concertId, int clientId, int price, String transactionDate) {
        this.ticketId = ticketId;
        this.concertId = concertId;
        this.clientId = clientId;
        this.price = price;
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
