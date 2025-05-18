package services;

import models.*;
import db_methods.ConcertDbMethods;
import db_methods.MusicianDbMethods;
import db_methods.SponsorDbMethods;
import db_methods.TicketDbMethods;
import java.util.List;
import java.util.Map;

public class ConcertService {
    ConcertDbMethods concertDb = new ConcertDbMethods();
    MusicianDbMethods musicianDb = new MusicianDbMethods();
    SponsorDbMethods sponsorDb = new SponsorDbMethods();
    TicketDbMethods ticketDb = new TicketDbMethods();

    public List<Concert> listConcerts(){
        return concertDb.selectAll();
    }

    public List<Musician> listMusicians(int concertId){
        return musicianDb.getMusiciansByConcert(concertId);
    }

    public Map<Sponsor, SponsorType> listSponsors(int concertId){
        return sponsorDb.getSponsorsbyConcertId(concertId);
    }

    public List<Ticket> listTickets(int concertId){
        return ticketDb.selectTicketsByConcertId(concertId);
    }
}
