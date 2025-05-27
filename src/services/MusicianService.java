package services;

import db_methods.BandDbMethods;
import db_methods.SoloArtistDbMethods;
import db_methods.MusicianDbMethods;
import models.Musician;
import models.SoloArtist;
import models.Band;
import models.Concert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MusicianService {
    private final BandDbMethods bandDb = BandDbMethods.getInstance();
    private final SoloArtistDbMethods soloDb = SoloArtistDbMethods.getInstance();
    private final MusicianDbMethods musicianDb = MusicianDbMethods.getInstance();

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

    public List<Musician> listMusiciansByGenre(String genre) {

        // filter solo artists by provided genre
        List<SoloArtist> soloArtists = soloDb.selectAll()
                .stream()
                .filter(s -> genre.equalsIgnoreCase(s.getGenre()))
                .toList();
        List<Musician> filtered = new ArrayList<>(soloArtists);

        // filter bands by the same genre
        List<Band> bands = bandDb.selectAll()
                .stream()
                .filter(b -> genre.equalsIgnoreCase(b.getGenre()))
                .toList();
        filtered.addAll(bands);

        return filtered;
    }

    public Set<String> getUniqueGenres() {
        return musicianDb.selectAll()
                .stream()
                .map(Musician::getGenre)
                .collect(Collectors.toSet());
    }
}
