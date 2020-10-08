package MoviePortal;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Movie {
    private String name;
    private Set<Rate> rateSet;
    private Date releaseDate;
    private List<Actor> actors;
    private Set<Artist> artists;

    public Movie(String name, Set<Rate> rateSet, Date releaseDate, List<Actor> actors, Set<Artist> artists) {
        this.name = name;
        this.rateSet = rateSet;
        this.releaseDate = releaseDate;
        this.actors = actors;
        this.artists = artists;
    }
    public Movie(){};
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Rate> getRateSet() {
        return rateSet;
    }

    public void setRateSet(Set<Rate> rateSet) {
        this.rateSet = rateSet;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", rateSet=" + rateSet +
                ", releaseDate=" + releaseDate +
                ", actors=" + actors +
                ", artists=" + artists +
                '}';
    }
}
