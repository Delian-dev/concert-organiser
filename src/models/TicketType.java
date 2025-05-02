package models;

public enum TicketType {
    EARLY_BIRD("First batch of tickets, generally at cheap prices"){
        @Override
        public void printTicketDescription(){
            System.out.println(getDescription());
        }
    },
    STANDARD("The usual type of ticket"){
        @Override
        public void printTicketDescription(){
            System.out.println(getDescription());
        }
    },
    VIP("Expensive and limited type of tickets, generally includes backstage access and other facilities"){
        @Override
        public void printTicketDescription(){
            System.out.println(getDescription());
        }
    };

    private final String ticketDescription;

    TicketType(String ticketDescription){this.ticketDescription = ticketDescription;}

    public String getDescription() {
        return ticketDescription;
    }

    public abstract void printTicketDescription();
}
