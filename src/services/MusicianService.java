package services;

import db_methods.BandDbMethods;
import db_methods.SoloArtistDbMethods;
import db_methods.MusicianDbMethods;
import models.Musician;
import models.SoloArtist;
import models.Band;
import models.Concert;
import utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicianService {
    private final BandDbMethods bandDb = new BandDbMethods();
    private final SoloArtistDbMethods soloDb = new SoloArtistDbMethods();
    private final MusicianDbMethods musicianDb = new MusicianDbMethods();

    public List<Musician> getAllMusicians() {
        List<Band> bands = bandDb.selectAll();
        List<Musician> musicians = new ArrayList<>(bands);

        List<SoloArtist> soloArtists = soloDb.selectAll();
        musicians.addAll(soloArtists);

        return musicians;
    }

    public List<Concert> getConcertsByMusicianId(int musicianId) {
        return musicianDb.selectConcertsByMusicianId(musicianId);
    }
}
