package services;

import models.Concert;
import models.Sponsor;
import models.SponsorType;
import db_methods.SponsorDbMethods;

import java.util.ArrayList;
import java.util.List;


public class SponsorService {
    private final SponsorDbMethods sponsorDbMethods = SponsorDbMethods.getInstance();

    public List<Sponsor> getAllSponsors() {
        return sponsorDbMethods.selectAll();
    }

    public List<Concert> getConcertsBySponsorId(int sponsorId) {
        return sponsorDbMethods.selectConcertsBySponsorId(sponsorId);
    }
}
