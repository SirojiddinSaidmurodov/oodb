/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package MoviePortal;

import ObjModelAnalysis.annotations.Column;
import ObjModelAnalysis.annotations.Entity;
import ObjModelAnalysis.annotations.ManyToOne;
import ObjModelAnalysis.annotations.OneToMany;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Movie {
    @Column
    private String name;
    @Column
    @ManyToOne
    private Set<Rate> rateSet;
    @Column
    private Date releaseDate;
    @Column
    @OneToMany
    private List<Actor> actors;
    @Column
    @OneToMany
    private Set<Artist> artists;

    public Movie(String name, Set<Rate> rateSet, Date releaseDate, List<Actor> actors, Set<Artist> artists) {
        this.name = name;
        this.rateSet = rateSet;
        this.releaseDate = releaseDate;
        this.actors = actors;
        this.artists = artists;
    }

    public Movie() {
    }

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
