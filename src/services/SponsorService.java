package services;

import models.Concert;
import models.Sponsor;
import models.SponsorType;
import db_methods.SponsorDbMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SponsorService {
    private final SponsorDbMethods sponsorDbMethods = SponsorDbMethods.getInstance();

    public List<Sponsor> getAllSponsors() {
        return sponsorDbMethods.selectAll();
    }

    public List<Concert> getConcertsBySponsorId(int sponsorId) {
        return sponsorDbMethods.selectConcertsBySponsorId(sponsorId);
    }

    public int getConcertCountBySponsorId(int sponsorId) {
        List<Concert> concerts = sponsorDbMethods.selectConcertsBySponsorId(sponsorId);
        return concerts != null ? concerts.size() : 0;
    }

    public List<Sponsor> getSponsorsSortedByConcertCount() {
        return getAllSponsors().stream()
                .sorted((s1, s2) -> {
                    int c1 = getConcertCountBySponsorId(s1.getSponsorId());
                    int c2 = getConcertCountBySponsorId(s2.getSponsorId());
                    return Integer.compare(c2, c1); // descending
                })
                .collect(Collectors.toList());
    }
}
